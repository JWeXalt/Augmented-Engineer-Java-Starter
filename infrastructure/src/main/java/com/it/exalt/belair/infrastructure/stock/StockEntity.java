package com.it.exalt.belair.infrastructure.stock;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stocks")
public class StockEntity {

    @Id
    @Column(name = "article_id")
    private String articleId;

    @Column(name = "quantite")
    private int quantite;

    protected StockEntity() {}

    public StockEntity(String articleId, int quantite) {
        this.articleId = articleId;
        this.quantite = quantite;
    }

    public String getArticleId() { return articleId; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
