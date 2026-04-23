package com.it.exalt.belair.domain.notification;

/**
 * Driven port to send water notification to a festival goer using any channel (push, SMS...)
 */
public interface WaterNotificationPort {

    void sendWaterNotification(String festivalierId);
}
