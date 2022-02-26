package gov.iti.jets.client.business.services.impl;

import java.time.LocalDate;

import common.business.dtos.UserAuthDto;
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

		String saltValue = HashManager.INSTANCE.getSaltvalue(15);
		String encryptedPassword = HashManager.INSTANCE.generateSecurePassword(userModel.getPassword(),
				saltValue);

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
		if (userDto == null) {
			return null;
		}
		UserModel userModel = new UserModel();

		// TODO: Implement Image mapping  
		if (userDto.phoneNumber != null && !userDto.phoneNumber.isBlank())
			userModel.setPhoneNumber(userDto.phoneNumber);
		if (userDto.name != null && !userDto.name.isBlank())
			userModel.setUserName(userDto.name);
		if (userDto.email != null && !userDto.email.isBlank())
			userModel.setEmail(userDto.email);
		// password should never be mapped
		// if (userDto.password!= null && !userDto.password.isBlank())
		// userModel.setPassword(userDto.password);
		if (userDto.dateOfBirth != null)
			userModel.setDateOfBirth(userDto.dateOfBirth);
		if (userDto.country != null && !userDto.country.isBlank())
			userModel.setCountry(userDto.country);
		if (userDto.gender != null)
			userModel.setGender(userDto.gender);
		return userModel;
	}

	@Override
	public UserAuthDto mapToUserAuthDto(UserModel userModel) {

		if (userModel == null) {
			return null;
		}

		return new UserAuthDto(
				userModel.getPhoneNumber(), userModel.getPassword());
	}

	@Override
	public UserModel mapFromUserAuthDto(UserAuthDto ustherAuDto) {
		return null;
	}

}
