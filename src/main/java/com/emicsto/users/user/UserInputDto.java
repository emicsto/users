package com.emicsto.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
public class UserInputDto {
    @NotNull
    private Long id;

    @NotNull
    private String login;

    private String name;

    @NotNull
    private String type;

    @NotNull
    @JsonProperty("avatar_url")
    private String avatarUrl;

    @NotNull
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @Min(0)
    @NotNull
    private Integer followers;

    @Min(0)
    @NotNull
    @JsonProperty("public_repos")
    private Integer publicRepos;
}
