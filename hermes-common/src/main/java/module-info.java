module common.mod {

	requires java.rmi;
	exports common;
	exports common.business.dtos;
	exports common.business.services;
	uses common.business.services.AddingContact;
	uses common.business.services.AddingNewContact;
	uses common.business.services.BlockingContact;
	uses common.business.services.CreatingGroup;
	uses common.business.services.Login;
	uses common.business.services.Logout;
	uses common.business.services.NotifyFriendsImageChanges;
	uses common.business.services.SearchingContact;
	uses common.business.services.SendingAnnouncementFromServer;
	uses common.business.services.SendingMessage;
	uses common.business.services.SendingNotifaction;
	uses common.business.services.UpdatingProfile;

}