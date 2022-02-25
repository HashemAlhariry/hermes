package gov.iti.jets.server.persistance.util;

import gov.iti.jets.server.business.daos.*;
import gov.iti.jets.server.persistance.daos.impl.*;

public enum DaosFactory {

    INSTANCE;
    private UserDao userDao = new UserDaoImpl();
    private GroupDao GroupDao = new GroupDaoImpl();
    private MessageDao messageDao = new MessageDaoImpl();
    private InvitationDao invitationDao = new InvitationDaoImpl();
    private GroupUserDao groupUserDao = new GroupUserDaoImpl();
    public UserDao getUserDao() {
        return userDao;
    }

    public GroupDao getGroupDao() {
        return GroupDao;
    }
    
    public MessageDao getMessageDao() {
        return messageDao;
    }

    public InvitationDao getInvitationDao (){return  invitationDao;}

    public GroupUserDao getGroupUserDao(){return groupUserDao;}
}
