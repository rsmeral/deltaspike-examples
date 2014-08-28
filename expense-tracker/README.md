DeltaSpike Expense Tracker example
==================================

Simple corporate expense tracking application.

## DeltaSpike features demonstrated
* Core
 * project-stage-based bean exclusion `@Exclude(ifProjectStage=...)`
 * type-safe messages for i18n using `@MessageBundle`, `@MessageTemplate`
 * decoupled exception handling using `@ExceptionHandler`, `ExceptionToCatchEvent`
 * DS feature deactivation using `ClassDeactivator` and `Deactivatable`

* Security
 * simple method-level authorization using `@Secures`, through [picketlink-deltaspike](https://github.com/picketlink/picketlink/tree/master/modules/deltaspike) integration module
 * complex authorization using @Secured, AccessDecisionVoters
 * integration with an IDM framework ([PicketLink](https://github.com/picketlink))

* Data 
 * Entity repositories
  * (Method-name-based DSL)-based querying
  * Annotation-based querying using `@Query`
  * Criteria API integration
 * Automatic tracking of date/author of last change in entities using `@ModifiedBy`, `@ModifiedOn`
 
* JPA
 * declarative transaction management in CDI beans using `@Transactional`

* JSF
 * Type-safe view configuration and navigation using `ViewConfig`, `@View`, `@Folder`, `@ViewMetaData`
 * security integration: authorization of access to views using `@Secured` and `AccessDecisionVoter`s
 * view lifecycle callbacks: `@PreRenderView`, `@PostRenderView` and `@ViewControllerRef`, `@ViewRef`

* BeanValidation
 * custom CDI-enabled `ConstraintValidator` using `CDIAwareConstraintValidatorFactory`

* Servlet
 * observing servlet events using CDI observers (`@Observes @Initialized ServletContext`)

* CdiControl
 * manually starting and stopping built-in CDI contexts using `ContextControl`
