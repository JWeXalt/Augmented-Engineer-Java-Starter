package com.it.exalt.belair.domain.order;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;

public class CancelOrderService {

    private final OrderRepositoryPort orderRepositoryPort;
    private final FestivalierPort festivalierPort;
    private final ArticleCatalogPort articleCatalogPort;

    public CancelOrderService(OrderRepositoryPort orderRepositoryPort,
                              FestivalierPort festivalierPort,
                              ArticleCatalogPort articleCatalogPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.festivalierPort = festivalierPort;
        this.articleCatalogPort = articleCatalogPort;
    }

    public void execute(CancelOrderCommand command) {
        Order order = orderRepositoryPort.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

        if (order.getStatut() != OrderStatut.EN_ATTENTE) {
            throw new OrderAlreadyAcknowledgedException(command.orderId());
        }

        Festivalier festivalier = festivalierPort.findById(command.festivalierId())
                .orElseThrow(() -> new IllegalArgumentException("Festivalier not found: " + command.festivalierId()));

        int refundAmount = 0;
        for (OrderLine line : order.getLignes()) {
            Article article = articleCatalogPort.findById(line.articleId())
                    .orElseThrow(() -> new IllegalArgumentException("Article not found: " + line.articleId()));
            refundAmount += article.foodType().getTokenCost() * line.quantite();
        }

        festivalier.deductFoodTokens(-refundAmount);

        Order cancelledOrder = new Order(order.getId(), order.getFestivalierId(), OrderStatut.ANNULEE, order.getLignes());
        orderRepositoryPort.save(cancelledOrder);
    }
}
