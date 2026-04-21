package com.it.exalt.belair.domain.order;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;
import com.it.exalt.belair.domain.stock.Stock;
import com.it.exalt.belair.domain.stock.StockPort;

import java.util.UUID;

public class PlaceOrderService implements PlaceOrderUseCase {

    private final FestivalierPort festivalierPort;
    private final ArticleCatalogPort articleCatalogPort;
    private final StockPort stockPort;
    private final OrderPort orderPort;

    public PlaceOrderService(FestivalierPort festivalierPort, ArticleCatalogPort articleCatalogPort,
                             StockPort stockPort, OrderPort orderPort) {
        this.festivalierPort = festivalierPort;
        this.articleCatalogPort = articleCatalogPort;
        this.stockPort = stockPort;
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

            Stock stock = stockPort.findByArticleId(item.articleId())
                    .orElseThrow(() -> new IllegalArgumentException("Stock not found for article: " + item.articleId()));

            if (stock.quantite() < item.quantite()) {
                throw new OutOfStockException(
                        "Insufficient stock for article: " + item.articleId()
                                + " (requested: " + item.quantite() + ", available: " + stock.quantite() + ")"
                );
            }

            totalCost += article.foodType().getTokenCost() * item.quantite();
        }

        if (festivalier.getFoodTokenBalance() < totalCost) {
            throw new InsufficientTokensException(
                    "Insufficient food tokens: required " + totalCost + ", available " + festivalier.getFoodTokenBalance()
            );
        }

        festivalier.deductFoodTokens(totalCost);

        for (PlaceOrderCommand.ArticleItem item : command.articles()) {
            stockPort.deductStock(item.articleId(), item.quantite());
        }

        Order order = new Order(UUID.randomUUID().toString(), OrderStatut.EN_ATTENTE);
        Order savedOrder = orderPort.save(order);

        return new PlaceOrderResult(savedOrder.getId(), savedOrder.getStatut());
    }
}
