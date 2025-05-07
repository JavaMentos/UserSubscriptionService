package ru.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.entity.Subscription;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findSubscriptionByUser_Id(UUID userId);

    boolean existsByServiceNameAndUser_Id(String serviceName, UUID userId);

}
