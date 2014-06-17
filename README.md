DeltaSpike Examples
===================

A collection of example applications demonstrating the features of the [DeltaSpike](http://deltaspike.apache.org) project.

## Running an example

1. Switch to the directory of a particular example
2. Build the project
    
        mvn clean package


3. Deploy to a Java EE 6+ container, as described in the following sections

### Deploying to JBoss EAP / WildFly

1. Set the `JBOSS_HOME` environment variable to the path to a JBoss EAP / WildFly installation
2. Start the container

        $JBOSS_HOME/bin/standalone.sh &


3. Deploy the application
        
        $JBOSS_HOME/bin/jboss-cli.sh "connect, deploy path/to/the.war"


## Running the functional tests

Some of the examples provide Arquillian functional tests. They are located under the "functional-tests" directory of an example.

1. To run these tests, you must first build the example
2. Navigate to the `functional-tests` directory of the example
3. Run the tests, optionally adding `-Dremote` to the following command, if the server is already running

        mvn clean verify


To run the functional tests of all examples at once, run

    mvn clean verify -Pftest
