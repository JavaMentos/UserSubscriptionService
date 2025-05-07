package ru.home.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.home.dto.SubscriptionDto;
import ru.home.dto.SubscriptionResponse;
import ru.home.entity.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "userEmail", source = "user.email")
    SubscriptionDto toDto(Subscription subscription);

    SubscriptionResponse toDtoResponse(Subscription subscription);
}