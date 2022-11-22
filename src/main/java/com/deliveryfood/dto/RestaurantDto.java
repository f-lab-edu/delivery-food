package com.deliveryfood.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class RestaurantDto {

    public enum FoodCategory {
        NONE,
        KOREAN,
        SNACK,
        CAFE,
        JAPANESE,
        CHICKEN,
        PIZZA,
        ASIAN,
        WESTERN,
        FASTFOOD
    }

    public enum Status {
        NONE,
        RUN,        // 운영
        CLOSURE     // 폐업
    }

    @NonNull
    private String restaurantId;
    @NonNull
    private String userId;
    @NonNull
    private String name;
    @NonNull
    private Integer businessId;
    @NonNull
    private FoodCategory foodCategory;
    @NonNull
    private String address;
    @NonNull
    private String phone;
    @NonNull
    private String openingHours;
    @NonNull
    private Integer distance;
    @NonNull
    private Status status;
    @NonNull
    private LocalDateTime regDt;
    @NonNull
    private LocalDateTime udtDt;
}