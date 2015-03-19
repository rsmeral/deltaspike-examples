DeltaSpike MBean translator
===========================

A translation service from English into two languages -- French and German.

Exposes statistics about translations through an MBean, using DeltaSpike's `@MBean` and `@JmxManaged` annotations.

The dictionaries are stored in properties files and accessed using `@MessageBundle`, with custom implementations of `LocaleResolver` and `MessageInterpolator`.