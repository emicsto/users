package com.emicsto.users.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

@Validated
@FeignClient(value= "users", url = "https://api.github.com/users")
public interface UserClient {

    @Valid
    @GetMapping( "/{login}")
    UserInputDto getUser(@PathVariable String login);
}
