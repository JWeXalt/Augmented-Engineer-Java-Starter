package com.it.exalt.belair.domain.notification;

import java.time.Duration;

/**
 * Result of a water notification attempt.
 */
public record WaterNotificationResult(Outcome outcome, Duration nextNotificationInterval) {
    public enum Outcome { SENT, SKIPPED }
}
