package gov.iti.jets.server.persistance.daos.impl;

import gov.iti.jets.server.business.daos.GroupUserDao;
import gov.iti.jets.server.persistance.DataSource;
import gov.iti.jets.server.persistance.entities.GroupUserEntity;

import java.sql.SQLException;

public class GroupUserDaoImpl implements GroupUserDao {

    @Override
    public int insertGroupUser(GroupUserEntity groupUserEntity) {
        //inserting new group for 2 specific users
        String sql = "insert into group_user (group_id_fk, user_phone_fk) values (?,?)";

        try (var preparedStmt = DataSource.INSTANCE.getDataSource().getConnection().prepareStatement(sql);) {

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
}
