package cse360.controllers;

import cse360.model.User;
import cse360.service.LoginService;
import cse360.service.NurseService;
import cse360.service.UserService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginController {
    public ComboBox type;
    public TextField email;
    public TextField password;
    public Label errorLabel;

    private LoginService loginService;
    private NurseService nurseService;
    private UserService userService;
    private Stage primaryStage;

    public LoginController(){}

    /**
     *
     * @param primaryStage
     * @param loginService
     */
    public void initService(Stage primaryStage, LoginService loginService, NurseService nurseService, UserService userService){
        this.loginService = loginService;
        this.primaryStage = primaryStage;
        this.nurseService = nurseService;
        this.userService = userService;
    }

    public void login(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {

        if(email.getText() == null || password.getText() == null || type.getValue()==null){
            errorLabel.setText("select all parameters");
        }
        else{
            User success = loginService.login(email.getText(), password.getText(), type.getValue().toString());
            if(null == success){
                System.out.println("unsuccessful");
                errorLabel.setText("Login unsuccessful");
            }
            else{
                errorLabel.setText("Login successfull");
                if(type.getValue().toString().equals("Doctor")) {
                    URL url = getClass().getResource("/doctorView.fxml");
                    System.out.println(url.toString());
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent root = loader.load();
                    DoctorController doctorController = loader.getController();
                    doctorController.initService(primaryStage, loginService, nurseService, userService, email.getText());
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
                else if(type.getValue().toString().equals("Nurse")){
                    URL url = getClass().getResource("/nurseView.fxml");
                    System.out.println(url.toString());
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent root = loader.load();
                    NurseController nurseController = loader.getController();
                    nurseController.initService(primaryStage, loginService, nurseService, userService, email.getText());
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
                else{
                    URL url = getClass().getResource("/patientView.fxml");
                    System.out.println(url.toString());
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent root = loader.load();
                    PatientController patientController = loader.getController();
                    patientController.initService(primaryStage, loginService, nurseService, userService, email.getText());
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
            }
        }

    }


    public void takeToCreateAccount(MouseEvent mouseEvent) throws IOException {
        URL url = getClass().getResource("/createAccount.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        createAccountController createAccountController = loader.getController();
        createAccountController.initService(primaryStage, loginService, nurseService, userService);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
