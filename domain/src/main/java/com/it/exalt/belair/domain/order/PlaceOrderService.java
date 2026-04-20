package com.it.exalt.belair.domain.order;

import java.util.UUID;

public class PlaceOrderService implements PlaceOrderUseCase {


    private final ArticleRepository articleRepository;

    public PlaceOrderService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public PlaceOrderResult execute(PlaceOrderCommand command) {
        return new PlaceOrderResult(UUID.randomUUID().toString(), OrderStatut.EN_ATTENTE);
    }
}
