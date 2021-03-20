package com.emicsto.users.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {
    private static final String USER_LOGIN = "sample_login";
    private static final long USER_ID = 1L;
    private static final String USER_NAME = "sample_name";
    private static final String USER_TYPE = "sample_type";
    private static final String USER_AVATAR_URL = "sample_avatar_url";
    private static final ZonedDateTime USER_CREATED_AT = ZonedDateTime.of(2020, 12, 29, 12, 0, 0, 0, ZoneId.of("UTC"));
    private static final int USER_120_FOLLOWERS = 120;
    private static final int USER_3_PUBLIC_REPOS = 3;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserClient userClient;

    @Test
    void shouldReturnValidUserData() throws Exception {
        given(userClient.getUser(USER_LOGIN)).willReturn(
                UserInputDto.builder()
                        .id(USER_ID)
                        .login(USER_LOGIN)
                        .name(USER_NAME)
                        .type(USER_TYPE)
                        .avatarUrl(USER_AVATAR_URL)
                        .createdAt(USER_CREATED_AT)
                        .followers(USER_120_FOLLOWERS)
                        .publicRepos(USER_3_PUBLIC_REPOS)
                        .build()
        );

        mockMvc.perform(get("/users/" + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(USER_ID)))
        .andExpect(jsonPath("$.login", is(USER_LOGIN)))
        .andExpect(jsonPath("$.name", is(USER_NAME)))
        .andExpect(jsonPath("$.type", is(USER_TYPE)))
        .andExpect(jsonPath("$.avatarUrl", is(USER_AVATAR_URL)))
        .andExpect(jsonPath("$.createdAt", is(USER_CREATED_AT)))
        .andExpect(jsonPath("$.calculations", is(100)));
    }
}
