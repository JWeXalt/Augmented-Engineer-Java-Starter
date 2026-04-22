package com.it.exalt.belair.infrastructure.article;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "articles")
public class ArticleEntity {

    @Id
    private String id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "food_type")
    private String foodType;

    protected ArticleEntity() {}

    public ArticleEntity(String id, String nom, String foodType) {
        this.id = id;
        this.nom = nom;
        this.foodType = foodType;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getFoodType() { return foodType; }
}
