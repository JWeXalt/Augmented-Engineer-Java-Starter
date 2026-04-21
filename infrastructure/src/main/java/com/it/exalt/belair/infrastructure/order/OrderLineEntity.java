package com.it.exalt.belair.infrastructure.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrderLineEntity {

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "quantite")
    private int quantite;

    protected OrderLineEntity() {}

    public OrderLineEntity(String articleId, int quantite) {
        this.articleId = articleId;
        this.quantite = quantite;
    }

    public String getArticleId() {
        return articleId;
    }

    public int getQuantite() {
        return quantite;
    }
}
