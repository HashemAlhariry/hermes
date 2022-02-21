package gov.iti.jets.server.persistance.daos.impl;

import java.sql.SQLException;
import java.util.List;

import gov.iti.jets.server.business.daos.InvitationDao;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.DataSource;
import gov.iti.jets.server.persistance.entities.InvitationEntity;
import gov.iti.jets.server.persistance.entities.enums.InvitationStatus;
import javafx.scene.chart.PieChart.Data;

public class InvitationDaoImpl implements InvitationDao {

	@Override
	public boolean createInvitation(InvitationEntity invitationEntity) {
		String sql = "insert into invitation (sender_phone, reciever_phone, status) values (?,?,?)";
		try (var preparedStmt = DataSource.INSTANCE.getDataSource().getConnection().prepareStatement(sql);) {
			// check if reciever is a member in database
			UserDao daoImpl = new UserDaoImpl();
			if (daoImpl.getUserByPhone(invitationEntity.getRecieverPhone()).isPresent()) {
				preparedStmt.setString(1, invitationEntity.getSenderPhone());
				preparedStmt.setString(2, invitationEntity.getRecieverPhone());
				preparedStmt.setInt(3, InvitationStatus.PENDEING.ordinal());
				// isInserted if returns 1 and in case insertion failed returns 0
				int isInserted = preparedStmt.executeUpdate();
				return isInserted == 1;
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return false;
	}

	@Override
	public void deleteInvitation(InvitationEntity invitationEntity) {
		// TODO Auto-generated method stub
		// mybe used when canceling invitation is implemented in the future

	}

	@Override
	public List<InvitationEntity> getPendingInvitationsByReciever(InvitationEntity invitationEntity) {
		String sql = "select * from invitation where id = ?";
		try (var preparedStmt = DataSource.INSTANCE.getDataSource().getConnection().prepareStatement(sql)) {
			preparedStmt.executeQuery();
		} catch (Exception e) {
			//TODO: handle exception
		}

		return null;
	}

	@Override
	public void updateInvitationStatus(InvitationEntity invitationEntity) {
		// TODO Auto-generated method stub

	}

}
