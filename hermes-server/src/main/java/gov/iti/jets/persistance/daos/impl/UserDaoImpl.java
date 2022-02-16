package gov.iti.jets.persistance.daos.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import gov.iti.jets.business.daos.UserDao;
import gov.iti.jets.business.dtos.UserDto;
import gov.iti.jets.persistance.DataSource;
import gov.iti.jets.persistance.entities.UserEntity;

public class UserDaoImpl implements UserDao {

	@Override
	public List<UserEntity> getAllUsers() throws SQLException {

		List<UserEntity> userEntities = new ArrayList<>();

		var connection = DataSource.INSTANCE.getConnection();

		String query = "Select * from user";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		var resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			UserEntity userEntity = new UserEntity();
			userEntity.name = resultSet.getString("name");
			userEntity.phone = resultSet.getString("phone");
			userEntities.add(userEntity);
		}
		return userEntities;
	}

	@Override
	public UserEntity getUserByPhone(UserDto userDto) {
		
		String query = "Select * from user where phone = ? and password = ?";


		return null;

	}

	@Override
	public UserEntity getUserRegistered(UserDto userDto) {
		
		String query = "Select * from user where phone = ? and password = ?";


		return null;

	}

	

	@Override
	public void insertUser(UserEntity user) {
		throw new UnsupportedOperationException("NOT IMPLMENTED YET");
	}

	@Override
	public void updateUser(UserEntity user) {
		throw new UnsupportedOperationException("NOT IMPLMENTED YET");
	}

	@Override
	public void deleteUser(UserEntity user) {
		throw new UnsupportedOperationException("NOT IMPLMENTED YET");
	}

}
