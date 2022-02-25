package gov.iti.jets.server.business.services.impl;

import java.time.LocalDate;

import common.business.dtos.UserAuthDto;
import common.business.dtos.UserDto;
import common.business.services.Mapper;
import gov.iti.jets.server.persistance.entities.UserEntity;

public enum MapperImpl implements Mapper<UserEntity> {
    INSTANCE;

    @Override
    public UserDto mapToUserDto(UserEntity userData) {
        if (userData == null)
            return null;
        return new UserDto(
                userData.phone,
                userData.password,
                userData.name,
                userData.email,
                userData.gender,
                userData.dob.toLocalDate(),
                userData.country,
                userData.bio);
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
                java.sql.Date.valueOf(userDto.dateOfBirth),
                userDto.country,
                userDto.bio);
    }

    public UserEntity mapFromUserAuthDto(UserAuthDto userAuthDto) {
        if (userAuthDto == null)
            return null;
        return new UserEntity(
                userAuthDto.phoneNumber,
                userAuthDto.password);
    }

    @Override
    public UserAuthDto mapToUserAuthDto(UserEntity userData) {
        // TODO Auto-generated method stub
        return null;
    }

}
