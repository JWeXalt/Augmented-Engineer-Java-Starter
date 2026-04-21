package com.it.exalt.belair.domain.order;

import java.util.List;

public record PlaceOrderCommand(String festivalierId, List<ArticleItem> articles) {

    public record ArticleItem(String articleId, int quantite) {}
}
