package ru.home.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record SubscriptionDto(
        @JsonProperty("service_name") String serviceName,
        @JsonProperty("user_email") String userEmail) {}