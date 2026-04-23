package com.it.exalt.belair.domain.notification;

/**
 * Driving port (use case) for sending water notifications.
 */
public interface SendWaterNotificationUseCase {
    WaterNotificationResult execute(WaterNotificationCommand command);
}
