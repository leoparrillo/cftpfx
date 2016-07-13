package service;

import model.Server;
import model.User;

public class ServiceFtp {
	private String ftpString; 
	private String[] command; 
	private String directory;
	private String fileCpl;

	public ServiceFtp(Server server, User user) {
		//ftpString = "c:\\ftpString\\co\\trwsftp.exe -hn:" + server.getHost();
		ftpString = server.getFtpDir() + " -hn:" + server.getIp();
		ftpString = ftpString + " -un:" + user.getUser() + " -pw:" + user.getPasswd() + " -cm:get ";    	
    	fileCpl = "";
		
    	if (!user.getRadioText().equals("SENF")) {
    		
    		if (user.getRadioText().equals("XML")) {
    			directory = server.getXmlDir();
    			fileCpl = "-nfe-signed.xml";
    		} else {
    			directory = server.getHomeDir() + user.getUser() + "/";    			
    		}
    		command = new String[1];
    		command[0] = ftpString + "-lf:" + user.getFile() + fileCpl + " -rf:" + directory + user.getFile() + fileCpl;
        } else {
        	command = new String[44];
        	directory = server.getSenfDir(); 
        	//SENF FATURAMENTO
        	command[0]  = ftpString + "-lf:s"   + user.getFile() + "_7_13_21_3_3.txt"  + " -rf:" + directory + "s"   + user.getFile() + "_7_13_21_3_3.txt";
        	command[1]  = ftpString + "-lf:s"   + user.getFile() + "_7_13_21_3_3.log"  + " -rf:" + directory + "s"   + user.getFile() + "_7_13_21_3_3.log";
        	command[2]  = ftpString + "-lf:s"   + user.getFile() + "_7_13_21_3_7.txt"  + " -rf:" + directory + "s"   + user.getFile() + "_7_13_21_3_7.txt";
        	command[3]  = ftpString + "-lf:s"   + user.getFile() + "_7_13_21_3_7.log"  + " -rf:" + directory + "s"   + user.getFile() + "_7_13_21_3_7.log";
        	command[4]  = ftpString + "-lf:s"   + user.getFile() + "_12_17_14.txt"     + " -rf:" + directory + "s"   + user.getFile() + "_12_17_14.txt";
        	command[5]  = ftpString + "-lf:s"   + user.getFile() + "_12_17_14.log"     + " -rf:" + directory + "s"   + user.getFile() + "_12_17_14.log";
        	command[6]  = ftpString + "-lf:s"   + user.getFile() + "_12_17_22.txt"     + " -rf:" + directory + "s"   + user.getFile() + "_12_17_22.txt";
        	command[7]  = ftpString + "-lf:s"   + user.getFile() + "_12_17_22.log"     + " -rf:" + directory + "s"   + user.getFile() + "_12_17_22.log";
        	command[8]  = ftpString + "-lf:s"   + user.getFile() + "_3_7.txt"          + " -rf:" + directory + "s"   + user.getFile() + "_3_7.txt";
        	command[9]  = ftpString + "-lf:s"   + user.getFile() + "_3_7.log"          + " -rf:" + directory + "s"   + user.getFile() + "_3_7.log";
        	command[10] = ftpString + "-lf:s"   + user.getFile() + "_3_4_4.txt"        + " -rf:" + directory + "s"   + user.getFile() + "_3_4_4.txt";
        	command[11] = ftpString + "-lf:s"   + user.getFile() + "_3_4_4.log"        + " -rf:" + directory + "s"   + user.getFile() + "_3_4_4.log";
        	command[12] = ftpString + "-lf:s"   + user.getFile() + "_7_13_21_3_8.txt"  + " -rf:" + directory + "s"   + user.getFile() + "_7_13_21_3_8.txt";
        	command[13] = ftpString + "-lf:s"   + user.getFile() + "_7_13_21_3_8.log"  + " -rf:" + directory + "s"   + user.getFile() + "_7_13_21_3_8.log";
        	command[14] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_1.cim"  + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_1.cim";
        	command[15] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_1.log"  + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_1.log";
        	command[16] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_13.cim" + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_13.cim";
        	command[17] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_13.log" + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_13.log";
        	command[18] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_4.cim"  + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_4.cim";
        	command[19] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_4.log"  + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_4.log";
        	command[20] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_14.cim" + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_14.cim";
        	command[21] = ftpString + "-lf:sdv" + user.getFile() + "5_13_21_20_14.log" + " -rf:" + directory + "sdv" + user.getFile() + "5_13_21_20_14.log";
        	
        	//SENF DE CANCELAMENTO
        	command[22] = ftpString + "-lf:cs"   + user.getFile() + "_7_13_21_3_3.txt"  + " -rf:" + directory + "cs"   + user.getFile() + "_7_13_21_3_3.txt";
        	command[23] = ftpString + "-lf:cs"   + user.getFile() + "_7_13_21_3_3.log"  + " -rf:" + directory + "cs"   + user.getFile() + "_7_13_21_3_3.log";
        	command[24] = ftpString + "-lf:cs"   + user.getFile() + "_7_13_21_3_7.txt"  + " -rf:" + directory + "cs"   + user.getFile() + "_7_13_21_3_7.txt";
        	command[25] = ftpString + "-lf:cs"   + user.getFile() + "_7_13_21_3_7.log"  + " -rf:" + directory + "cs"   + user.getFile() + "_7_13_21_3_7.log";
        	command[26] = ftpString + "-lf:cs"   + user.getFile() + "_12_17_14.txt"     + " -rf:" + directory + "cs"   + user.getFile() + "_12_17_14.txt";
        	command[27] = ftpString + "-lf:cs"   + user.getFile() + "_12_17_14.log"     + " -rf:" + directory + "cs"   + user.getFile() + "_12_17_14.log";
        	command[28] = ftpString + "-lf:cs"   + user.getFile() + "_12_17_22.txt"     + " -rf:" + directory + "cs"   + user.getFile() + "_12_17_22.txt";
        	command[29] = ftpString + "-lf:cs"   + user.getFile() + "_12_17_22.log"     + " -rf:" + directory + "cs"   + user.getFile() + "_12_17_22.log";
        	command[30] = ftpString + "-lf:cs"   + user.getFile() + "_3_7.txt"          + " -rf:" + directory + "cs"   + user.getFile() + "_3_7.txt";
        	command[31] = ftpString + "-lf:cs"   + user.getFile() + "_3_7.log"          + " -rf:" + directory + "cs"   + user.getFile() + "_3_7.log";
        	command[32] = ftpString + "-lf:cs"   + user.getFile() + "_3_4_4.txt"        + " -rf:" + directory + "cs"   + user.getFile() + "_3_4_4.txt";
        	command[33] = ftpString + "-lf:cs"   + user.getFile() + "_3_4_4.log"        + " -rf:" + directory + "cs"   + user.getFile() + "_3_4_4.log";
        	command[34] = ftpString + "-lf:cs"   + user.getFile() + "_7_13_21_3_8.txt"  + " -rf:" + directory + "cs"   + user.getFile() + "_7_13_21_3_8.txt";
        	command[35] = ftpString + "-lf:cs"   + user.getFile() + "_7_13_21_3_8.log"  + " -rf:" + directory + "cs"   + user.getFile() + "_7_13_21_3_8.log";
        	command[36] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_1.cim"  + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_1.cim";
        	command[37] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_1.log"  + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_1.log";
        	command[38] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_13.cim" + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_13.cim";
        	command[39] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_13.log" + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_13.log";
        	command[40] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_4.cim"  + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_4.cim";
        	command[41] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_4.log"  + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_4.log";
        	command[42] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_14.cim" + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_14.cim";
        	command[43] = ftpString + "-lf:csdv" + user.getFile() + "5_13_21_20_14.log" + " -rf:" + directory + "csdv" + user.getFile() + "5_13_21_20_14.log";
        }
		
	}
	
	public String[] getCommand(){
		return this.command;
	}

}
