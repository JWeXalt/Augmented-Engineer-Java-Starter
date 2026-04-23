package com.it.exalt.belair.domain.order;

import java.util.List;

public record ChangeOrderCommand(String festivalierId, String orderId, List<ArticleItem> articles) {

    public record ArticleItem(String articleId, int quantite) {}
}
