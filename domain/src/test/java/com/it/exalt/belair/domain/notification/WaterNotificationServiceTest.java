package com.it.exalt.belair.domain.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WaterNotificationServiceTest {

    @Mock
    private WaterNotificationPort notificationPort;

    @Mock
    private DrinkConsumptionPort drinkConsumptionPort;

    @InjectMocks
    private WaterNotificationService service;

    @Test
    void givenWithinAllowedWindowAndLightDrinker_thenSendHourlyNotification() {
        // Given
        ZoneId zone = ZoneOffset.UTC;
        Instant instant = Instant.parse("2026-04-23T12:00:00Z");
        Clock clock = Clock.fixed(instant, zone);

        service = new WaterNotificationService(clock, notificationPort, drinkConsumptionPort);

        when(drinkConsumptionPort.countAlcoholicDrinks("f-1", instant.minus(1, ChronoUnit.HOURS), instant)).thenReturn(2);

        WaterNotificationCommand command = new WaterNotificationCommand("f-1", LocalTime.of(12, 0));

        // When
        WaterNotificationResult result = service.execute(command);

        // Then
        assertEquals(WaterNotificationResult.Status.SENT, result.status());
        assertEquals(Duration.ofHours(1), result.nextNotificationInterval());
        verify(notificationPort).sendWaterNotification("f-1");
    }

    @Test
    void givenWithinAllowedWindowAndHeavyDrinker_thenSendEvery30Minutes() {
        // Given
        ZoneId zone = ZoneOffset.UTC;
        Instant instant = Instant.parse("2026-04-23T12:00:00Z");
        Clock clock = Clock.fixed(instant, zone);

        service = new WaterNotificationService(clock, notificationPort, drinkConsumptionPort);

        when(drinkConsumptionPort.countAlcoholicDrinks("f-2", instant.minus(1, ChronoUnit.HOURS), instant)).thenReturn(5);

        WaterNotificationCommand command = new WaterNotificationCommand("f-2", LocalTime.of(12, 0));

        // When
        WaterNotificationResult result = service.execute(command);

        // Then
        assertEquals(WaterNotificationResult.Status.SENT, result.status());
        assertEquals(Duration.ofMinutes(30), result.nextNotificationInterval());
        verify(notificationPort).sendWaterNotification("f-2");
    }

    @Test
    void givenOutsideAllowedWindow_thenSkipNotification() {
        // Given
        ZoneId zone = ZoneOffset.UTC;
        Instant instant = Instant.parse("2026-04-23T10:00:00Z");
        Clock clock = Clock.fixed(instant, zone);

        service = new WaterNotificationService(clock, notificationPort, drinkConsumptionPort);

        WaterNotificationCommand command = new WaterNotificationCommand("f-3", LocalTime.of(10, 0));

        // When
        WaterNotificationResult result = service.execute(command);

        // Then
        assertEquals(WaterNotificationResult.Status.SKIPPED, result.status());
        // Next notification should be at 11:00 same day
        Duration expected = Duration.between(instant, LocalDateTime.of(LocalDate.of(2026,4,23), LocalTime.of(11,0)).atZone(zone).toInstant());
        assertEquals(expected, result.nextNotificationInterval());
        verifyNoInteractions(notificationPort);
    }
}
