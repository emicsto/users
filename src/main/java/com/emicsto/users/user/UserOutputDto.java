package com.emicsto.users.user;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
class UserOutputDto {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private ZonedDateTime createdAt;
    private BigDecimal calculations;
}
