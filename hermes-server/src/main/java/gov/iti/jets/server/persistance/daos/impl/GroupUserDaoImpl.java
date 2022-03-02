package gov.iti.jets.server.persistance.daos.impl;

import gov.iti.jets.server.business.daos.GroupUserDao;
import gov.iti.jets.server.persistance.DataSource;
import gov.iti.jets.server.persistance.entities.GroupUserEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupUserDaoImpl implements GroupUserDao {

    @Override
    public int insertGroupUser(GroupUserEntity groupUserEntity) {
        //inserting new group for 2 specific users
        String sql = "insert into group_user (group_id_fk, user_phone_fk) values (?,?)";

        try (var connection = DataSource.INSTANCE.getDataSource().getConnection();
             var preparedStmt = connection.prepareStatement(sql)) {

            preparedStmt.setInt(1, groupUserEntity.groupIdFk);
            preparedStmt.setString(2,groupUserEntity.userPhoneFk);


            // isInserted if returns 1 and in case insertion failed returns 0
            int isInserted = preparedStmt.executeUpdate();

            return isInserted;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<String> getAllUsersByGroup(int groupID) {
        List<String> userPhones = new ArrayList<>();
        String sql = " select user_phone_fk from hermesdb.group_user where group_id_fk = ?;";

        try (var connection = DataSource.INSTANCE.getDataSource().getConnection();
             var preparedStmt = connection.prepareStatement(sql)) {

            preparedStmt.setInt(1, groupID );


            var resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                userPhones.add(resultSet.getString("user_phone_fk"));
            }
        return userPhones;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return userPhones;
    }
}
