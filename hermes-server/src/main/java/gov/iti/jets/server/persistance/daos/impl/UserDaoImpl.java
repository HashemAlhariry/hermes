package gov.iti.jets.server.persistance.daos.impl;

import java.util.ArrayList;
import java.util.List;
import common.business.dtos.UserDto;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.DataSource;
import gov.iti.jets.server.persistance.entities.UserEntity;
import java.sql.SQLException;

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
		} catch (SQLException e) {
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
		int gender = user.gender ? 1 : 0;
		String query = "INSERT INTO hermesdb.user (name, phone, email, password, gender, dob, country) VALUES (?,?,?,?,?,?,?);";
		try (var connection = dataSource.getDataSource().getConnection();
			var preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, user.name);
			preparedStatement.setString(2, user.phone);
			preparedStatement.setString(3, user.email);
			preparedStatement.setString(4, user.password);
			preparedStatement.setInt(5, gender);
			preparedStatement.setDate(6, user.dob);
			preparedStatement.setString(7, user.country);
			// System.out.println(preparedStatement.executeUpdate());
			preparedStatement.executeUpdate();
			// System.out.println(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(UserEntity user) {

	}

	@Override
	public void deleteUser(UserEntity user) {

	}

}
