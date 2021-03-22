package com.emicsto.users.user;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(notes = "Result of calculation 6/followers * (2 + public_repos) of scale 5. " +
            "Returns null if followers are equal to 0.")
    private BigDecimal calculations;
}
