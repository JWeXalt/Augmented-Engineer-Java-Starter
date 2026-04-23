package com.it.exalt.belair.domain.notification;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Service implementing the SendWaterNotificationUseCase and enforcing domain rules:
 * - Allowed window: 11:00 - 19:00 (11AM inclusive, 7PM exclusive)
 * - Standard frequency: every 1 hour
 * - Increased frequency: every 30 minutes when more than 3 alcoholic drinks were
 *   consumed in the past hour
 */
public class WaterNotificationService implements SendWaterNotificationUseCase {

    private static final LocalTime ALLOWED_START = LocalTime.of(11, 0);
    private static final LocalTime ALLOWED_END = LocalTime.of(19, 0);
    private static final Duration STANDARD_INTERVAL = Duration.ofHours(1);
    private static final Duration INCREASED_INTERVAL = Duration.ofMinutes(30);

    private final Clock clock;
    private final WaterNotificationPort notificationPort;
    private final DrinkConsumptionPort drinkConsumptionPort;

    public WaterNotificationService(Clock clock, WaterNotificationPort notificationPort, DrinkConsumptionPort drinkConsumptionPort) {
        this.clock = clock;
        this.notificationPort = notificationPort;
        this.drinkConsumptionPort = drinkConsumptionPort;
    }

    @Override
    public WaterNotificationResult execute(WaterNotificationCommand command) {
        LocalTime currentTime = command.currentTime();
        ZoneId zone = clock.getZone();
        Instant nowInstant = Instant.now(clock);

        boolean withinWindow = !currentTime.isBefore(ALLOWED_START) && currentTime.isBefore(ALLOWED_END);
        if (!withinWindow) {
            // compute duration until next allowed start
            LocalDate today = LocalDate.now(clock);
            LocalDate targetDate = currentTime.isBefore(ALLOWED_START) ? today : today.plusDays(1);
            ZonedDateTime nextStartZdt = ZonedDateTime.of(targetDate, ALLOWED_START, zone);
            Duration untilNext = Duration.between(nowInstant, nextStartZdt.toInstant());
            return new WaterNotificationResult(WaterNotificationResult.Status.SKIPPED, untilNext);
        }

        // within allowed window: evaluate drink consumption in the past hour
        Instant oneHourAgo = nowInstant.minus(1, ChronoUnit.HOURS);
        int alcoholicDrinks = drinkConsumptionPort.countAlcoholicDrinks(command.festivalierId(), oneHourAgo, nowInstant);

        Duration nextInterval = alcoholicDrinks > 3 ? INCREASED_INTERVAL : STANDARD_INTERVAL;

        // send notification
        notificationPort.sendWaterNotification(command.festivalierId());

        return new WaterNotificationResult(WaterNotificationResult.Status.SENT, nextInterval);
    }
}
