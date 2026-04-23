package com.it.exalt.belair.domain.notification;

import java.time.Instant;

/**
 * Command to trigger a water notification for a festival goer.
 */
public record WaterNotificationCommand(String festivalierId, Instant now) {
}
