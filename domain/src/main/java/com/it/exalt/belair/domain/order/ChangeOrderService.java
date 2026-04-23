package com.it.exalt.belair.domain.order;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;
import com.it.exalt.belair.domain.stock.Stock;
import com.it.exalt.belair.domain.stock.StockPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChangeOrderService {

    private final OrderRepositoryPort orderRepositoryPort;
    private final FestivalierPort festivalierPort;
    private final ArticleCatalogPort articleCatalogPort;
    private final StockPort stockPort;

    public ChangeOrderService(OrderRepositoryPort orderRepositoryPort,
                              FestivalierPort festivalierPort,
                              ArticleCatalogPort articleCatalogPort,
                              StockPort stockPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.festivalierPort = festivalierPort;
        this.articleCatalogPort = articleCatalogPort;
        this.stockPort = stockPort;
    }

    public void execute(ChangeOrderCommand command) {
        Order order = orderRepositoryPort.findById(command.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + command.orderId()));

        if (order.getStatut() != OrderStatut.EN_ATTENTE) {
            throw new OrderAlreadyAcknowledgedException(command.orderId());
        }

        Festivalier festivalier = festivalierPort.findById(command.festivalierId())
                .orElseThrow(() -> new IllegalArgumentException("Festivalier not found: " + command.festivalierId()));

        // Build maps of articleId -> quantity for old and new lines
        Map<String, Integer> oldQuantities = new HashMap<>();
        for (OrderLine line : order.getLignes()) {
            oldQuantities.put(line.articleId(), line.quantite());
        }

        Map<String, Integer> newQuantities = new HashMap<>();
        for (ChangeOrderCommand.ArticleItem item : command.articles()) {
            newQuantities.put(item.articleId(), item.quantite());
        }

        // Calculate total token costs
        int oldCost = 0;
        for (Map.Entry<String, Integer> e : oldQuantities.entrySet()) {
            Article article = articleCatalogPort.findById(e.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Article not found: " + e.getKey()));
            oldCost += article.foodType().getTokenCost() * e.getValue();
        }

        int newCost = 0;
        for (Map.Entry<String, Integer> e : newQuantities.entrySet()) {
            Article article = articleCatalogPort.findById(e.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Article not found: " + e.getKey()));
            newCost += article.foodType().getTokenCost() * e.getValue();
        }

        int delta = newCost - oldCost;

        if (delta > 0 && festivalier.getFoodTokenBalance() < delta) {
            throw new InsufficientTokensException("Insufficient food tokens: required " + delta + ", available " + festivalier.getFoodTokenBalance());
        }

        // Deduct or refund tokens (deductFoodTokens accepts negative values to refund)
        festivalier.deductFoodTokens(delta);

        // Adjust stock for any increases in quantity
        for (Map.Entry<String, Integer> e : newQuantities.entrySet()) {
            String articleId = e.getKey();
            int newQ = e.getValue();
            int oldQ = oldQuantities.getOrDefault(articleId, 0);
            int diff = newQ - oldQ;
            if (diff > 0) {
                Stock stock = stockPort.findByArticleId(articleId)
                        .orElseThrow(() -> new IllegalArgumentException("Stock not found for article: " + articleId));
                if (stock.quantite() < diff) {
                    throw new OutOfStockException(
                            "Insufficient stock for article: " + articleId
                                    + " (requested additional: " + diff + ", available: " + stock.quantite() + ")"
                    );
                }
                stockPort.deductStock(articleId, diff);
            }
        }

        List<OrderLine> newLines = command.articles().stream()
                .map(a -> new OrderLine(a.articleId(), a.quantite()))
                .collect(Collectors.toList());

        Order updated = new Order(order.getId(), order.getFestivalierId(), order.getStatut(), newLines);
        orderRepositoryPort.save(updated);
    }
}
