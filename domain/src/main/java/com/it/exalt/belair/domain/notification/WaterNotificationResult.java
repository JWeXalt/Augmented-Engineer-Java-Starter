package com.it.exalt.belair.domain.notification;

import java.time.Duration;

public record WaterNotificationResult(WaterNotificationResult.Status status, Duration nextNotificationInterval) {

    public enum Status {
        SENT,
        SKIPPED
    }
}
