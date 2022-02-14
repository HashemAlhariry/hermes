package gov.iti.jets.persistance.daos.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.iti.jets.persistance.DataSource;
import gov.iti.jets.persistance.daos.UserDao;
import gov.iti.jets.persistance.entities.UserEntity;

public class UserDaoImpl implements UserDao {

	@Override
	public List<UserEntity> getAllUsers() throws SQLException {
		List<UserEntity> userEntities = new ArrayList<>();
		var con = DataSource.getConnection();
		String query = "Select * from user";
		PreparedStatement preparedStatement = con.prepareStatement(query);

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
	public UserEntity getUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertUser(UserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(UserEntity user) {
		// TODO Auto-generated method stub
		
	}
	
}
