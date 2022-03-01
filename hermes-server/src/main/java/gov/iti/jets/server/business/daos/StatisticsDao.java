package gov.iti.jets.server.business.daos;

import java.util.Map;

public interface StatisticsDao {

    int getAllUsers();
    int getMaleUsers();
    int getFemaleUsers();
    Map<String, Integer> getAllCountries();
}
