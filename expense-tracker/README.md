DeltaSpike Expense Tracker example
==================================

Simple corporate expense tracking application.

## What

The application allows employees to create expense reports, add expenses and 
upload receipts which cover the expenses. An accountant can be assigned to a 
report and add reimbursements. The employee submits the report, then the 
accountant either approves or rejects it. After that, the report can be settled. 
Administrators can also manage employees and purposes.

## Why

Primary purpose of this application is demonstration and testing of DeltaSpike 
features. The aim is to integrate as many features as possible in (more or less) 
sensible real-world use cases. 

## How

Several principles are observed throughout the application design:
* EE6-based, CDI-oriented, EJB-free design
* standard MVC pattern
* portability
* separation of concerns
 * identity model and domain model are independent
 * application layers: presentation, domain logic (separate from application logic), data access

That being sad, the code indeed departs from these ideals in many places,
but tries to adhere as much as possible.

# Technical stuff

The main dependencies are as follows:
* EE6 APIs: CDI 1.0, JPA 2.0, JSF 2.1, Bean Validation 1.0, Servlet 3.0
* Almost all DeltaSpike modules: Core, Security, Data, BeanValidation, JPA, JSF, Servlet, CdiControl
* PicketLink IDM
* RichFaces

## Testing

There is a functional testsuite covering most basic user scenarios. The tests
are "object-oriented", following the Page Object (and Page Fragments) test design 
patterns. 

The tests use: 
* JUnit
* Selenium WebDriver
* Arquillian Core
* Arquillian Graphene
* Arquillian Drone

## OpenShift deployment

The application is deployable to OpenShift. The POM contains an `openshift`
profile and can be therefore also compiled on OpenShift. 

The OpenShift application needs an JBoss AS/EAP server and a MySQL database. 
Application data reside in the default database, but one more database called 
`identity` needs to be created for IDM data.

## DeltaSpike features demonstrated

* Core
 * project-stage-based bean exclusion `@Exclude(ifProjectStage=...)`
 * simple configuration using `@ConfigProperty` and `PropertyFileConfig`
 * type-safe messages for i18n using `@MessageBundle`, `@MessageTemplate`
 * decoupled exception handling using `@ExceptionHandler`, `ExceptionToCatchEvent`
 * DS feature deactivation using `ClassDeactivator` and `Deactivatable`

* Security
 * simple method-level authorization using `@Secures`, through [picketlink-deltaspike](https://github.com/picketlink/picketlink/tree/master/modules/deltaspike) integration module
 * implementation of domain rules using `@Secures` and `@SecurityParameterBinding`
 * complex authorization using `@Secured`, `AccessDecisionVoter`s
 * integration with an IDM framework ([PicketLink](https://github.com/picketlink))

* Data 
 * entity repositories
  * (Method-name-based DSL)-based querying
  * annotation-based querying using `@Query`
  * criteria API integration
  * multiple entity managers, resolution using `@EntityManagerResolver`
 * automatic tracking of date/author of last change in entities using `@ModifiedBy`, `@ModifiedOn`
 
* JPA
 * declarative transaction management in CDI beans using `@Transactional`

* JSF
 * type-safe view configuration and navigation using `ViewConfig`, `@View`, `@Folder`, `@ViewMetaData`
 * security integration: authorization of access to views using `@Secured` and `AccessDecisionVoter`s
 * view lifecycle callbacks: `@PreRenderView`, `@PostRenderView` and `@ViewControllerRef`, `@ViewRef`
 * add messages from a `@MessageBundle` to JSF context using the `JsfMessage<T>` API
 * automatic redirection of exceptions to a designated error view using `DefaultErrorView`

* BeanValidation
 * custom CDI-enabled `ConstraintValidator` using `CDIAwareConstraintValidatorFactory`

* Servlet
 * observing servlet events using CDI observers (`@Observes @Initialized ServletContext`)
 * injection of web resources using `@InjectableResource(resourceProvider = WebResourceProvider.class)`

* CdiControl
 * manually starting and stopping built-in CDI contexts using `ContextControl`
