package ru.home.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.home.dto.SubscriptionDto;
import ru.home.dto.SubscriptionResponse;
import ru.home.entity.Subscription;
import ru.home.entity.User;
import ru.home.exception.SubscriptionNotFoundException;
import ru.home.exception.UserAndSubscriptionAlreadyExistsException;
import ru.home.exception.UserNotFoundException;
import ru.home.mapper.SubscriptionMapper;
import ru.home.repository.SubscriptionRepository;
import ru.home.repository.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionDto addSubscription(String email, String serviceName) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found for adding subscription", email);
                    return new UserNotFoundException("User with email " + email + " not found");
                });

        if (subscriptionRepository.existsByServiceNameAndUser_Id(serviceName, user.getId())) {
            log.error("Subscription {} for user {} already exists", serviceName, email);
            throw new UserAndSubscriptionAlreadyExistsException(
                    "Subscription " + serviceName + " for user " + email + " already exists"
            );
        }

        Subscription subscription = new Subscription();
        subscription.setServiceName(serviceName);
        subscription.setUser(user);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        log.info("Added subscription {} for user with email: {}", serviceName, email);
        return subscriptionMapper.toDto(savedSubscription);
    }

    public List<SubscriptionResponse> getSubscriptions(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found for getting subscriptions", email);
                    return new UserNotFoundException("User with email " + email + " not found");
                });
        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionByUser_Id(user.getId());
        log.info("Retrieved {} subscriptions for user with email: {}", subscriptions.size(), email);
        return subscriptions.stream()
                .map(subscriptionMapper::toDtoResponse)
                .collect(Collectors.toList());
    }

    public void deleteSubscription(String email, UUID subscriptionId) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found for deleting subscription", email);
                    return new UserNotFoundException("User with email " + email + " not found");
                });
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> {
                    log.error("Subscription with id {} not found", subscriptionId);
                    return new SubscriptionNotFoundException("Subscription with id " + subscriptionId + " not found");
                });
        if (!subscription.getUser().getId().equals(user.getId())) {
            log.warn("Subscription with id {} does not belong to user with email {}", subscriptionId, email);
            throw new SubscriptionNotFoundException("Subscription does not belong to user");
        }
        subscriptionRepository.deleteById(subscriptionId);
        log.info("Deleted subscription with id {} for user with email: {}", subscriptionId, email);
    }
}