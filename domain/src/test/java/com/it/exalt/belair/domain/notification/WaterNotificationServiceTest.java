package com.it.exalt.belair.domain.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WaterNotificationServiceTest {

    @Mock
    private DrinkConsumptionPort drinkConsumptionPort;

    @Mock
    private WaterNotificationPort waterNotificationPort;

    private Clock clock;
    private WaterNotificationService service;

    @BeforeEach
    void setUp() {
        // default zone for tests
        ZoneId zone = ZoneId.systemDefault();
        // clock will be initialized in each test with a fixed instant
        clock = Clock.system(zone);
    }

    @Test
    void givenWithinAllowedWindow_andLightDrinker_whenExecute_thenNotificationSentHourly() {
        // 2026-04-23 12:00
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2026, 4, 23, 12, 0), ZoneId.systemDefault());
        Instant now = zdt.toInstant();
        clock = Clock.fixed(now, ZoneId.systemDefault());

        service = new WaterNotificationService(drinkConsumptionPort, waterNotificationPort, clock);

        when(drinkConsumptionPort.countAlcoholicDrinks(any(), any(), any())).thenReturn(2);

        WaterNotificationCommand command = new WaterNotificationCommand("festivalier-1", now);
        WaterNotificationResult result = service.execute(command);

        assertEquals(WaterNotificationResult.Outcome.SENT, result.outcome());
        assertEquals(Duration.ofHours(1), result.nextNotificationInterval());
        verify(waterNotificationPort, times(1)).sendWaterNotification("festivalier-1");
    }

    @Test
    void givenWithinAllowedWindow_andHeavyDrinker_whenExecute_thenNotificationSentEvery30Minutes() {
        // 2026-04-23 15:00
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2026, 4, 23, 15, 0), ZoneId.systemDefault());
        Instant now = zdt.toInstant();
        clock = Clock.fixed(now, ZoneId.systemDefault());

        service = new WaterNotificationService(drinkConsumptionPort, waterNotificationPort, clock);

        when(drinkConsumptionPort.countAlcoholicDrinks(any(), any(), any())).thenReturn(5);

        WaterNotificationCommand command = new WaterNotificationCommand("festivalier-2", now);
        WaterNotificationResult result = service.execute(command);

        assertEquals(WaterNotificationResult.Outcome.SENT, result.outcome());
        assertEquals(Duration.ofMinutes(30), result.nextNotificationInterval());
        verify(waterNotificationPort, times(1)).sendWaterNotification("festivalier-2");
    }

    @Test
    void givenOutsideAllowedWindow_whenExecute_thenSkippedAndNextIsUntilNextStart() {
        // 2026-04-23 09:30 (before 11:00)
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2026, 4, 23, 9, 30), ZoneId.systemDefault());
        Instant now = zdt.toInstant();
        clock = Clock.fixed(now, ZoneId.systemDefault());

        service = new WaterNotificationService(drinkConsumptionPort, waterNotificationPort, clock);

        WaterNotificationCommand command = new WaterNotificationCommand("festivalier-3", now);
        WaterNotificationResult result = service.execute(command);

        assertEquals(WaterNotificationResult.Outcome.SKIPPED, result.outcome());
        // next should be until today 11:00 -> 1h30m
        assertEquals(Duration.ofMinutes(90), result.nextNotificationInterval());
        verify(waterNotificationPort, never()).sendWaterNotification(any());
    }
}
