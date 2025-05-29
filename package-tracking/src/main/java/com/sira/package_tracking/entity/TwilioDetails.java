package com.sira.package_tracking.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TwilioDetails {
    private final String toPhoneNumber;
    private final String fromPhoneNumber;
    private final String message;
}