package com.logistic.gps.application.port.in.command;

public record GpsDistanceCommand(
    String depart,
    String arrival
) {
}
