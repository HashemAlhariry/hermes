package gov.iti.jets.server.persistance.daos.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gov.iti.jets.server.business.daos.MessageDao;
import gov.iti.jets.server.persistance.entities.MessageEntity;
import gov.iti.jets.server.persistance.DataSource;

public class MessageDaoImpl implements MessageDao {

	private DataSource dataSource;

	public MessageDaoImpl() {
		this.dataSource = DataSource.INSTANCE;
	}

	@Override
	public List<MessageEntity> getAllMessagesByGroup(Integer groupId) {

		List<MessageEntity> messageEntities = new ArrayList<>();
		String query = "select * from hermesdb.message where group_user_group_id_fk = ?;";
		try (var connection = dataSource.getDataSource().getConnection();
				var preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, groupId);
			var resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MessageEntity messageEntity = new MessageEntity();
				fillMessageEntity(resultSet, messageEntity);
				messageEntities.add(messageEntity);
			}
			System.out.println(messageEntities);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messageEntities;

	}

	@Override
	public void insertMessage(MessageEntity messageEntity) {
		/**
insert into hermesdb.message
(content, send_date, group_user_group_id_fk, group_user_user_phone_fk)
values ('<html dir="ltr"><head></head><body contenteditable="true"><p style=" background-color: 0x999933ff;  color: 0xffe6b3ff; "><span style="font-family: &quot;&quot;;">askldlasjdl;a</span></p></body></html>',
'2002-02-28',1,'01112066286'); */
		String query = "INSERT INTO hermesdb.message (content, send_date, group_user_group_id_fk, group_user_user_phone_fk) VALUES (?,?,?,?);";
		try (var connection = dataSource.getDataSource().getConnection();
			var preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, messageEntity.content);
			preparedStatement.setDate(2, messageEntity.sendDate);
			preparedStatement.setInt(3, messageEntity.groupId);
			preparedStatement.setString(4, messageEntity.senderPhone);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void fillMessageEntity(ResultSet resultSet, MessageEntity messageEntity) throws SQLException {
		messageEntity.content = resultSet.getString("content");
		messageEntity.sendDate = resultSet.getDate("send_date");
		messageEntity.groupId = resultSet.getInt("group_user_group_id_fk");
		messageEntity.senderPhone = resultSet.getString("group_user_user_phone_fk");
	}

}
