package com.belair.buvette.domain.order;

/**
 * Command to place a food order.
 * Contains all the information needed to create a new order in the system.
 *
 * @param festivalerId the unique identifier of the festival goer placing the order
 * @param itemName the name of the food item being ordered
 * @param quantity the quantity of items being ordered
 * @param available whether the item is currently available for ordering
 */
public record PlaceOrderCommand(
    String festivalerId,
    String itemName,
    int quantity,
    boolean available
) {
}
