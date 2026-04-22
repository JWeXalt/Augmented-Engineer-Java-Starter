# Bel'Air's Buvette — Project Status

This document tracks the current implementation state of the Bel'Air's Buvette backend,
describing what is implemented, what is pending, known gaps, and the overall architecture
as it stands today.

---

## Architecture Overview

The project follows **Hexagonal Architecture (Ports and Adapters)**, structured as three Gradle modules:

```
+-------------------+         +-------------------+         +------------------------+
|   Application     |  --->   |      Domain       |  <---   |     Infrastructure     |
| (Controllers,DTOs)|         | (Entities, Ports, |         | (JPA Repositories,     |
|                   |         |  Use Cases)       |         |  Adapters)             |
+-------------------+         +-------------------+         +------------------------+
```

| Module | Artifact ID | Responsibility |
|--------|------------|----------------|
| `application/` | `belair-buvette-application` | REST controllers, DTOs, Spring Boot entry point |
| `domain/` | `belair-buvette-domain` | Business logic, entities, ports, use cases |
| `infrastructure/` | `belair-buvette-infrastructure` | JPA persistence adapters, in-memory stubs |

**Tech Stack:** Java 21 · Spring Boot · H2 (embedded) · Spring Data JPA · JUnit 5 · Mockito · Gradle

---

## Domain Model

### Entities

| Class | Module | Description |
|-------|--------|-------------|
| `Order` | domain | Aggregate root. Fields: `id`, `festivalierId`, `statut`, `lignes` |
| `Festivalier` | domain | Festival goer. Fields: `id`, `foodTokenBalance` |

### Value Objects / Records

| Class | Module | Description |
|-------|--------|-------------|
| `OrderLine` | domain | One line in an order: `articleId`, `quantite` |
| `Article` | domain | Catalog item: `id`, `nom`, `foodType` |
| `Stock` | domain | Inventory state: `articleId`, `quantite` |

### Enumerations

| Enum | Values | Notes |
|------|--------|-------|
| `OrderStatut` | `EN_ATTENTE`, `PRETE`, `ANNULEE` | Missing `ACKNOWLEDGED` status (needed for acknowledge-order feature) |
| `FoodType` | `SNACK` (1 token), `MEAL` (3 tokens) | Token cost encoded as `getTokenCost()` |

---

## Feature Implementation Status

| Feature | Domain | Application | Infrastructure | Notes |
|---------|--------|-------------|---------------|-------|
| ✅ Place food order | `PlaceOrderService` | `POST /commandes` | JPA adapters | Fully wired |
| ⚠️ Cancel order | `CancelOrderService` | ❌ No endpoint | JPA adapters | Domain service exists; no REST endpoint; missing use-case interface |
| 🔲 Acknowledge order | ❌ | ❌ | ❌ | Not started; `OrderStatut.ACKNOWLEDGED` missing |
| 🔲 Mark order ready | ❌ | ❌ | ❌ | Not started |
| 🔲 Order multiple items | ❌ | ❌ | ❌ | Multiple `OrderLine`s already supported in `Order` model |
| 🔲 Group order | ❌ | ❌ | ❌ | Not started |
| 🔲 Change order | ❌ | ❌ | ❌ | Not started |
| 🔲 Review change request | ❌ | ❌ | ❌ | Not started |
| 🔲 Transfer tokens | ❌ | ❌ | ❌ | Not started |
| 🔲 Water notifications | ❌ | ❌ | ❌ | Not started |

**Legend:** ✅ Done · ⚠️ Partial · 🔲 Not started · ❌ Missing component

---

## REST API

| Method | Path | Status Code(s) | Description |
|--------|------|---------------|-------------|
| `POST` | `/commandes` | 201 / 400 / 401 | Place a food order |

### `POST /commandes` — Place food order

**Request body:**
```json
{
  "festivalierId": "string",
  "articles": [
    { "articleId": "string", "quantite": 1 }
  ]
}
```

**Response body (201 Created):**
```json
{
  "commandeId": "string",
  "statut": "EN_ATTENTE"
}
```

**Error cases:**
- `401 UNAUTHORIZED` — `festivalierId` is null
- `400 BAD_REQUEST` — `articles` list is null or empty
- `500 INTERNAL_SERVER_ERROR` — domain exceptions (no `@RestControllerAdvice` yet)

---

## Ports and Adapters

### Driven Ports (defined in Domain, implemented in Infrastructure)

