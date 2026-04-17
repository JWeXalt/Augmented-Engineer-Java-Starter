package com.belair.buvette.domain.order;

public class PlaceOrderCommand {
    private final String customerId;
    private final String itemName;
    private final int quantity;
    private final boolean available;

    public PlaceOrderCommand(String customerId, String itemName, int quantity, boolean available) {
        this.customerId = customerId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.available = available;
    }

    public String customerId() { return customerId; }
    public String itemName() { return itemName; }
    public int quantity() { return quantity; }
    public boolean available() { return available; }
}
