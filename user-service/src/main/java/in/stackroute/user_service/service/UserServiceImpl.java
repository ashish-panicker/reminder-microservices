package in.stackroute.user_service.service;

import in.stackroute.user_service.dto.UserCreatedDto;
import in.stackroute.user_service.dto.UserDto;
import in.stackroute.user_service.dto.ValidUserDto;
import in.stackroute.user_service.exceptions.InvalidUserDetailException;
import in.stackroute.user_service.model.User;
import in.stackroute.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserCreatedDto save(UserDto userDto) {
        log.info("Saving user {} ", userDto);
        User user = userRepository.save(toEntity(userDto));
        return new UserCreatedDto(
                user.getId(),
                String.format("Account for '%s' created successfully ", user.getUserName())
        );
    }

    @Override
    public ValidUserDto authenticate(UserDto userDto) {
        User user = userRepository.findByUserName(userDto.userName())
                .orElseThrow(() -> new InvalidUserDetailException("User not found"));
        if (!user.getPassword().equals(userDto.password())) {
            throw new InvalidUserDetailException("Invalid password");
        }
        return new ValidUserDto(
                user.getId(),
                String.format("Welcome '%s'", user.getUserName())
        );
    }

    private User toEntity(UserDto userDto) {
        log.info("Converting userDto {} to entity", userDto);
        return User.builder()
                .userName(userDto.userName())
                .password(userDto.password())
                .build();
    }
}
