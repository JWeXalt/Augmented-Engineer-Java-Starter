package com.it.exalt.belair.application.order;

import java.util.List;

public record PlaceOrderRequest(
        String festivalierId,
        List<ArticleItem> articles
) {
    public record ArticleItem(String articleId, int quantite) {}
}
