package com.it.exalt.belair.domain.order;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;

import java.util.UUID;

public class PlaceOrderService implements PlaceOrderUseCase {

    private final FestivalierPort festivalierPort;
    private final ArticleCatalogPort articleCatalogPort;
    private final OrderPort orderPort;

    public PlaceOrderService(FestivalierPort festivalierPort, ArticleCatalogPort articleCatalogPort, OrderPort orderPort) {
        this.festivalierPort = festivalierPort;
        this.articleCatalogPort = articleCatalogPort;
        this.orderPort = orderPort;
    }

    @Override
    public PlaceOrderResult execute(PlaceOrderCommand command) {
        Festivalier festivalier = festivalierPort.findById(command.festivalierId())
                .orElseThrow(() -> new IllegalArgumentException("Festivalier not found: " + command.festivalierId()));

        int totalCost = 0;
        for (PlaceOrderCommand.ArticleItem item : command.articles()) {
            Article article = articleCatalogPort.findById(item.articleId())
                    .orElseThrow(() -> new IllegalArgumentException("Article not found: " + item.articleId()));
            totalCost += article.foodType().getTokenCost() * item.quantite();
        }

        if (festivalier.getFoodTokenBalance() < totalCost) {
            throw new InsufficientTokensException(
                    "Insufficient food tokens: required " + totalCost + ", available " + festivalier.getFoodTokenBalance()
            );
        }

        festivalier.deductFoodTokens(totalCost);

        Order order = new Order(UUID.randomUUID().toString(), OrderStatut.EN_ATTENTE);
        Order savedOrder = orderPort.save(order);

        return new PlaceOrderResult(savedOrder.getId(), savedOrder.getStatut());
    }
}
