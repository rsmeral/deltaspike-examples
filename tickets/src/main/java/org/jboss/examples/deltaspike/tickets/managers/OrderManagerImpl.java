package org.jboss.examples.deltaspike.tickets.managers;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.jboss.examples.deltaspike.tickets.model.dto.OrderDto;
import org.jboss.examples.deltaspike.tickets.repositories.OrderRepository;

@Model
public class OrderManagerImpl {

    @Inject
    private OrderRepository orderRepository;

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll();
    }

}
