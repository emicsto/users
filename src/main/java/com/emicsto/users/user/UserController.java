package com.emicsto.users.user;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @ApiOperation(value = "Returns user info")
    @GetMapping("/{login}")
    public ResponseEntity<UserOutputDto> findUser(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUser(login));
    }
}
