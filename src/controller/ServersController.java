package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Server;
import model.User;
import service.ServerService;
import service.ServiceFtp;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * 
 * O controller da aplicação, onde a mágica acontece
 * @author Leonardo Parrillo
 *
 */
public class ServersController implements Initializable {

	@FXML
	private TableView<Server> tblServers;
	@FXML
	private TableColumn<Server, String> colHost;
	@FXML
	private TableColumn<Server, String> colIp;
	@FXML
	private TextField txtHost;
	@FXML
	private TextField txtIp;
	@FXML
	private TextField txtXml;
	@FXML
	private TextField txtSenf;
	@FXML
	private TextField txtHome;
	@FXML
	private TextField txtFtp;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnRemove;
	@FXML
	private Button btnClean;
	@FXML
	private Button btnFile;	
	@FXML
	private ComboBox<Server> cbbFtpServer;
	@FXML
	private TextArea txtFtpResult;
	@FXML
	private ToggleGroup rdGroupFtp;
	@FXML
	private TextField txtFtpUser;
	@FXML
	private PasswordField txtFtpPasswd;
	@FXML
	private TextField txtFtpFile;
	@FXML
	private Button btnFtpRun;
	
	
	private ServerService service;
		
	private ObservableList<Server> observableListServer;


//	 Esse método é chamado ao inicializar a aplicação, igual um construtor. Ele vem da interface Initializable
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = ServerService.getNewInstance();
		setupColumns();
		setupBindings();
		updateTableData();
		updateComboBox();
		toggleGroupControl();
		
