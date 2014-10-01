DeltaSpike Expense Tracker example
==================================

Simple corporate expense tracking application.

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
 * complex authorization using @Secured, AccessDecisionVoters
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
