package org.jboss.examples.deltaspike.expensetracker.app.security;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
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

/**
 * Configuration of PicketLink.
 */
@ApplicationScoped
public class IDMConfiguration {

    private IdentityConfiguration identityConfig = null;

    @Produces
    public IdentityConfiguration createConfig() {
        if (identityConfig == null) {
            initJPAConfig();
        }
        return identityConfig;
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
                .supportAllFeatures();

        identityConfig = builder.build();
    }
}
