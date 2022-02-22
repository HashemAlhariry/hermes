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
				userEntity.phoneNumber = resultSet.getString("phone");
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
	public int insertUser(UserEntity user) {
		int gender = user.gender ? 1 : 0;
		String query = "INSERT INTO hermesdb.user (name, phone, email, password, gender, dob, country) VALUES (?,?,?,?,?,?,?);";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);
				) {
					preparedStatement.setString(1, user.name);
					preparedStatement.setString(2, user.phoneNumber);
					preparedStatement.setString(3, user.email);
					preparedStatement.setString(4, user.password);
					preparedStatement.setInt(5, gender);
					preparedStatement.setString(6, user.dateOfBirth);
					preparedStatement.setString(7, user.country);
					// System.out.println(preparedStatement.executeUpdate());
					return preparedStatement.executeUpdate();
			// System.out.println(resultSet);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void updateUser(UserEntity user) {

	}

	@Override
	public void deleteUser(UserEntity user) {

	}

}
