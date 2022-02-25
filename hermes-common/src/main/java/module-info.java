module common.mod {

	requires java.rmi;
	exports common;
	exports common.business.dtos;
	exports common.business.services;

	uses common.business.services.Client;
	uses common.business.services.Server;
}
