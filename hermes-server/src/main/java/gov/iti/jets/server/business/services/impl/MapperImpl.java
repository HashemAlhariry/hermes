package gov.iti.jets.server.business.services.impl;

import common.business.dtos.UserDto;
import common.business.services.Mapper;
import gov.iti.jets.server.persistance.entities.UserEntity;

public enum MapperImpl implements Mapper<UserEntity> {
    INSTANCE;

    @Override
    public UserDto mapToUserDto(UserEntity userData) {
        return null;
    }

    @Override
    public UserEntity mapFromUserDto(UserDto userDto) {
        if (userDto == null)
            return null;
        return new UserEntity(
                userDto.phoneNumber,
                userDto.password,
                userDto.name,
                userDto.email,
                userDto.gender,
                userDto.country,
                userDto.dateOfBirth,
                userDto.bio);
    }

}
