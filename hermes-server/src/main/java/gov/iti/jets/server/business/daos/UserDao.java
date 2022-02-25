package gov.iti.jets.server.business.daos;

import java.sql.SQLException;
import java.util.List;

<<<<<<< Updated upstream
=======
import common.business.dtos.UserAuthDto;
>>>>>>> Stashed changes
import common.business.dtos.UserDto;
import gov.iti.jets.server.persistance.entities.UserEntity;

public interface UserDao {

	List<UserEntity> getAllUsers() throws SQLException;

	UserEntity getUserByPhone(UserAuthDto userAuthDto);

	void insertUser(UserEntity user);

	void updateUser(UserEntity user);

	void deleteUser(UserEntity user);

<<<<<<< Updated upstream
	UserEntity getUserRegistered(UserDto userDto);
=======
	boolean loginUser(UserEntity userEntity);
>>>>>>> Stashed changes

}
