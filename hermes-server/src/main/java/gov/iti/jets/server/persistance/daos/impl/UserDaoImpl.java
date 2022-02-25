package gov.iti.jets.server.persistance.daos.impl;

import java.lang.module.ResolutionException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
=======
import common.business.dtos.UserAuthDto;
>>>>>>> Stashed changes
import common.business.dtos.UserDto;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.business.services.impl.MapperImpl;
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
	public boolean loginUser(UserEntity userEntity){
		String query = "Select * from hermesdb.user where phone = ? and password = ?";
		try(var connection = dataSource.getDataSource().getConnection();
			var preparedStatement = connection.prepareStatement(query)){
				preparedStatement.setString(1,userEntity.phone);
				preparedStatement.setString(2, userEntity.password);
				ResultSet rs = preparedStatement.executeQuery();
					if(rs.next())
						return true;
					else
					return false;
					
			} catch(Exception e){
				e.printStackTrace();
			}
		return false;
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
<<<<<<< Updated upstream
	public UserEntity getUserRegistered(UserDto userDto) {
=======
	public UserEntity getUserByPhone(UserAuthDto userAuthDto) {
>>>>>>> Stashed changes
		String query = "Select * from user where phone = ? and password = ?";
		return null;
	}

}