		txtFtpFile.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null) {
	            return;
	        }

			if (newValue.length() > maxLenght(rdGroupFtp)) {
				txtFtpFile.setText(txtFtpFile.getText().substring(0, maxLenght(rdGroupFtp)));
			}

		});

	}

    // métodos públicos chamados quando o botão é clicado
	@FXML
	public void save() {
		if (tblServers.getSelectionModel().getSelectedItem() != null) {
			update();
		} else {
			Server s = new Server();
			getValues(s);
			service.save(s);
		}
		
		updateComboBox();
		updateTableData();
		clean();
	}

	public void update() {
		Server s = tblServers.getSelectionModel().getSelectedItem();
		getValues(s);
		service.update(s);
		//atualizaDadosTabela();
		//clean();
	}

	@FXML
	public void remove() {
		Server s = tblServers.getSelectionModel().getSelectedItem();
		service.remove(s.getId());
		updateTableData();
		updateComboBox();
	}

	@FXML
	public void clean() {		
		tblServers.getSelectionModel().select(null);
		txtHost.setText("");
		txtIp.setText("");
		txtXml.setText("");
		txtSenf.setText("");
		txtHome.setText("");
		txtFtp.setText("");		
	}
	
	@FXML
	public void cleanFtp() {		
		cbbFtpServer.getSelectionModel().select(null);
		txtFtpUser.setText("");
		txtFtpPasswd.setText("");
		txtFtpFile.setText("");
		txtFtpResult.clear();
	}
	
	@FXML
	public void searchFile() {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		 File selectedFile = fileChooser.showOpenDialog(new Stage());
		 		 
		 if (selectedFile != null) {
			 txtFtp.setText(selectedFile.toString());
		 }
	}
	
	@FXML
	public void runFtp() {
		RadioButton rdnBt = (RadioButton) rdGroupFtp.getSelectedToggle();		
		User u = new User(txtFtpUser, txtFtpPasswd, txtFtpFile, rdnBt.getText());
		Server s = cbbFtpServer.getSelectionModel().getSelectedItem();
	
		if (!validateFtp(rdnBt)) {
			return;
		}
		else {
			ServiceFtp ftp = new ServiceFtp(s, u);
			String[] command = ftp.getCommand();
		
			Task taskFtp = new Task() {
				
				@Override
				protected String call() throws Exception {
					for (int i = 0; i < command.length; i++) {	
						txtFtpResult.appendText(command[i] + "\n");
						Runtime r = Runtime.getRuntime();
						try {
                            Thread.sleep(10);  // milliseconds
                            Process output = r.exec(command[i]);
                            BufferedReader log = new BufferedReader(new InputStreamReader(output.getInputStream()));
				             while (log.readLine() != null) {
				                    txtFtpResult.appendText(log.readLine() + "\n");
				             }
				             log.close();
				                					                
							 output.waitFor();
                         } catch (IOException | InterruptedException ex) {
                        	 System.out.println(ex.getMessage());
                         }						
					}
					return "";
				}
			};
			
			btnFtpRun.disableProperty().bind(taskFtp.runningProperty());
			Thread t = new Thread(taskFtp);
			t.setDaemon(true);			
			t.start();
			
		}
	}
	
	// Controller's private methods

	// get values from view
	private void getValues(Server s) {
		s.setHost(txtHost.getText());
		s.setIp(txtIp.getText());
		s.setXmlDir(txtXml.getText());
		s.setSenfDir(txtSenf.getText());
		s.setHomeDir(txtHome.getText());
		s.setFtpDir(txtFtp.getText());		
	}


	// Update TableView with new data
	private void updateTableData() {
		tblServers.getItems().setAll(service.findAll());
		clean();
	}

	// Setup columns to show Server Class
	private void setupColumns() {
		colHost.setCellValueFactory(new PropertyValueFactory<>("host"));
		colIp.setCellValueFactory(new PropertyValueFactory<>("ip"));		
	}

	// Setup screen logic
	private void setupBindings() {
		// This binding is only "false" when all screen fields are filled
		
		BooleanBinding filledFields = txtHost.textProperty().isEmpty()
				.or(txtIp.textProperty().isEmpty())
				.or(txtXml.textProperty().isEmpty())
				.or(txtSenf.textProperty().isEmpty())
				.or(txtHome.textProperty().isEmpty())
				.or(txtFtp.textProperty().isEmpty());
		
		// It indicates whether there is something selected on the table
		BooleanBinding somethingSelected = tblServers.getSelectionModel().selectedItemProperty().isNull();
		
		// some buttons only are enabled if something was selected on the TableView
		//btnSave.disableProperty().bind(somethingSelected);
		btnRemove.disableProperty().bind(somethingSelected);
		//btnLimpart.disableProperty().bind(somethingSelected);
		
		// The save button only enable if the informations were filled 
		//btnSave.disableProperty().bind(somethingSelected.not().or(filledFields));
		
		// Fill the fields in UI after select a register in TableView
		tblServers.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
			if (n != null) {
				//LocalDate data = null;
				//data = n.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				txtHost.setText(n.getHost());
				txtIp.setText(n.getIp());
				txtXml.setText(n.getXmlDir());
				txtSenf.setText(n.getSenfDir());
				txtHome.setText(n.getHomeDir());
				txtFtp.setText(n.getFtpDir());				
				//dpVencimento.setValue(data);
			}
		});
	}
	
	private void updateComboBox() {		
		observableListServer = FXCollections.observableArrayList(service.findAll());
		cbbFtpServer.setItems(observableListServer);	
		
		cbbFtpServer.setOnAction((event) -> {
		    //Button was clicked, do something...
			//Server selectedServer = cbbFtpServer.getSelectionModel().getSelectedItem();
			//txtFtpResult.appendText(selectedServer.getHost() + "\n");
		});
	}
	
	private void toggleGroupControl() {
		rdGroupFtp.selectedToggleProperty().addListener((ov, ot, nt) -> {
			if (rdGroupFtp.getSelectedToggle() != null) {
				RadioButton rdnBt = (RadioButton) nt.getToggleGroup().getSelectedToggle();
				//System.out.println(rdGroupFtp.getSelectedToggle().getUserData().toString());
				if (rdnBt.getText().equals("XML")) {
					txtFtpFile.setPromptText("Put NFe Key");
					txtFtpFile.setText("");
				} else if (rdnBt.getText().equals("SENF")){
					txtFtpFile.setPromptText("Put SENF Number");
					txtFtpFile.setText("");
				} else {
					txtFtpFile.setPromptText("Put file name");
					txtFtpFile.setText("");
				}
				
				
			}
		});
		

	}
	
	private int maxLenght (ToggleGroup tgp) {
		int maxLenght = 44;
		
		RadioButton rdnBt = (RadioButton) tgp.getSelectedToggle();
		if (rdnBt.getText().equals("XML")) {
			maxLenght = 44;
		} else if (rdnBt.getText().equals("SENF")) {
			maxLenght = 8;
		} else {
			maxLenght = 500;
		}
		
		return maxLenght;
	}
	
	private boolean validateFtp(RadioButton rdnBt) {
		
		String errorMessage = "";
		if (cbbFtpServer.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Servidor não selecionado!\n";
		}
		if (txtFtpUser.getText().isEmpty()) {
			errorMessage += "Usuário não informado!\n";
		}
		if (txtFtpPasswd.getText().isEmpty()) {
			errorMessage += "Senha não informada!\n";
		}
		if (txtFtpFile.getText().isEmpty()) {			
			if (rdnBt.getText().equals("XML")) {
				errorMessage += "Chave da NFe não informada!\n";				
			} else if (rdnBt.getText().equals("SENF")) {
				errorMessage += "Número da SENF não informada!\n";
			} else {
				errorMessage += "Nenhum arquivo para download foi informado!\n";
			}			
		}
		
		if (errorMessage.isEmpty()) {
			return true;
		} else {
			txtFtpResult.appendText(errorMessage);
			/*
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Campos Inválidos");
          //alert.setHeaderText("Por favor, corrija os campos inválidos");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            */
			return false;
		}
		
	}

}