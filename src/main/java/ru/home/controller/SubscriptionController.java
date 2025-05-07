package ru.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.home.dto.SubscriptionDto;
import ru.home.dto.SubscriptionResponse;
import ru.home.service.SubscriptionService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @Operation(summary = "Add a subscription to a user", description = "Adds a new subscription to the user with the specified email.")
    @ApiResponse(responseCode = "201", description = "Subscription added successfully",
            content = @Content(schema = @Schema(implementation = SubscriptionDto.class)))
    public ResponseEntity<SubscriptionDto> addSubscription(@RequestBody SubscriptionDto subscription) {

        SubscriptionDto signed = subscriptionService.addSubscription(subscription.userEmail(), subscription.serviceName());

        return ResponseEntity.status(HttpStatus.CREATED).body(signed);
    }

    @GetMapping("/{email}")
    @Operation(summary = "Get subscriptions of a user", description = "Retrieves all subscriptions of the user with the specified email.")
    @ApiResponse(responseCode = "200", description = "Subscriptions retrieved successfully",
            content = @Content(schema = @Schema(implementation = SubscriptionDto.class)))
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptions(@PathVariable String email) {

        List<SubscriptionResponse> subscriptions = subscriptionService.getSubscriptions(email);

        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{email}/{subscriptionId}")
    @Operation(summary = "Delete a subscription of a user", description = "Deletes a subscription by its ID for the user with the specified email.")
    @ApiResponse(responseCode = "204", description = "Subscription deleted successfully")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String email, @PathVariable UUID subscriptionId) {

        subscriptionService.deleteSubscription(email, subscriptionId);

        return ResponseEntity.noContent().build();
    }
}