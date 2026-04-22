package com.it.exalt.belair.application.order;

import com.it.exalt.belair.domain.order.PlaceOrderCommand;
import com.it.exalt.belair.domain.order.PlaceOrderResult;
import com.it.exalt.belair.domain.order.PlaceOrderUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * REST controller exposing the order management API.
 * <p>
 * All business logic is delegated to use cases. This controller is responsible only for
 * request validation, command mapping, and response formatting.
 * </p>
 *
 * <p><strong>Note:</strong> Error handling for domain exceptions (e.g. insufficient tokens,
 * out-of-stock) should be moved to a {@code @RestControllerAdvice}.</p>
 */
@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final PlaceOrderUseCase placeOrderUseCase;

    public CommandeController(PlaceOrderUseCase placeOrderUseCase) {
        this.placeOrderUseCase = placeOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<PlaceOrderResponse> placerCommande(@RequestBody PlaceOrderRequest request) {
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