| Port Interface | Implementation | Status |
|---------------|---------------|--------|
| `OrderRepositoryPort` | `OrderRepositoryAdapter` (JPA) | ✅ Implemented |
| `FestivalierPort` | `FestivalierRepositoryAdapter` (JPA) | ✅ Implemented |
| `ArticleCatalogPort` | `InMemoryArticleCatalogAdapter` | ⚠️ Stub — always returns `Optional.empty()` |
| `StockPort` | `InMemoryStockAdapter` | ⚠️ Stub — no-op `deductStock`, empty `findByArticleId` |

### Primary Ports (Use Cases — defined in Domain, called from Application)

| Use Case Interface | Implementation | Wired in DI? |
|-------------------|---------------|-------------|
| `PlaceOrderUseCase` | `PlaceOrderService` | ✅ `DomainConfiguration` |
| _(none)_ | `CancelOrderService` | ❌ No interface; not registered in Spring context |

---

## Known Issues and Gaps

> These are implementation issues discovered during documentation review. They should be addressed before the corresponding features are considered complete.

### 🐛 Critical

1. **`PlaceOrderService` does not persist the festivalier after token deduction.**  
   `festivalier.deductFoodTokens(totalCost)` is called but `festivalierPort.save(festivalier)` is never
   invoked. Token balances are not actually persisted.  
   _File:_ `domain/.../order/PlaceOrderService.java` line 56

2. **`Order` created without `festivalierId` or `lignes`.**  
   `new Order(UUID.randomUUID().toString(), OrderStatut.EN_ATTENTE)` does not link the order to the
   festivalier, nor does it include the ordered lines. The association and order content are lost.  
   _File:_ `domain/.../order/PlaceOrderService.java` line 62

3. **`CancelOrderService` has no Use Case interface.**  
   It is not a Spring bean — there is no `@Component` annotation and no entry in `DomainConfiguration`.
   The cancel-order domain logic cannot currently be called from the Application layer.

### ⚠️ Important

4. **No `@RestControllerAdvice`** — domain exceptions (`InsufficientTokensException`,
   `OutOfStockException`, `OrderAlreadyAcknowledgedException`) will bubble up as `500 Internal Server
   Error` instead of meaningful HTTP responses.

5. **`Festivalier` only tracks food tokens** — `FEATURES.md` specifies both drink tokens and food tokens
   (9 food + 6 drink per day). Only `foodTokenBalance` exists; drink token balance is entirely absent.

6. **`OrderStatut` is missing `ACKNOWLEDGED` status** — required for the acknowledge-order feature
   and for enforcing the rule "cancel only if not yet acknowledged."

7. **Article catalog and stock are empty stubs** — `InMemoryArticleCatalogAdapter` always returns
   `Optional.empty()` and `InMemoryStockAdapter` has no-op implementations. Any real order placement
   will throw `IllegalArgumentException("Article not found")`.

8. **No OpenAPI/Swagger documentation** — documentation guidelines require all REST endpoints to be
   documented using OpenAPI annotations.

9. **No Javadoc on public classes and methods** — documentation guidelines require Javadoc on all
   public API surfaces; none of the source files currently have it.

---

## Test Coverage

| Test Class | Type | Scope | Tests |
|-----------|------|-------|-------|
| `PlaceOrderUseCaseTest` | Unit | Domain | 6 |
| `CancelOrderUseCaseTest` | Unit | Domain | 2 |
| `CommandeControllerTest` | Unit (MockMvc) | Application | 3 |
| `InMemoryOrderAdapterTest` | Integration (`@DataJpaTest`) | Infrastructure | 3 |
| `FestivalierRepositoryAdapterTest` | Integration (`@DataJpaTest`) | Infrastructure | 3 |

**Total: 17 tests** · Framework: JUnit 5 + Mockito

---

## Next Priorities (suggested)

1. Fix the critical bugs in `PlaceOrderService` (festivalier not saved, order lignes not stored)
2. Add `CancelOrderUseCase` interface and wire `CancelOrderService` as a Spring bean
3. Add `DELETE /commandes/{id}` endpoint for cancel order
4. Implement real `InMemoryArticleCatalogAdapter` and `InMemoryStockAdapter` with seeded data
5. Add `@RestControllerAdvice` for exception-to-HTTP mapping
6. Add `ACKNOWLEDGED` to `OrderStatut`
7. Add drink token balance to `Festivalier`
8. Implement the acknowledge-order use case (domain + application + infrastructure)
