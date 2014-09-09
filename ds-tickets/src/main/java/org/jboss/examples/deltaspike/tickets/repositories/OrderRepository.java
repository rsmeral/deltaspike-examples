package org.jboss.examples.deltaspike.tickets.repositories;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.mapping.MappingConfig;
import org.jboss.examples.deltaspike.tickets.model.Order;
import org.jboss.examples.deltaspike.tickets.model.OrderEntity;
import org.jboss.examples.deltaspike.tickets.model.OrderMapper;

@Repository(forEntity = OrderEntity.class)
@MappingConfig(OrderMapper.class)
public interface OrderRepository extends EntityRepository<Order, Long> {

}
