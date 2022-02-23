package gov.iti.jets.server.business.daos;

import java.sql.SQLException;
import java.util.List;
import common.business.dtos.UserDto;
import gov.iti.jets.server.persistance.entities.UserEntity;

public interface UserDao {

	List<UserEntity> getAllUsers() throws SQLException;

	UserEntity getUserByPhone(UserDto userDto);

	void insertUser(UserEntity user);

	void updateUser(UserEntity user);

	void deleteUser(UserEntity user);

}
