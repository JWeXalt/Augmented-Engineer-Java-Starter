package com.it.exalt.belair.domain.notification;

/**
 * Driven port for sending notifications (push, sms, etc.).
 */
public interface WaterNotificationPort {
    void sendWaterNotification(String festivalierId);
}
