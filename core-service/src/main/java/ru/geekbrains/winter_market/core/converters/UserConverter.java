package ru.geekbrains.winter_market.core.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.api.UserDto;
import ru.geekbrains.winter_market.core.entities.User;

@Component
public class UserConverter {
    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
