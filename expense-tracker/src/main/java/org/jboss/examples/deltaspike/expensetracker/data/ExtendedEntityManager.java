package org.jboss.examples.deltaspike.expensetracker.data;

import java.io.Serializable;
import java.util.Map;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

@Typed()
public class ExtendedEntityManager implements EntityManager, Serializable {

    private transient EntityManager wrapped;

    protected ExtendedEntityManager() {
    }

    public ExtendedEntityManager(EntityManager wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void persist(Object entity) {
        wrapped.persist(entity);
    }

    @Override
    public <T> T merge(T entity) {
        return wrapped.merge(entity);
    }

    @Override
    public void remove(Object entity) {
        wrapped.remove(entity);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey) {
        return wrapped.find(entityClass, primaryKey);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
        return wrapped.find(entityClass, primaryKey, properties);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
        return wrapped.find(entityClass, primaryKey, lockMode);
    }

    @Override
    public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
        return wrapped.find(entityClass, primaryKey, lockMode, properties);
    }

    @Override
    public <T> T getReference(Class<T> entityClass, Object primaryKey) {
        return wrapped.getReference(entityClass, primaryKey);
    }

    @Override
    public void flush() {
        wrapped.flush();
    }

    @Override
    public void setFlushMode(FlushModeType flushMode) {
        wrapped.setFlushMode(flushMode);
    }

    @Override
    public FlushModeType getFlushMode() {
        return wrapped.getFlushMode();
    }

    @Override
    public void lock(Object entity, LockModeType lockMode) {
        wrapped.lock(entity, lockMode);
    }

    @Override
    public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        wrapped.lock(entity, lockMode, properties);
    }

    @Override
    public void refresh(Object entity) {
        wrapped.refresh(entity);
    }

    @Override
    public void refresh(Object entity, Map<String, Object> properties) {
        wrapped.refresh(entity, properties);
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode) {
        wrapped.refresh(entity, lockMode);
    }

    @Override
    public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
        wrapped.refresh(entity, lockMode, properties);
    }

    @Override
    public void clear() {
        wrapped.clear();
    }

    @Override
    public void detach(Object entity) {
        wrapped.detach(entity);
    }

    @Override
    public boolean contains(Object entity) {
        return wrapped.contains(entity);
    }

    @Override
    public LockModeType getLockMode(Object entity) {
        return wrapped.getLockMode(entity);
    }

    @Override
    public void setProperty(String propertyName, Object value) {
        wrapped.setProperty(propertyName, value);
    }

    @Override
    public Map<String, Object> getProperties() {
        return wrapped.getProperties();
    }

    @Override
    public Query createQuery(String qlString) {
        return wrapped.createQuery(qlString);
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return wrapped.createQuery(criteriaQuery);
    }

    @Override
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
        return wrapped.createQuery(qlString, resultClass);
    }

    @Override
    public Query createNamedQuery(String name) {
        return wrapped.createNamedQuery(name);
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
        return wrapped.createNamedQuery(name, resultClass);
    }

    @Override
    public Query createNativeQuery(String sqlString) {
        return wrapped.createNativeQuery(sqlString);
    }

    @Override
    public Query createNativeQuery(String sqlString, Class resultClass) {
        return wrapped.createNativeQuery(sqlString, resultClass);
    }

    @Override
    public Query createNativeQuery(String sqlString, String resultSetMapping) {
        return wrapped.createNativeQuery(sqlString, resultSetMapping);
    }

    @Override
    public void joinTransaction() {
        wrapped.joinTransaction();
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return wrapped.unwrap(cls);
    }

    @Override
    public Object getDelegate() {
        return wrapped.getDelegate();
    }

    @Override
    public void close() {
        wrapped.close();
    }

    @Override
    public boolean isOpen() {
        return wrapped.isOpen();
    }

    @Override
    public EntityTransaction getTransaction() {
        return wrapped.getTransaction();
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return wrapped.getEntityManagerFactory();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return wrapped.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return wrapped.getMetamodel();
    }

}
