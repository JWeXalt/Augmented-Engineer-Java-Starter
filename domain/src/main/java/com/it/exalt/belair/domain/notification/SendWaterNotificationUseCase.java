package com.it.exalt.belair.domain.notification;

public interface SendWaterNotificationUseCase {

    WaterNotificationResult execute(WaterNotificationCommand command);
}
