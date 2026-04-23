package com.it.exalt.belair.domain.notification;

import java.time.LocalTime;

public record WaterNotificationCommand(String festivalierId, LocalTime currentTime) {
}
