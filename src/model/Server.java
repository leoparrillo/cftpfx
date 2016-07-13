package model;

/**
 * 
 * Nossa classe de modelo do objeto que "sofrerá" as operações de CRUD
 * @author Leonardo Parrillo
 *
 */

public class Server {
	private int id;
	private String host;
	private String ip;
	private String xmldir;
	private String senfdir;
	private String homedir;
	private String ftpdir;

	// gets e sets
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getXmlDir() {
		return xmldir;
	}

	public void setXmlDir(String xmldir) {
		this.xmldir = xmldir;
	}

	public String getSenfDir() {
		return senfdir;
	}

	public void setSenfDir(String senfdir) {
		this.senfdir = senfdir;
	}

	public String getHomeDir() {
		return homedir;
	}

	public void setHomeDir(String homedir) {
		this.homedir = homedir;
	}

	public String getFtpDir() {
		return ftpdir;
	}

	public void setFtpDir(String ftpdir) {
		this.ftpdir = ftpdir;
	}
	
	
	@Override
    public String toString() {
        return getHost();
    }
    
	

}
