package in.stackroute.user_service.service;

import in.stackroute.user_service.dto.UserCreatedDto;
import in.stackroute.user_service.dto.UserDto;
import in.stackroute.user_service.dto.ValidUserDto;

public interface UserService {

    UserCreatedDto save(UserDto userDto);

    ValidUserDto authenticate(UserDto userDto);
}
