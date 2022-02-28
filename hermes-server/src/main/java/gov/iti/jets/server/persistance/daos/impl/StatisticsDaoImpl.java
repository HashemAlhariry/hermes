package gov.iti.jets.server.persistance.daos.impl;

import gov.iti.jets.server.business.daos.StatisticsDao;
import gov.iti.jets.server.business.daos.UserDao;
import gov.iti.jets.server.persistance.util.DaosFactory;

import java.sql.SQLException;
import java.util.Map;

public class StatisticsDaoImpl implements StatisticsDao {


    @Override
    public int getAllUsers() {
        try {
            return DaosFactory.INSTANCE.getUserDao().getAllUsers().size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getMaleUsers() {
        return DaosFactory.INSTANCE.getUserDao().getAllMaleUsers().size();
    }

    @Override
    public int getFemaleUsers() {
        return DaosFactory.INSTANCE.getUserDao().getAllFemaleUsers().size();
    }

    @Override
    public Map<String, Integer> getAllCountries(){
        return DaosFactory.INSTANCE.getUserDao().getAllCountries();
    }

}
