package com.it.exalt.belair.application.order;

import com.it.exalt.belair.domain.order.PlaceOrderCommand;
import com.it.exalt.belair.domain.order.PlaceOrderResult;
import com.it.exalt.belair.domain.order.PlaceOrderUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Collectors;

public class CommandeController {

    private final PlaceOrderUseCase placeOrderUseCase;

    public CommandeController(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
    }

    public ResponseEntity<PlaceOrderResponse> placerCommande(PlaceOrderRequest request) {
        if (request.festivalierId() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (request.articles() == null || request.articles().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        PlaceOrderCommand command = new PlaceOrderCommand(
                request.festivalierId(),
                request.articles().stream()
                        .map(a -> new PlaceOrderCommand.ArticleItem(a.articleId(), a.quantite()))
                        .collect(Collectors.toList())
        );
        PlaceOrderResult result = placeOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PlaceOrderResponse(result.commandeId(), result.statut()));
    }
}
