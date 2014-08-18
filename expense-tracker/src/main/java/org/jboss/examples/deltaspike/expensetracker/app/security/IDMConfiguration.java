package org.jboss.examples.deltaspike.expensetracker.app.security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.picketlink.idm.config.IdentityConfiguration;
import org.picketlink.idm.config.IdentityConfigurationBuilder;
import org.picketlink.idm.jpa.model.sample.simple.AccountTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.AttributeTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.GroupTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.IdentityTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.PartitionTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.PasswordCredentialTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RelationshipIdentityTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RelationshipTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RoleTypeEntity;
import org.picketlink.idm.model.Relationship;

@ApplicationScoped
public class IDMConfiguration {
//
//    @Inject
//    private EEJPAContextInitializer contextInitializer;

    private IdentityConfiguration identityConfig = null;

    @Produces
    @ApplicationScoped
    public IdentityConfiguration createJPAConfig() {
        if (identityConfig == null) {
            initFileConfig();
        }
        return identityConfig;
    }

    private void initFileConfig() {
        IdentityConfigurationBuilder builder = new IdentityConfigurationBuilder();

        builder
                .named("default")
                .stores()
                .file()
                .workingDirectory("target/data")
                .supportGlobalRelationship(Relationship.class)
                .supportAllFeatures();

        identityConfig = builder.build();
    }

    private void initJPAConfig() {
        IdentityConfigurationBuilder builder = new IdentityConfigurationBuilder();

        builder
                .named("default")
                .stores()
                .jpa()
                .mappedEntity(
                        AccountTypeEntity.class,
                        AttributeTypeEntity.class,
                        GroupTypeEntity.class,
                        IdentityTypeEntity.class,
                        PartitionTypeEntity.class,
                        PasswordCredentialTypeEntity.class,
                        RelationshipIdentityTypeEntity.class,
                        RelationshipTypeEntity.class,
                        RoleTypeEntity.class
                )
                .supportGlobalRelationship(Relationship.class)
//                .addContextInitializer(contextInitializer)
                .supportAllFeatures();

        identityConfig = builder.build();
    }
}
