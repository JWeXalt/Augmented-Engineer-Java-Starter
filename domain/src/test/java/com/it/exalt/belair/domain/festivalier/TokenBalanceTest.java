package com.it.exalt.belair.domain.festivalier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenBalanceTest {

    @Test
    void givenValidTokenCounts_whenCreatingTokenBalance_thenBalanceIsCreatedSuccessfully() {
        // Given / When
        TokenBalance balance = new TokenBalance(9, 6);

        // Then
        assertEquals(9, balance.foodTokens());
        assertEquals(6, balance.drinkTokens());
    }

    @Test
    void givenZeroTokenCounts_whenCreatingTokenBalance_thenBalanceIsCreatedSuccessfully() {
        // Given / When
        TokenBalance balance = new TokenBalance(0, 0);

        // Then
        assertEquals(0, balance.foodTokens());
        assertEquals(0, balance.drinkTokens());
    }

    @Test
    void givenNegativeFoodTokens_whenCreatingTokenBalance_thenNegativeTokenBalanceExceptionIsThrown() {
        // Given / When / Then
        assertThrows(NegativeTokenBalanceException.class, () -> new TokenBalance(-1, 0));
    }

    @Test
    void givenNegativeDrinkTokens_whenCreatingTokenBalance_thenNegativeTokenBalanceExceptionIsThrown() {
        // Given / When / Then
        assertThrows(NegativeTokenBalanceException.class, () -> new TokenBalance(0, -1));
    }

    @Test
    void givenBothNegative_whenCreatingTokenBalance_thenNegativeTokenBalanceExceptionIsThrown() {
        // Given / When / Then
        assertThrows(NegativeTokenBalanceException.class, () -> new TokenBalance(-3, -2));
    }

    @Test
    void whenRequestingDailyAllocation_thenReturns9FoodAnd6DrinkTokens() {
        // Given / When
        TokenBalance daily = TokenBalance.dailyAllocation();

        // Then
        assertNotNull(daily);
        assertEquals(9, daily.foodTokens());
        assertEquals(6, daily.drinkTokens());
    }

    @Test
    void givenBalance_whenDeductingFoodTokens_thenFoodBalanceDecreasesAndDrinkBalanceUnchanged() {
        // Given
        TokenBalance balance = new TokenBalance(9, 6);

        // When
        TokenBalance result = balance.deductFood(3);

        // Then
        assertEquals(6, result.foodTokens());
        assertEquals(6, result.drinkTokens());
    }

    @Test
    void givenBalance_whenDeductingDrinkTokens_thenDrinkBalanceDecreasesAndFoodBalanceUnchanged() {
        // Given
        TokenBalance balance = new TokenBalance(9, 6);

        // When
        TokenBalance result = balance.deductDrink(2);

        // Then
        assertEquals(9, result.foodTokens());
        assertEquals(4, result.drinkTokens());
    }

    @Test
    void givenBalance_whenDeductingMoreFoodTokensThanAvailable_thenNegativeTokenBalanceExceptionIsThrown() {
        // Given
        TokenBalance balance = new TokenBalance(2, 6);

        // When / Then
        assertThrows(NegativeTokenBalanceException.class, () -> balance.deductFood(3));
    }

    @Test
    void givenBalance_whenDeductingMoreDrinkTokensThanAvailable_thenNegativeTokenBalanceExceptionIsThrown() {
        // Given
        TokenBalance balance = new TokenBalance(9, 1);

        // When / Then
        assertThrows(NegativeTokenBalanceException.class, () -> balance.deductDrink(2));
    }

    @Test
    void givenBalance_whenDeductingAllFoodTokens_thenFoodBalanceIsZero() {
        // Given
        TokenBalance balance = new TokenBalance(5, 3);

        // When
        TokenBalance result = balance.deductFood(5);

        // Then
        assertEquals(0, result.foodTokens());
        assertEquals(3, result.drinkTokens());
    }

    @Test
    void givenTokenBalance_whenOriginalIsUnchangedAfterDeduction_thenTokenBalanceIsImmutable() {
        // Given
        TokenBalance original = new TokenBalance(9, 6);

        // When
        original.deductFood(3);
        original.deductDrink(2);

        // Then — original must not have changed
        assertEquals(9, original.foodTokens());
        assertEquals(6, original.drinkTokens());
    }

    @Test
    void givenDailyAllocationConstants_whenCheckingValues_thenDailyFoodIs9AndDailyDrinkIs6() {
        // Given / When / Then
        assertEquals(9, TokenBalance.DAILY_FOOD_TOKENS);
        assertEquals(6, TokenBalance.DAILY_DRINK_TOKENS);
    }
}
