package ru.home.repository;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.home.entity.Subscription;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    List<Subscription> findSubscriptionByUser_Id(UUID userId);

    boolean existsByServiceNameAndUser_Id(String serviceName, UUID userId);

    @Query(value = """
    SELECT service_name, COUNT(*) AS count
    FROM subscriptions
    GROUP BY service_name
    ORDER BY count DESC
    LIMIT 3
    """, nativeQuery = true)
    List<Tuple> findTopSubscriptions();
}
