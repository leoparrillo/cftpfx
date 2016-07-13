package service;

import model.Server;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerCSVService implements ServerService {

	// file delimiter
	private static final String DELIMITER = ";";

	// file path
	private static final Path OUTPUT_FILE = Paths.get("cFTP.ini");
	
	// file data
	private List<Server> servers = new ArrayList<>();

	public ServerCSVService() {
		loadData();
	}

	@Override
	public void save(Server server) {
		if (!server.getHost().isEmpty()) {
			server.setId(lastId() + 1);
			servers.add(server);
			saveDataToCsv();
		}
	}


	@Override
	public void update(Server server) {
		Server serverOld = findById(server.getId());
		serverOld.setHost(server.getHost());
		serverOld.setIp(server.getIp());
		serverOld.setXmlDir(server.getXmlDir());
		serverOld.setSenfDir(server.getSenfDir());
		serverOld.setHomeDir(server.getHomeDir());
		serverOld.setFtpDir(server.getFtpDir());
		saveDataToCsv();
	}

	@Override
	public List<Server> findAll() {
		return servers;
	}

	@Override
	public void remove(int id) {
		Server server = findById(id);
		servers.remove(server);
		saveDataToCsv();
	}

	public Server findById(int id) {
		return servers.stream().filter(c -> c.getId() == id).findFirst()
				.orElseThrow(() -> new Error("Server not found."));
	}

	// save a data list in the file, re-generate a new CSV File
	private void saveDataToCsv() {
		StringBuffer sb = new StringBuffer();
		for (Server s : servers) {
			String line = createLine(s);
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
		}
		try {
			//Files.delete(ARQUIVO_SAIDA);
			Files.write(OUTPUT_FILE, sb.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// return higher ID
	private int lastId() {
		return servers.stream().mapToInt(Server::getId).max().orElse(0);
	}

	// Load Data File to List 'servers'
	private void loadData() {
		try {
			if(!Files.exists(OUTPUT_FILE)) {
				Files.createFile(OUTPUT_FILE);
			}
			servers = Files.lines(OUTPUT_FILE).map(this::readLine).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	// Change a CSV line into Server
	private Server readLine(String line) {
		String column[] = line.split(DELIMITER);
		int id = Integer.parseInt(column[0]);

		Server server = new Server();
		server.setId(id);
		server.setHost(column[1]);
		server.setIp(column[2]);
		server.setXmlDir(column[3]);
		server.setSenfDir(column[4]);
		server.setHomeDir(column[5]);
		server.setFtpDir(column[6]);

		return server;
	}
	
	// Change a Server Object in CSV File
	private String createLine(Server s) {
		//String dataStr = formatoData.format(c.getDataVencimento());
		String idStr = String.valueOf(s.getId());
		String line = String.join(DELIMITER, idStr, s.getHost(), s.getIp(), s.getXmlDir(), s.getSenfDir(), s.getHomeDir(), s.getFtpDir());
		return line;
	}

}