package gov.iti.jets.persistance.util;

import gov.iti.jets.business.daos.*;
import gov.iti.jets.persistance.daos.impl.*;

public enum DaosFactory {

    INSTANCE;
    private UserDao userDao = new UserDaoImpl();
    private GroupDao GroupDao = new GroupDaoImpl();
    private MessageDao messageDao = new MessageDaoImpl();
   
    public UserDao getUserDao() {
        return userDao;
    }

    public GroupDao getGroupDao() {
        return GroupDao;
    }
    
    public MessageDao getMessageDao() {
        return messageDao;
    }

}
