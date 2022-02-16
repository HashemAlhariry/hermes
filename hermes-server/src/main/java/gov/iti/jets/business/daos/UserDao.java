package gov.iti.jets.business.daos;

import java.sql.SQLException;
import java.util.List;

import gov.iti.jets.business.dtos.UserDto;
import gov.iti.jets.persistance.entities.UserEntity;

public interface UserDao {

	List<UserEntity> getAllUsers() throws SQLException;
	UserEntity getUserByPhone(UserDto userDto);
	void insertUser(UserEntity user);
	void updateUser(UserEntity user);
	void deleteUser(UserEntity user);
 	UserEntity getUserRegistered(UserDto userDto);
	
}
