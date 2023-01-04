package ru.geekbrains.winter_market.converters;

import org.springframework.stereotype.Component;
import ru.geekbrains.winter_market.dtos.UserDto;
import ru.geekbrains.winter_market.entities.User;

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
