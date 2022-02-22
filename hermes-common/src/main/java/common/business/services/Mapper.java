package common.business.services;

import common.business.dtos.UserDto;

public interface Mapper<T> {
    public UserDto mapToUserDto(T userData);
    public T mapFromUserDto(UserDto userDto);
}
