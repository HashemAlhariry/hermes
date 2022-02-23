package gov.iti.jets.server.persistance.daos.impl;

import java.util.ArrayList;
import java.util.List;
import gov.iti.jets.server.business.daos.GroupDao;
import gov.iti.jets.server.persistance.DataSource;
import gov.iti.jets.server.persistance.entities.GroupEntity;
import gov.iti.jets.server.persistance.entities.UserEntity;

/**
 * GroupDaoImpl
 */
public class GroupDaoImpl implements GroupDao {

	private DataSource dataSource;

	@Override
	public List<GroupEntity> getAllGroupdByUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertGroup(GroupEntity groupEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGroup(GroupEntity groupEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteGroup(GroupEntity groupEntity) {
		// TODO Auto-generated method stub

	}

	//get all user attached to a group private or public
	public List<String> getUsersByGroupId(Long groupID) {

		String query = "select * from group_user where group_id_fk = ? ";
		List<String> result = new ArrayList<>();
		try (var connection = dataSource.getDataSource().getConnection();
			var preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setLong(1, groupID);
			var resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.add(resultSet.getString("user_phone_fk"));
				System.out.println("user attached to the group " + resultSet.getString("user_phone_fk"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
