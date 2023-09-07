package cse360.controllers;

import cse360.model.*;
import cse360.service.LoginService;
import cse360.service.NurseService;
import cse360.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class PatientController {

    public ComboBox dateDropdown;
    public TextArea visitTextArea;
    public TextField firstName;
    public TextField lastName;
    public TextField dob;
    public TextField email;
    public TextField phoneNo;
    public TextField insurance;
    public TextField age;
    public TextField pharmacy;
    public Label profileErrorLabel;
    public ComboBox messageDropdown;
    public TextArea messageBox;
    public TextField messageText;
    public Label messageError;
    private LoginService loginService;
    private Stage primaryStage;
    private NurseService nurseService;
    private UserService userService;
    private String logEmail;
    private Patient logPatient;
    private List<Visit> visitList;
    private List<User> docNurseList;

    /**
     *
     * @param primaryStage
     * @param loginService
     * @param nurseService
     * @param userService
     */
    public void initService(Stage primaryStage, LoginService loginService, NurseService nurseService, UserService userService, String email){
        this.loginService = loginService;
        this.primaryStage = primaryStage;
        this.nurseService = nurseService;
        this.userService = userService;
        this.logEmail = email;

    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        URL url = getClass().getResource("/login.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.initService(primaryStage, loginService, nurseService, userService);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public void loadVisitInfo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if(visitList != null){
            String foundValue = dateDropdown.getValue().toString();
            int index = Integer.parseInt(foundValue.substring(foundValue.length()-1));
            Doctor doctor = userService.searchDoctor(logPatient.getDoctorId());
            Visit select = visitList.get(index);

            if(select.getBP()==null){
                select.setBP("");
            }
            if(select.getComments()==null){
                select.setComments("");
            }
            if(select.getDiagnostic()==null){
                select.setDiagnostic("");
            }
            if(select.getMedication()==null){
                select.setMedication("");
            }

            visitTextArea.setText("Visit Date: "+select.getDate()+"\n\n"+
                    "Doctor: "+doctor.getFirstName()+" "+doctor.getLastName()+"\n\n"+
                    "Height: "+ select.getHeight()+"\n\n"+
                    "Weight:  "+ select.getWeight()+"\n\n"+
                    "Body Temp:  "+ select.getBodyTemp()+"\n\n"+
                    "Blood pressure:  "+ select.getBP()+"\n\n"+
                    "Nurse's comments:  "+ select.getComments()+"\n\n"+
                    "Diagnostic:  "+ select.getDiagnostic()+"\n\n"+
                    "Medication:  "+ select.getMedication()+"\n\n");
        }
    }

    public void loadVisitDropdown(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        logPatient = userService.getPatientByEmail(this.logEmail);
        visitList =  nurseService.getVisitList(logPatient.getId());
        for(int x = 0; x<visitList.size(); x++){
            dateDropdown.getItems().add(visitList.get(x).getDate()+"  "+x);
        }
    }

    public void viewImmunization(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        User patient = userService.searchUserByEmail(email.getText());
        URL url = getClass().getResource("/immunization.fxml");
        System.out.println(url.toString());
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        ImmunizationController immunizationController = loader.getController();
        immunizationController.initService(userService, patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getDOB());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Immunization Record");
        stage.setScene(scene);
        stage.show();

    }

    public void loadProfile(Event event) throws SQLException, ClassNotFoundException {
        logPatient = userService.getPatientByEmail(this.logEmail);
        logPatient.setAge(logPatient.calculateAge(logPatient.getDOB()));
        firstName.setText(logPatient.getFirstName()); lastName.setText(logPatient.getLastName());
        dob.setText(logPatient.getDOB()); age.setText(Integer.toString(logPatient.getAge()));
        insurance.setText(logPatient.getInsurance()); pharmacy.setText(logPatient.getPharmacy());
        email.setText(logPatient.getEmail()); phoneNo.setText(logPatient.getPhoneNumber());
    }

    public void updatePatient(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        logPatient = userService.getPatientByEmail(this.logEmail);
        if(phoneNo.getText().isEmpty()){
            profileErrorLabel.setText("Please type in Phone no.");
        }
        else {
            nurseService.editProfile(logPatient.getId(), phoneNo.getText());
            profileErrorLabel.setText("Updated Successfully");
        }
    }

    public void loadMessages(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        messageBox.setText("");
        logPatient = userService.getPatientByEmail(logEmail);
        docNurseList = userService.getDocNurseList();
        if(docNurseList.isEmpty()){
            messageError.setText("No messages found");
        }
        else {
            if(null != messageDropdown.getValue()) {
                String select = messageDropdown.getValue().toString();
                int index = Integer.parseInt(select.substring(select.length() - 1));
                User user = docNurseList.get(index);
                List<String> messages = userService.getMessages(logPatient.getId(), user.getId());
                if(messages.isEmpty()){
                    messageError.setText("no messages conversed");
                    return;
                }
                StringBuilder str = new StringBuilder();
                for (String s : messages
                ) {
                    str.append(s+"\n");
                }
                messageBox.setText(str.toString());
                messageError.setText("Messages found");
            }
        }
    }

    public void loadUserComboBox(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        docNurseList = userService.getDocNurseList();
        if(docNurseList != null && !docNurseList.isEmpty()){
            for(int x = 0 ; x<docNurseList.size(); x++){
                messageDropdown.getItems().add(docNurseList.get(x).getFirstName()+" "+docNurseList.get(x).getLastName()+" "+x);
            }
        }
    }

    public void sendMessage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        messageBox.setText("");
        if(messageDropdown.getValue() == null){
            messageError.setText("Please select patient.");
        }
        else {
            if(messageText.getText().isEmpty()){
                messageError.setText("Please type a message.");
            }
            else {
                //Date date = new Date();
                logPatient = userService.getPatientByEmail(logEmail);
                String select = messageDropdown.getValue().toString();
                int index = Integer.parseInt(select.substring(select.length() - 1));
                User user = docNurseList.get(index);
                String message = logPatient.getFirstName()+": "+messageText.getText();
                userService.addDate(logPatient.getId(), user.getId(), message);
                messageError.setText("Message sent.");
                messageText.setText("");

                if(docNurseList.isEmpty()){
                    messageError.setText("No messages found");
                }
                else {
                    if(null != messageDropdown.getValue()) {
                        select = messageDropdown.getValue().toString();
                        index = Integer.parseInt(select.substring(select.length() - 1));
                        user = docNurseList.get(index);
                        List<String> messages = userService.getMessages(logPatient.getId(), user.getId());
                        if(messages.isEmpty()){
                            messageError.setText("no messages conversed");
                            return;
                        }
                        StringBuilder str = new StringBuilder();
                        for (String s : messages
                        ) {
                            str.append(s+"\n");
                        }
                        messageBox.setText(str.toString());
                    }
                }
            }
        }
    }
}
