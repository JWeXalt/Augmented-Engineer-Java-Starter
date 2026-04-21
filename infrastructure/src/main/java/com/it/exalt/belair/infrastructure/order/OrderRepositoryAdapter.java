package com.it.exalt.belair.infrastructure.order;

import com.it.exalt.belair.domain.order.Order;
import com.it.exalt.belair.domain.order.OrderRepositoryPort;
import com.it.exalt.belair.domain.order.OrderStatut;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderJpaRepository jpaRepository;
    private final OrderEntityMapper mapper;

    public OrderRepositoryAdapter(OrderJpaRepository jpaRepository, OrderEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findByFestivalierIdAndStatut(String festivalierId, OrderStatut statut) {
        return jpaRepository.findByFestivalierIdAndStatut(festivalierId, statut.name())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
