package gov.iti.jets.services.util;

import gov.iti.jets.services.LoginService;
import gov.iti.jets.services.impls.LoginServiceImpl;

public class ServicesFactory {


    private static final ServicesFactory servicesFactory = new ServicesFactory();
    private final LoginService loginService = new LoginServiceImpl();
    public static ServicesFactory getInstance(){
        return servicesFactory;
    }

    private ServicesFactory(){

    }

    public LoginService getLoginService(){
        return  loginService;
    }
}
