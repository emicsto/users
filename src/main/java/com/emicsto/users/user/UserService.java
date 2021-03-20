package com.emicsto.users.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Validated
class UserService {
    private final UserRepository userRepository;
    private final UserClient userClient;
    private final ModelMapper mapper;

    @Transactional
    public UserOutputDto getUser(String login) {
        User user = userRepository.findByLogin(login).orElse(new User(login, 0));
        user.setRequestCount(user.getRequestCount() + 1);
        userRepository.save(user);

        UserInputDto userInputDto = userClient.getUser(login);
        UserOutputDto userOutputDto = mapper.map(userInputDto, UserOutputDto.class);
        userOutputDto.setCalculations(calculateRepos(userInputDto.getFollowers(), userInputDto.getPublicRepos()));
        return userOutputDto;
    }

    private BigDecimal calculateRepos(@Min(0) @NotNull Integer followers, @Min(0) @NotNull Integer publicRepos) {
        if(followers == 0) {
            return null;
        } else {
            return BigDecimal.valueOf(6).divide(BigDecimal.valueOf(followers), 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(2L + publicRepos));
        }
    }
}
