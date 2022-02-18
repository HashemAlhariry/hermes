module gov.iti.jets.server {

	requires transitive javafx.controls;
	requires javafx.fxml;
	requires com.zaxxer.hikari;
	requires mysql.connector.java;
	requires java.sql;
	requires java.rmi;
    requires transitive common.mod;
	requires java.management;
    opens gov.iti.jets.server.presentation.gui.controllers to javafx.fxml;

	exports gov.iti.jets.server;
	exports gov.iti.jets.server.business.services.impl;
	
	provides common.business.services.AddingContact with gov.iti.jets.server.business.services.impl.AddingContactImpl;
	provides common.business.services.AddingNewContact  with gov.iti.jets.server.business.services.impl.AddingNewContactImpl;
	provides common.business.services.BlockingContact with gov.iti.jets.server.business.services.impl.BlockingContactImpl;
	provides common.business.services.CreatingGroup with gov.iti.jets.server.business.services.impl.CreatingGroupImpl;
	provides common.business.services.Login with gov.iti.jets.server.business.services.impl.LoginImpl;
	provides common.business.services.Logout with gov.iti.jets.server.business.services.impl.LogoutImpl;
	provides common.business.services.NotifyFriendsImageChanges with gov.iti.jets.server.business.services.impl.NotifyFriendsImageChangesImpl;
	provides common.business.services.SearchingContact with gov.iti.jets.server.business.services.impl.SearchingContactImpl;
	provides common.business.services.SendingAnnouncementFromServer with gov.iti.jets.server.business.services.impl.SendingAnnouncementFromServerImpl;
	provides common.business.services.SendingMessage with gov.iti.jets.server.business.services.impl.SendingMessageImpl;
	provides common.business.services.SendingNotifaction with gov.iti.jets.server.business.services.impl.SendingNotifactionImpl;
	provides common.business.services.UpdatingProfile with gov.iti.jets.server.business.services.impl.UpdatingProfileImpl;
}