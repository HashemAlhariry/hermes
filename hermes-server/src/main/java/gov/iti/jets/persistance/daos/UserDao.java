package gov.iti.jets.persistance.daos;

import java.sql.SQLException;
import java.util.List;

import gov.iti.jets.persistance.entities.UserEntity;

public interface UserDao {

	List<UserEntity> getAllUsers() throws SQLException;

	UserEntity getUserByPhone(String phone);

	void insertUser(UserEntity user);

	void updateUser(UserEntity user);

	void deleteUser(UserEntity user);
}
