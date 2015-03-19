package org.jboss.examples.deltaspike.tickets.repositories;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.mapping.MappingConfig;
import org.jboss.examples.deltaspike.tickets.model.Order;
import org.jboss.examples.deltaspike.tickets.model.dto.OrderDto;
import org.jboss.examples.deltaspike.tickets.model.mapper.OrderMapper;

@Repository(forEntity = Order.class)
@MappingConfig(OrderMapper.class)
public interface OrderRepository extends EntityRepository<OrderDto, Long> {

}
