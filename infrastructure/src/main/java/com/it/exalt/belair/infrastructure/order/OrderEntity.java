package com.it.exalt.belair.infrastructure.order;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    private String id;

    @Column(name = "festivalier_id")
    private String festivalierId;

    @Column(name = "statut")
    private String statut;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_lignes", joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderLineEntity> lignes = new ArrayList<>();

    protected OrderEntity() {}

    public OrderEntity(String id, String festivalierId, String statut, List<OrderLineEntity> lignes) {
        this.id = id;
        this.festivalierId = festivalierId;
        this.statut = statut;
        this.lignes = new ArrayList<>(lignes);
    }

    public String getId() {
        return id;
    }

    public String getFestivalierId() {
        return festivalierId;
    }

    public String getStatut() {
        return statut;
    }

    public List<OrderLineEntity> getLignes() {
        return lignes;
    }
}
