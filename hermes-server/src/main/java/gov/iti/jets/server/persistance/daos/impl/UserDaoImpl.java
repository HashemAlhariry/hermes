package gov.iti.jets.server.persistance.daos.impl;

import java.util.ArrayList;
import java.util.List;
import common.business.dtos.UserDto;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.DataSource;
import gov.iti.jets.server.persistance.entities.UserEntity;

public class UserDaoImpl implements UserDao {

	private DataSource dataSource;

	public UserDaoImpl() {
		this.dataSource = DataSource.INSTANCE;
	}

	@Override
	public List<UserEntity> getAllUsers() {
		List<UserEntity> userEntities = new ArrayList<>();
		String query = "Select * from user";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);
				var resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				userEntity.name = resultSet.getString("name");
				userEntity.phone = resultSet.getString("phone");
				userEntities.add(userEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userEntities;
	}

	@Override
	public UserEntity getUserByPhone(UserDto userDto) {
		String query = "Select * from user where phone = ? and password = ?";
		return null;
	}

	@Override
	public void insertUser(UserEntity user) {

	}

	@Override
	public void updateUser(UserEntity user) {

	}

	@Override
	public void deleteUser(UserEntity user) {

	}

	@Override
	public UserEntity getUserRegistered(UserDto userDto) {
		String query = "Select * from user where phone = ? and password = ?";
		return null;
	}

}
