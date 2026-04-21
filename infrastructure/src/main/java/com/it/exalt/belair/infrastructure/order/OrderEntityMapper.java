package com.it.exalt.belair.infrastructure.order;

import com.it.exalt.belair.domain.order.OrderLine;
import com.it.exalt.belair.domain.order.Order;
import com.it.exalt.belair.domain.order.OrderStatut;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderEntityMapper {

    public OrderEntity toEntity(Order order) {
        List<OrderLineEntity> ligneEntities = order.getLignes().stream()
                .map(l -> new OrderLineEntity(l.articleId(), l.quantite()))
                .collect(Collectors.toList());
        return new OrderEntity(order.getId(), order.getFestivalierId(), order.getStatut().name(), ligneEntities);
    }

    public Order toDomain(OrderEntity entity) {
        List<OrderLine> lignes = entity.getLignes().stream()
                .map(l -> new OrderLine(l.getArticleId(), l.getQuantite()))
                .collect(Collectors.toList());
        return new Order(entity.getId(), entity.getFestivalierId(), OrderStatut.valueOf(entity.getStatut()), lignes);
    }
}
