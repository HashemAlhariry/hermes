package gov.iti.jets.client.business.services.impl;

import common.business.dtos.UserDto;
import common.business.services.Mapper;
import gov.iti.jets.client.business.services.util.HashManager;
import gov.iti.jets.client.presentation.models.UserModel;

public enum MapperImpl implements Mapper<UserModel> {
    INSTANCE;

    @Override
    public UserDto mapToUserDto(UserModel userModel) {
        // encrypt incoming userpassword
        // map user data
        if (userModel == null) {
            return null;
        }

        String encryptedPassword = HashManager.INSTANCE.generateSecurePassword(userModel.getPassword());

        return new UserDto(
                userModel.getPhoneNumber(),
                encryptedPassword,
                userModel.getUserName(),
                userModel.getEmail(),
                userModel.getGender(),
                userModel.getDateOfBirth(),
                userModel.getCountry(),
                userModel.getBio());
    }

    @Override
    public UserModel mapFromUserDto(UserDto userDto) {
        return null;
    }

}
