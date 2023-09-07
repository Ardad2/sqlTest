package cse360.controllers;

import cse360.model.Patient;
import cse360.model.User;
import cse360.model.Visit;
import cse360.service.LoginService;
import cse360.service.NurseService;
import cse360.service.UserService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class DoctorController {

    public TextField profileFirstName;
    public TextField profileLastName;
    public TextField profileDob;
    public TextField profileAge;
    public TextField profilePhoneNo;
    public Label profileErrorLabel;
    public TextField profileEmail;
    public ComboBox visitPatientDropdown;
    public TextArea comments;
    public TextArea diagnostic;
    public TextField visitHeight;
    public TextField visitWeight;
    public TextField visitBp;
    public ComboBox visitDateDropdown;
    public TextField visitTemp;
    public TextField medication;
    public TextField visitDob;
    public Label visitErrorLabel;


    public Label patErrorLabel;
    public ComboBox patPatientDropdown;
    public TextField patientDob;
    public TextField patFirstName;
    public TextField patLastName;
    public TextField patDob;
    public TextField patAge;
    public TextField patInsurance;
    public TextField patPharmacy;
    public TextField patEmail;
    public TextField patPhoneNo;
    public ComboBox messagesPDropdown;
    public TextField messagesDate;
    public Label messagesError;
    public TextField messageText;
    public TextArea messageBox;

    private LoginService loginService;
    private Stage primaryStage;
    private NurseService nurseService;
    private UserService userService;
    private User loggedDoctor;
    private String loggedEmail;
    private List<Patient> visitPatientList;
    private List<Patient> patPatientList;
    private List<Visit> visitList;
    private List<Patient> messagesPatientList;


    public void initService(Stage primaryStage, LoginService loginService, NurseService nurseService, UserService userService, String email){
        this.loginService = loginService;
        this.primaryStage = primaryStage;
        this.nurseService = nurseService;
        this.userService = userService;
        this.loggedEmail = email;
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


    public void loadProfile(Event event) throws SQLException, ClassNotFoundException {
        profileErrorLabel.setText("");
        User logDoctor = userService.searchUserByEmail(loggedEmail);
        this.loggedDoctor = logDoctor;
        profileFirstName.setText(loggedDoctor.getFirstName());
        profileLastName.setText(loggedDoctor.getLastName());
        profileDob.setText(loggedDoctor.getDOB());
        profileEmail.setText(loggedDoctor.getEmail());
        profilePhoneNo.setText(loggedDoctor.getPhoneNumber());
        profileAge.setText(Integer.toString(loggedDoctor.calculateAge(loggedDoctor.getDOB())));
    }

    public void updateProfile(MouseEvent mouseEvent) throws SQLException {
        if(profilePhoneNo.getText().isEmpty()){
            profileErrorLabel.setText("Please type in Phone no.");
        }
        else {
            nurseService.editProfile(loggedDoctor.getId(), profilePhoneNo.getText());
            profileErrorLabel.setText("Updated Successfully");
        }
    }

    public void loadVisitInfo(ActionEvent actionEvent) throws SQLException {
        visitErrorLabel.setText("");
        if(visitDateDropdown.getValue() == null){
            visitErrorLabel.setText("Please select Date from dropdown.");
        }
        else{
            if(visitDateDropdown.getValue()!=null){
                if (visitPatientDropdown.getValue() != null) {
                    String foundValue = visitPatientDropdown.getValue().toString();
                    String id = "";
                    if (foundValue != null) {
                        for (Patient patient : visitPatientList
                        ) {
                            String check = patient.getFirstName() + " " + patient.getLastName();
                            if (check.equals(foundValue)) {
                                id = patient.getId();
                            }
                        }
                    }
                    //call visit list
                    List<Visit> visitList = nurseService.getVisitList(id);
                    int x = 0;
                    for (Visit v:visitList
                    ) {
                        if((v.getDate()+"  "+x).equals(visitDateDropdown.getValue().toString())){
                            visitHeight.setText(v.getHeight()); visitWeight.setText(v.getWeight());
                            visitBp.setText(v.getBP()); visitTemp.setText(v.getBodyTemp()); comments.setText(v.getComments());
                            diagnostic.setText(""); medication.setText("");
                            if(v.getDiagnostic() ==null && v.getMedication()==null){
                                diagnostic.setText(""); medication.setText("");
                            }
                            else if(v.getDiagnostic() == null){
                                diagnostic.setText(""); medication.setText(v.getMedication());
                            }
                            else if(v.getMedication() == null){
                                diagnostic.setText(v.getDiagnostic()); medication.setText("");
                            }
                            else {
                                diagnostic.setText(v.getDiagnostic()); medication.setText(v.getMedication());
                            }
                        }
                        x++;
                    }
                }
            }
        }
    }

    public void updateVisit(MouseEvent mouseEvent) throws SQLException {
        if(visitPatientDropdown.getValue() == null){
            visitErrorLabel.setText("Please select patient");
        }
        else if(visitDateDropdown.getValue() == null){
            visitErrorLabel.setText("Please select a visit to edit");
        }
        else{
            String select = visitDateDropdown.getValue().toString();
            int index = Integer.parseInt(select.substring(select.length()-1));
            if(visitList!=null){
                Visit updateVisit = visitList.get(index);
                updateVisit.setHeight(visitHeight.getText()); updateVisit.setWeight(visitWeight.getText());
                updateVisit.setBP(visitBp.getText()); updateVisit.setBodyTemp(visitTemp.getText());
                updateVisit.setMedication(medication.getText()); updateVisit.setDiagnostic(diagnostic.getText());
                userService.editVisit(updateVisit);
                visitErrorLabel.setText("Visit updated.");
            }
        }

    }

    public void searchForPatients(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        visitPatientDropdown.getItems().clear();
        if(visitDob.getText().isEmpty()){
            visitErrorLabel.setText("Please enter date:");
        }
        else{
            loggedDoctor = userService.searchUserByEmail(loggedEmail);
            visitPatientList = userService.getDoctorsPatientList(loggedDoctor.getId(), visitDob.getText());
            if(visitPatientList.isEmpty()){
                visitErrorLabel.setText("No Patients Found");
                if(visitPatientDropdown.getItems() != null) {
                    visitPatientDropdown.getItems().clear();
                }
            }
            else{
                visitErrorLabel.setText("Patients found.");
                visitPatientDropdown.getItems().clear();
                for (Patient p:visitPatientList
                ) {
                    visitPatientDropdown.getItems().add(p.getFirstName() + " " + p.getLastName());
                }
            }
        }
    }

    public void searchForDates(ActionEvent actionEvent) throws SQLException {
        visitDateDropdown.getItems().clear();
        if (visitPatientDropdown.getValue() != null) {
            String foundValue = visitPatientDropdown.getValue().toString();
            String id = "";
            if (foundValue != null) {
                for (Patient patient : visitPatientList
                ) {
                    String check = patient.getFirstName() + " " + patient.getLastName();
                    if (check.equals(foundValue)) {
                        id = patient.getId();
                    }
                }
            }
            //call visit list
            visitList = nurseService.getVisitList(id);
            if(visitList!= null){
                for (int x = 0; x<visitList.size(); x++) {
                    visitDateDropdown.getItems().add(visitList.get(x).getDate()+"  "+x);
                }
            }
        }
    }

    public void displayPatInfo(ActionEvent actionEvent) {
        if(patPatientDropdown.getValue() == null){
            patErrorLabel.setText("Please select Date from dropdown.");
        }
        else{
            String foundValue = patPatientDropdown.getValue().toString();
            int index = Integer.parseInt(foundValue.substring(foundValue.length()-1));
            Patient select= patPatientList.get(index);
            patFirstName.setText(select.getFirstName());
            patLastName.setText(select.getLastName());
            patDob.setText(select.getDOB());
            patEmail.setText(select.getEmail());
            int value = Integer.parseInt(select.getDOB().substring(6));
            select.setAge(select.calculateAge(select.getDOB()));
            patAge.setText(Integer.toString(select.getAge()));
            patInsurance.setText(select.getInsurance());
            patPharmacy.setText(select.getPharmacy());
            patPhoneNo.setText(select.getPhoneNumber());

        }
    }

    public void openImmunization(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, IOException {
        if(patPatientDropdown.getValue() == null){
            patErrorLabel.setText("Please select patient.");
        }
        else{
            User patient = userService.searchUserByEmail(patEmail.getText());
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
    }

    public void searchDoctorsPatList(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        patPatientDropdown.getItems().clear();
        if(patientDob.getText().isEmpty()){
            patErrorLabel.setText("Please enter Dob.");
        }
        else{
            loggedDoctor = userService.searchUserByEmail(loggedEmail);
            patPatientList = userService.getDoctorsPatientList(loggedDoctor.getId(), patientDob.getText());
            if(patPatientList.isEmpty()){
                patErrorLabel.setText("No patients found.");
                if(patPatientDropdown.getItems() != null) {
                    visitPatientDropdown.getItems().clear();
                }
            }
            else{
                patErrorLabel.setText("Patients found.");
                patPatientDropdown.getItems().clear();
                for (int x = 0; x<patPatientList.size();x++) {
                    patPatientDropdown.getItems().add(patPatientList.get(x).getFirstName() + " " + patPatientList.get(x).getLastName()+"  "+x);
                }
            }

        }
    }

    public void loadMessages(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        messageBox.setText("");
        loggedDoctor = userService.searchUserByEmail(loggedEmail);
        messagesPatientList = userService.getDoctorsPatientList(loggedDoctor.getId(), messagesDate.getText());
        if(messagesPatientList.isEmpty()){
            messagesError.setText("No messages found");
        }
        else {
            if(null != messagesPDropdown.getValue()) {
                String select = messagesPDropdown.getValue().toString();
                int index = Integer.parseInt(select.substring(select.length() - 1));
                Patient selectedPatient = messagesPatientList.get(index);
                List<String> messages = userService.getMessages(selectedPatient.getId(), loggedDoctor.getId());
                if(messages.isEmpty()){
                    messagesError.setText("no messages conversed");
                    return;
                }
                StringBuilder str = new StringBuilder();
                for (String s : messages
                ) {
                    str.append(s+"\n");
                }
                messageBox.setText(str.toString());
                messagesError.setText("Messages found");
            }
        }
    }

    public void loadMessagesDropdown(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        messagesPDropdown.getItems().clear();
        if(messagesDate.getText().isEmpty()){
            messagesError.setText("Please enter DOB.");
        }
        else{
            loggedDoctor = userService.searchUserByEmail(loggedEmail);
            messagesPatientList = userService.getDoctorsPatientList(loggedDoctor.getId(), messagesDate.getText());
            if(messagesPatientList.isEmpty()){
                messagesError.setText("No patients found.");
                if(messagesPDropdown.getItems() != null){
                    messagesPDropdown.getItems().clear();
                }
            }
            else{
                messagesError.setText("Patients found.");
                messagesPDropdown.getItems().clear();
                for(int x = 0; x < messagesPatientList.size(); x++){
                    messagesPDropdown.getItems().add(messagesPatientList.get(x).getFirstName()+" "+messagesPatientList.get(x).getLastName()+"  "+x);
                }
            }
        }
    }

    public void sendMessage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        messageBox.setText("");
        if(messagesPDropdown.getValue() == null){
            messagesError.setText("Please select patient.");
        }
        else {
            if(messageText.getText().isEmpty()){
                messagesError.setText("Please type a message.");
            }
            else {
                //Date date = new Date();
                loggedDoctor = userService.searchUserByEmail(loggedEmail);
                String select = messagesPDropdown.getValue().toString();
                int index = Integer.parseInt(select.substring(select.length() - 1));
                Patient selectedPatient = messagesPatientList.get(index);
                String message = loggedDoctor.getFirstName()+": "+messageText.getText();
                userService.addDate(selectedPatient.getId(), loggedDoctor.getId(), message);
                messagesError.setText("Message sent.");
                messageText.setText("");

                if(messagesPatientList.isEmpty()){
                    messagesError.setText("No messages found");
                }
                else {
                    if(null != messagesPDropdown.getValue()) {
                        select = messagesPDropdown.getValue().toString();
                        index = Integer.parseInt(select.substring(select.length() - 1));
                        selectedPatient = messagesPatientList.get(index);
                        List<String> messages = userService.getMessages(selectedPatient.getId(), loggedDoctor.getId());
                        if(messages.isEmpty()){
                            messagesError.setText("no messages conversed");
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
