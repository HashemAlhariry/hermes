package gov.iti.jets.server.persistance.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userEntities;
	}

	@Override
	public boolean checkPhone(String phone) {
		String query = "Select * from hermesdb.user where phone = ?";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, phone);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next())
				return true;
			else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean setUserProfilePicture(String phone, String format) {
		String query = "update hermesdb.user set image = ? where  phone= ? ";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, phone + "." + format);
			preparedStatement.setString(2, phone);
			int i = preparedStatement.executeUpdate();
			if (i > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean loginUser(UserEntity userEntity) {
		String query = "Select * from hermesdb.user where phone = ? and password = ?";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, userEntity.phone);
			preparedStatement.setString(2, userEntity.password);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return true;
			} else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Optional<UserEntity> getUserByPhone(String phone) {

		String sql = "select * from user where phone = ?";
		UserEntity userEntity = new UserEntity();
		try (var connection = DataSource.INSTANCE.getDataSource().getConnection();
				var preparedStmt = connection.prepareStatement(sql)) {
			preparedStmt.setString(1, phone);
			var resultSet = preparedStmt.executeQuery();
			if (resultSet.next()) {
				fillUserEntity(resultSet, userEntity);
				return Optional.of(userEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();

	}

	private void fillUserEntity(ResultSet resultSet, UserEntity userEntity) throws SQLException {
		userEntity.phone = resultSet.getString("phone");
		userEntity.name = resultSet.getString("name");
		userEntity.email = resultSet.getString("email");
		userEntity.password = resultSet.getString("password");
		userEntity.image = resultSet.getString("image");
		userEntity.gender = resultSet.getBoolean("gender");
		userEntity.dob = resultSet.getDate("dob");
		userEntity.country = resultSet.getString("country");
		userEntity.bio = resultSet.getString("bio");

	}

	@Override
	public String insertUser(UserEntity user) {
		boolean uniqueEmail = true;
		boolean uniquePhone = true;
		List<UserEntity> allUsers = getAllUsers();
		for (UserEntity userEntity : allUsers) {
			if (userEntity.phone.equals(user.phone)) {
				uniquePhone = false;
				return "Phone Number Must Be Unique";
			}
			// if (userEntity.email.equalsIgnoreCase(user.email)) {
			// 	uniqueEmail = false;
			// 	return "Email Must Be Unique";
			// }
		}

		if (uniquePhone) {
			int gender = user.gender ? 1 : 0;
			String query = "INSERT INTO hermesdb.user (name, phone, email, password, gender, dob, country) VALUES (?,?,?,?,?,?,?);";
			try (var connection = dataSource.getDataSource().getConnection();
					var preparedStatement = connection.prepareStatement(query);) {
				preparedStatement.setString(1, user.name.trim());
				preparedStatement.setString(2, user.phone.trim());
				preparedStatement.setString(3, user.email.trim());
				preparedStatement.setString(4, user.password.trim());
				preparedStatement.setInt(5, gender);
				preparedStatement.setDate(6, user.dob);
				preparedStatement.setString(7, user.country.trim());
				// System.out.println(preparedStatement.executeUpdate());
				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void updateUser(UserEntity user) {
		String query = "update hermesdb.user set country = ? , email = ? , dob = ? ,  bio= ? , password=?  where  phone=?";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, user.country);
			preparedStatement.setString(2, user.email);
			preparedStatement.setDate(3, user.dob);
			preparedStatement.setString(4, user.bio);
			preparedStatement.setString(5, user.password);
			preparedStatement.setString(6, user.phone);
			int rowAffected = preparedStatement.executeUpdate();

			if (rowAffected > 0) {
				System.out.println("Update is done.");

			} else
				System.out.println("Wrong update");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteUser(UserEntity user) {

	}

	@Override
	public List<UserEntity> getAllMaleUsers() {
		List<UserEntity> userEntities = new ArrayList<>();
		String query = "Select * from user where gender = 1";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);
				var resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				fillUserEntity(resultSet, userEntity);
				userEntities.add(userEntity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userEntities;
	}

	@Override
	public List<UserEntity> getAllFemaleUsers() {
		List<UserEntity> userEntities = new ArrayList<>();
		String query = "Select * from user where gender = 0";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);
				var resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				UserEntity userEntity = new UserEntity();
				fillUserEntity(resultSet, userEntity);
				userEntities.add(userEntity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userEntities;
	}

	public Map<String, Integer> getAllCountries() {
		Map<String, Integer> countriesWithUsers = new HashMap<>();
		String query = "Select * from user;";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);
				var resultSet = preparedStatement.executeQuery()) {
			String country;
			while (resultSet.next()) {
				country = resultSet.getString("country");
				if (resultSet.wasNull()) {
					continue;
				} else {
					if (countriesWithUsers.containsKey(country)) {
						countriesWithUsers.put(country, countriesWithUsers.get(country) + 1);
					} else {
						countriesWithUsers.put(country, 1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countriesWithUsers;
	}
	@Override
	public String getUserImageByPhone(String phone) {
		String query = "select image from hermesdb.user where phone = ? ";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, phone);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				String img = rs.getString(1);
				System.out.println(img);
				return img;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
