package cse360.controllers;

import cse360.service.LoginService;
import cse360.service.NurseService;
import cse360.service.UserService;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class createAccountController {

    public Button createAccountButton;
    public TextField inputEmail;
    public TextField inputPassword;
    public TextField inputRePassword;
    public Label errorLabel;


    private LoginService loginService;
    private NurseService nurseService;
    private UserService userService;
    private Stage primaryStage;

    public void initService(Stage primaryStage, LoginService loginService, NurseService nurseService, UserService userService){
        this.loginService = loginService;
        this.primaryStage = primaryStage;
        this.nurseService = nurseService;
        this.userService = userService;
    }



    public void clickCreateAccount(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        // Errors
        if(inputEmail.getText() == null || inputPassword.getText() == null || inputRePassword.getText() == null){
            errorLabel.setText("Please fill all info");
            return;
        }
        int result = loginService.setAccountPassword(inputEmail.getText(), inputPassword.getText(), inputRePassword.getText());

        if(result == -1){
            errorLabel.setText("User doesn't exist");
            errorLabel.setAlignment(Pos.CENTER);
        }
        else if(result == -2){
            errorLabel.setText("Password is already set");
            errorLabel.setTextAlignment(TextAlignment.CENTER);
        }
        else if(result == -3){
            errorLabel.setText("Password don't meet criteria or don't match");
            errorLabel.setTextAlignment(TextAlignment.CENTER);
        }
        
        // Successfully set password
        else{
            errorLabel.setText("Password set successfully");
            errorLabel.setAlignment(Pos.CENTER);
        }

    }

    public void backToLogin(MouseEvent mouseEvent) throws IOException {
        URL url = getClass().getResource("/login.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.initService(primaryStage, loginService, nurseService, userService);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
