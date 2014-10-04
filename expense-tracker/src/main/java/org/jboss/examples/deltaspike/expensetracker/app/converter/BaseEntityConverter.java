package org.jboss.examples.deltaspike.expensetracker.app.converter;

import java.lang.reflect.ParameterizedType;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.apache.deltaspike.data.api.EntityRepository;
import org.jboss.examples.deltaspike.expensetracker.domain.model.BaseEntity;

public abstract class BaseEntityConverter<REPO extends EntityRepository<ENT, Long>, ENT extends BaseEntity> implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if ("".equals(value)) {
            return null;
        }

        REPO repo = BeanProvider.getContextualReference(getRepositoryClass());
        return repo.findBy(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        return ((ENT) value).getId().toString();
    }

    private Class<REPO> getRepositoryClass() {
        return (Class<REPO>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

}
