package com.it.exalt.belair.domain.notification;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Service implementation of the water notification use case.
 */
public class WaterNotificationService implements SendWaterNotificationUseCase {

    private static final LocalTime WINDOW_START = LocalTime.of(11, 0);
    private static final LocalTime WINDOW_END = LocalTime.of(19, 0);

    private final DrinkConsumptionPort drinkConsumptionPort;
    private final WaterNotificationPort waterNotificationPort;
    private final Clock clock;

    public WaterNotificationService(DrinkConsumptionPort drinkConsumptionPort,
                                    WaterNotificationPort waterNotificationPort,
                                    Clock clock) {
        this.drinkConsumptionPort = drinkConsumptionPort;
        this.waterNotificationPort = waterNotificationPort;
        this.clock = clock;
    }

    @Override
    public WaterNotificationResult execute(WaterNotificationCommand command) {
        Instant nowInstant = command.now();
        ZoneId zone = clock.getZone();
        LocalTime currentLocalTime = ZonedDateTime.ofInstant(nowInstant, zone).toLocalTime();

        if (currentLocalTime.isBefore(WINDOW_START) || !currentLocalTime.isBefore(WINDOW_END)) {
            // Outside allowed window
            Duration untilNextStart = computeUntilNextWindowStart(nowInstant, zone);
            return new WaterNotificationResult(WaterNotificationResult.Outcome.SKIPPED, untilNextStart);
        }

        // Inside allowed window
        Instant from = nowInstant.minus(Duration.ofHours(1));
        int alcoholicDrinks = drinkConsumptionPort.countAlcoholicDrinks(command.festivalierId(), from, nowInstant);
        Duration nextInterval;
        if (alcoholicDrinks > 3) {
            nextInterval = Duration.ofMinutes(30);
        } else {
            nextInterval = Duration.ofHours(1);
        }

        waterNotificationPort.sendWaterNotification(command.festivalierId());
        return new WaterNotificationResult(WaterNotificationResult.Outcome.SENT, nextInterval);
    }

    private Duration computeUntilNextWindowStart(Instant nowInstant, ZoneId zone) {
        ZonedDateTime nowZdt = ZonedDateTime.ofInstant(nowInstant, zone);
        ZonedDateTime todayStart = nowZdt.withHour(WINDOW_START.getHour()).withMinute(WINDOW_START.getMinute()).withSecond(0).withNano(0);
        if (nowZdt.isBefore(todayStart)) {
            return Duration.between(nowZdt, todayStart);
        }
        // after today's window start -> next start is tomorrow
        ZonedDateTime tomorrowStart = todayStart.plusDays(1);
        return Duration.between(nowZdt, tomorrowStart);
    }
}
