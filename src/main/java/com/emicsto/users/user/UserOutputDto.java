package com.emicsto.users.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
class UserOutputDto {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private OffsetDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal calculations;
}
