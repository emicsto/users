package com.emicsto.users.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserControllerIntegrationTest {
    private static final String USER_LOGIN = "sample_login";
    private static final long USER_ID = 1L;
    private static final String USER_NAME = "sample_name";
    private static final String USER_TYPE = "sample_type";
    private static final String USER_AVATAR_URL = "sample_avatar_url";
    private static final OffsetDateTime USER_CREATED_AT = OffsetDateTime.of(2020, 12, 29, 12, 10, 5, 0, ZoneOffset.UTC);
    private static final int USER_FOLLOWERS = 2;
    private static final int USER_ZERO_FOLLOWERS = 0;
    private static final int USER_PUBLIC_REPOS = 3;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper mapper;

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
                        .followers(USER_FOLLOWERS)
                        .publicRepos(USER_PUBLIC_REPOS)
                        .build()
        );

        mockMvc.perform(get("/users/" + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(USER_ID), Long.class))
                .andExpect(jsonPath("$.login", is(USER_LOGIN)))
                .andExpect(jsonPath("$.name", is(USER_NAME)))
                .andExpect(jsonPath("$.type", is(USER_TYPE)))
                .andExpect(jsonPath("$.avatarUrl", is(USER_AVATAR_URL)))
                .andExpect(jsonPath("$.createdAt", is(USER_CREATED_AT.toString())))
                .andExpect(jsonPath("$.calculations", comparesEqualTo(BigDecimal.valueOf(15)), BigDecimal.class));

        em.flush();
    }

    @Test
    void shouldReturnNullCalculations_whenUserHasNoFollowers() throws Exception {
        given(userClient.getUser(USER_LOGIN)).willReturn(
                UserInputDto.builder()
                        .id(USER_ID)
                        .login(USER_LOGIN)
                        .name(USER_NAME)
                        .type(USER_TYPE)
                        .avatarUrl(USER_AVATAR_URL)
                        .createdAt(USER_CREATED_AT)
                        .followers(USER_ZERO_FOLLOWERS)
                        .publicRepos(USER_PUBLIC_REPOS)
                        .build()
        );

        mockMvc.perform(get("/users/" + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(USER_ID), Long.class))
                .andExpect(jsonPath("$.login", is(USER_LOGIN)))
                .andExpect(jsonPath("$.name", is(USER_NAME)))
                .andExpect(jsonPath("$.type", is(USER_TYPE)))
                .andExpect(jsonPath("$.avatarUrl", is(USER_AVATAR_URL)))
                .andExpect(jsonPath("$.createdAt", is(USER_CREATED_AT.toString())))
                .andExpect(jsonPath("$.calculations", nullValue()));

        em.flush();
    }

    @Test
    void shouldSaveNewUserToDatabaseAndThenIncrementRequestCountTwice() throws Exception {
        assertThat(userRepository.findByLogin(USER_LOGIN)).isEmpty();

        given(userClient.getUser(USER_LOGIN)).willReturn(
                UserInputDto.builder()
                        .id(USER_ID)
                        .login(USER_LOGIN)
                        .name(USER_NAME)
                        .type(USER_TYPE)
                        .avatarUrl(USER_AVATAR_URL)
                        .createdAt(USER_CREATED_AT)
                        .followers(USER_FOLLOWERS)
                        .publicRepos(USER_PUBLIC_REPOS)
                        .build()
        );

        mockMvc.perform(get("/users/" + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        em.flush();

        User user = userRepository.findByLogin(USER_LOGIN).orElseThrow(() -> new UserNotFoundException(USER_LOGIN));

        assertThat(user.getRequestCount()).isEqualTo(1);


        mockMvc.perform(get("/users/" + USER_LOGIN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        em.flush();

        user = userRepository.findByLogin(USER_LOGIN).orElseThrow(() -> new UserNotFoundException(USER_LOGIN));

        assertThat(user.getRequestCount()).isEqualTo(2);
    }
}
