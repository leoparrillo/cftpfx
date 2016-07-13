package service;

import java.util.List;

import model.Server;
import service.ServerCSVService;


public interface ServerService {

	// CREATE
	public void save(Server server);
	
	// RETRIEVE
	public List<Server> findAll();

	public Server findById(int id);
	
	// DELETE
	public void remove(int id);
	
	// UPDATE
	public void update(Server server);
	
	public static ServerService getNewInstance() {
		return new ServerCSVService();
		//return new ServerDBService();
	}

}