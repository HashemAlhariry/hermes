package gov.iti.jets.server.business.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import gov.iti.jets.server.persistance.entities.UserEntity;

public interface UserDao {

	List<UserEntity> getAllUsers() throws SQLException;

	void insertUser(UserEntity user);

	void updateUser(UserEntity user);

	void deleteUser(UserEntity user);

	boolean loginUser(UserEntity userEntity);

	boolean checkPhone(String phone);

	Optional<UserEntity> getUserByPhone(String phone);

	public String getUserImageByPhone(String phone);

	public boolean setUserProfilePicture(String phoneNumber , String phone);

}
