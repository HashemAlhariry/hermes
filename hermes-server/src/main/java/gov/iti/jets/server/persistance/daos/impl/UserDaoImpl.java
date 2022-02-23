package gov.iti.jets.server.persistance.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
				fillUserEntity(resultSet, userEntity);
				userEntities.add(userEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userEntities;
	}

	@Override
	public Optional<UserEntity> getUserByPhone(String phone) {

		String sql = "Select * from user where phone = ?";
		try (var preparedStmt = DataSource.INSTANCE.getDataSource().getConnection().prepareStatement(sql)) {
			preparedStmt.setString(1, phone);
			var resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				fillUserEntity(resultSet, userEntity);
				return Optional.of(userEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.<UserEntity>of(null);

	}

	private void fillUserEntity(ResultSet resultSet, UserEntity userEntity) throws SQLException {
		userEntity.setPhone(resultSet.getString("phone"));
		userEntity.setName(resultSet.getString("name"));
		userEntity.setEmail(resultSet.getString("email"));
		userEntity.setPassword(resultSet.getString("password"));
		userEntity.setImage(resultSet.getString("image"));
		userEntity.setGender(resultSet.getBoolean("gender"));
		userEntity.setDob(resultSet.getDate("dob"));
		userEntity.setCountry(resultSet.getString("country"));
		userEntity.setBio(resultSet.getString("bio"));
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

}
