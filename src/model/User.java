package model;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class User {
	private String user;
	private String passwd;
	private String file;
	private String radioText;
	
	
	public User (TextField txtFtpUser, PasswordField txtFtpPasswd, TextField txtFtpFile, String radioText) {
		this.user = txtFtpUser.getText();
		this.passwd = txtFtpPasswd.getText();
		this.file = txtFtpFile.getText();
		this.radioText = radioText;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getRadioText() {
		return radioText;
	}
	public void setRadioText(String radioText) {
		this.radioText = radioText;
	}	

}
