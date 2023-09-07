package cse360.controllers;

import cse360.model.*;
import cse360.service.LoginService;
import cse360.service.NurseService;
import cse360.service.UserService;
import cse360.util.RandomNumberUtil;
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

public class NurseController {


    public TextField profileFirstName;
    public TextField profileLastName;
    public TextField profileDob;
    public TextField profileEmail;
    public TextField profilePhoneNo;
    public TextField profileAge;
    public Label profileErrorLabel;
    public TextArea medAndDia;
    public TextArea messageBox;
    public TextField messageText;
    public ComboBox messagePDropdown;
    public TextField messageDate;
    public Label messageError;

    private String loggedEmail;
    public TextField firstName;
    public TextField lastName;
    public TextField dob;
    public TextField insurance;
    public TextField email;
    public TextField phoneNumber;
    public TextField age;
    public TextField pharmacy;
    public TextField doctorFirstName;
    public TextField doctorLastName;
    public Label errorLabel;
    public ComboBox patientListDropdown;
    public TextField searchDob;
    public TextField height;
    public TextField weight;
    public TextField bp;
    public TextArea comment;
    public TextField temp;
    public TextField vaccine;
    public TextField addDate;
    public Label visitErrorLabel;
    public TextField visitSearchDob;// search dob for visit tab
    public ComboBox existingPatientDropdown;
    public ComboBox dateDropdown;
    private User loggedNurse;


    private LoginService loginService;
    private NurseService nurseService;
    private UserService userService;
    private Stage primaryStage;
    private List<Patient> patientList;
    private List<Patient> patientListVisit;
    private List<Patient> messagePatientList;

    public void initService(Stage primaryStage, LoginService loginService, NurseService nurseService, UserService userService, String email) throws SQLException, ClassNotFoundException {
        this.loginService = loginService;
        this.primaryStage = primaryStage;
        this.nurseService = nurseService;
        this.userService =  userService;
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

    public void createPatient(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if(firstName.getText().isEmpty()|| lastName.getText().isEmpty() || dob.getText().isEmpty()
        || insurance.getText().isEmpty() || email.getText().isEmpty() || phoneNumber.getText().isEmpty()
        || age.getText().isEmpty() || pharmacy.getText().isEmpty() || doctorFirstName.getText().isEmpty() ||
        doctorFirstName.getText().isEmpty()){
            errorLabel.setText("Please fill out all fields.");
        }
        else{
            Doctor foundDoctor = userService.searchDoctor(doctorFirstName.getText(), doctorLastName.getText());
            if(foundDoctor == null){
                errorLabel.setText("Doctor not found.");
            }
            else {
                Patient addPatient = new Patient(RandomNumberUtil.getRandomNumber(), firstName.getText(), lastName.getText(), dob.getText(), email.getText(),
                        phoneNumber.getText(), null, insurance.getText(), pharmacy.getText(), foundDoctor.getId());
                boolean result = nurseService.createPatient(addPatient);
                if(!result){
                    errorLabel.setText("Patient with email already exists.");
                }
                else{
                    errorLabel.setText("Patient created.");
                }
            }

        }

    }

    public void searchForSpecifiedPatientList(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        firstName.setText(""); lastName.setText(""); dob.setText(""); insurance.setText(""); email.setText("");
        phoneNumber.setText(""); age.setText(""); pharmacy.setText(""); doctorFirstName.setText(""); doctorLastName.setText("");

        if(searchDob.getText().isEmpty()){
            errorLabel.setText("Please enter DOB.");
        }
        else{
            patientList = nurseService.getSpecifiedPatientList(searchDob.getText());
            if(patientList.isEmpty()){
                errorLabel.setText("No patients found.");
                if(patientListDropdown.getItems() != null) {
                    patientListDropdown.getItems().clear();
                }
            }
            else{
                errorLabel.setText("Patients found.");
                patientListDropdown.getItems().clear();
                for (Patient p:patientList
                     ) {
                    patientListDropdown.getItems().add(p.getFirstName() + " " + p.getLastName());
                }

            }
        }
    }

    public void displayPatient(ActionEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (patientListDropdown.getValue() != null) {
            String foundValue = patientListDropdown.getValue().toString();
            if (foundValue != null) {
                for (Patient patient : patientList
                ) {
                    String check = patient.getFirstName() + " " + patient.getLastName();
                    if (check.equals(foundValue)) {
                        firstName.setText(patient.getFirstName());
                        lastName.setText(patient.getLastName());
                        dob.setText(patient.getDOB());
                        insurance.setText(patient.getInsurance());
                        email.setText(patient.getEmail());
                        phoneNumber.setText(patient.getPhoneNumber());
                        int value = Integer.parseInt(patient.getDOB().substring(6));
                        patient.setAge(2021 - value);
                        age.setText(String.valueOf(patient.getAge()));
                        pharmacy.setText(patient.getPharmacy());
                        Doctor foundDoctor = userService.searchDoctor(patient.getDoctorId());
                        doctorFirstName.setText(foundDoctor.getFirstName());
                        doctorLastName.setText(foundDoctor.getLastName());
                    }
                }

            }
        }
    }

    public void editPatient(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if(insurance.getText().isEmpty() || pharmacy.getText().isEmpty() || phoneNumber.getText().isEmpty() || email.getText().isEmpty()){
            errorLabel.setText("Please fill all editable fields.");
        }
        else{
            User foundUser = userService.searchUserByEmail(email.getText());
            if(foundUser != null){
                nurseService.editPatient(foundUser.getId(), insurance.getText(), pharmacy.getText(), phoneNumber.getText());
                errorLabel.setText("Patient edited successfully");
            }
            else{
                errorLabel.setText("Patient with email does not exist.");
            }
        }

    }

    public void openImmunization(MouseEvent mouseEvent) throws IOException, SQLException, ClassNotFoundException {
        if(firstName.getText().isEmpty()|| lastName.getText().isEmpty() || dob.getText().isEmpty()
                || insurance.getText().isEmpty() || email.getText().isEmpty() || phoneNumber.getText().isEmpty()
                || age.getText().isEmpty() || pharmacy.getText().isEmpty() || doctorFirstName.getText().isEmpty() ||
                doctorFirstName.getText().isEmpty()){
            errorLabel.setText("Please select existing patient");
        }
        else{
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
    }

    public void createVisit(MouseEvent mouseEvent) throws SQLException {
        if(addDate.getText().isEmpty() || height.getText().isEmpty() || weight.getText().isEmpty() ||
        temp.getText().isEmpty()){
            visitErrorLabel.setText("Please fill out all fields.");
        }
        else{
            if (existingPatientDropdown.getValue() != null) {
                String foundValue = existingPatientDropdown.getValue().toString();
                String id = "";
                if (foundValue != null) {
                    for (Patient patient : patientListVisit
                    ) {
                        String check = patient.getFirstName() + " " + patient.getLastName();
                        if (check.equals(foundValue)) {
                            id = patient.getId();
                        }
                    }
                }
                Visit visit = new Visit(RandomNumberUtil.getRandomNumber(),id, addDate.getText(), height.getText(), weight.getText(), temp.getText()
                ,bp.getText(), comment.getText());
                nurseService.createVisit(visit);
                visitErrorLabel.setText("Visit added successfully");
            }
            else{
                visitErrorLabel.setText("Please choose Patient.");
            }
        }

    }



    public void loadVisit(ActionEvent mouseEvent) throws SQLException {
        if(dateDropdown.getValue()!=null){

            if (existingPatientDropdown.getValue() != null) {
                String foundValue = existingPatientDropdown.getValue().toString();
                String id = "";
                if (foundValue != null) {
                    for (Patient patient : patientListVisit
                    ) {
                        String check = patient.getFirstName() + " " + patient.getLastName();
                        if (check.equals(foundValue)) {
                            id = patient.getId();
                        }
                    }
                }
                //call visit list
                List<Visit> visitList = nurseService.getVisitList(id);
                for (Visit v:visitList
                     ) {
                    if(v.getDate().equals(dateDropdown.getValue().toString())){
                        addDate.setText(v.getDate()); height.setText(v.getHeight()); weight.setText(v.getWeight());
                        bp.setText(v.getBP()); temp.setText(v.getBodyTemp()); comment.setText(v.getComments());
                        if(v.getDiagnostic() ==null && v.getMedication()==null){
                            medAndDia.setText("Diagnostic:  " + "\nMedication:  ");
                        }
                        else if(v.getDiagnostic() == null){
                            medAndDia.setText("Diagnostic:  " + "\nMedication:  "+v.getMedication());
                        }
                        else if(v.getMedication() == null){
                            medAndDia.setText("Diagnostic:  " +v.getDiagnostic()+"\nMedication:  ");
                        }
                        else {
                            medAndDia.setText("Diagnostic:  " + v.getDiagnostic() + "\nMedication:  " + v.getMedication());
                        }

                    }
                }
            }
        }
    }

    public void searchForVisitPatient(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        existingPatientDropdown.getItems().clear();
        if(visitSearchDob.getText().isEmpty()){
            visitErrorLabel.setText("Please fill out date field.");
        }
        else{
            patientListVisit = nurseService.getSpecifiedPatientList(visitSearchDob.getText());
            if(patientListVisit.isEmpty()){
                visitErrorLabel.setText("No patients found.");
                if(existingPatientDropdown.getItems() != null) {
                    existingPatientDropdown.getItems().clear();
                }
            }
            else{
                visitErrorLabel.setText("Patients found.");
                existingPatientDropdown.getItems().clear();
                for (Patient p:patientListVisit
                ) {
                    existingPatientDropdown.getItems().add(p.getFirstName() + " " + p.getLastName());
                }
            }
        }
    }

    public void displayDateList(ActionEvent actionEvent) throws SQLException {
        dateDropdown.getItems().clear();
        if (existingPatientDropdown.getValue() != null) {
            String foundValue = existingPatientDropdown.getValue().toString();
            String id = "";
            if (foundValue != null) {
                for (Patient patient : patientListVisit
                ) {
                    String check = patient.getFirstName() + " " + patient.getLastName();
                    if (check.equals(foundValue)) {
                        id = patient.getId();
                    }
                }
            }
            //call visit list
            List<Visit> visitList = nurseService.getVisitList(id);
            if(visitList!= null){
                for (Visit v:visitList
                ) {
                    dateDropdown.getItems().add(v.getDate());
                }
            }
        }

    }

    public void addImmunization(MouseEvent mouseEvent) throws SQLException {
        if(existingPatientDropdown.getValue() == null){
            visitErrorLabel.setText("Please select a patient");
        }
        else if(addDate.getText().isEmpty()){
            visitErrorLabel.setText("Please enter date.");
        }
        else{
            String foundValue = existingPatientDropdown.getValue().toString();
            String id = "";
            for (Patient patient : patientListVisit
            ) {
                String check = patient.getFirstName() + " " + patient.getLastName();
                if (check.equals(foundValue)) {
                    id = patient.getId();
                }
            }
            nurseService.addImmunization(id, vaccine.getText(), addDate.getText());
            visitErrorLabel.setText("Vaccine record added");
        }
    }

    public void updateProfile(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if(profilePhoneNo.getText().isEmpty()){
            profileErrorLabel.setText("Please type in Phone no.");
        }
        else {
            nurseService.editProfile(loggedNurse.getId(), profilePhoneNo.getText());
            profileErrorLabel.setText("Updated Successfully");
        }
    }

    public void loadProfile(Event event) throws SQLException, ClassNotFoundException {
        profileErrorLabel.setText("");
        User logNurse = userService.searchUserByEmail(loggedEmail);
        this.loggedNurse = logNurse;
        profileFirstName.setText(logNurse.getFirstName());
        profileLastName.setText(logNurse.getLastName());
        profileDob.setText(logNurse.getDOB());
        profileEmail.setText(logNurse.getEmail());
        profilePhoneNo.setText(logNurse.getPhoneNumber());
        profileAge.setText(Integer.toString(logNurse.calculateAge(logNurse.getDOB())));
    }

    public void loadMessages(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        messageBox.setText("");
        loggedNurse = userService.searchUserByEmail(loggedEmail);
        messagePatientList = nurseService.getSpecifiedPatientList(messageDate.getText());
        if(messagePatientList.isEmpty()){
            messageError.setText("No messages found");
        }
        else {
            if(null != messagePDropdown.getValue()) {
                String select = messagePDropdown.getValue().toString();
                int index = Integer.parseInt(select.substring(select.length() - 1));
                Patient selectedPatient = messagePatientList.get(index);
                List<String> messages = userService.getMessages(selectedPatient.getId(), loggedNurse.getId());
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

    public void loadMessageDropdown(MouseEvent contextMenuEvent) throws SQLException, ClassNotFoundException {
        messagePDropdown.getItems().clear();
        if(messageDate.getText().isEmpty()){
            messageError.setText("Please enter DOB.");
        }
        else{
            loggedNurse = userService.searchUserByEmail(loggedEmail);
            messagePatientList = nurseService.getSpecifiedPatientList(messageDate.getText());
            if(messagePatientList.isEmpty()){
                messageError.setText("No patients found.");
                if(messagePDropdown.getItems() != null){
                    messagePDropdown.getItems().clear();
                }
            }
            else{
                messageError.setText("Patients found.");
                messagePDropdown.getItems().clear();
                for(int x = 0; x < messagePatientList.size(); x++){
                    messagePDropdown.getItems().add(messagePatientList.get(x).getFirstName()+" "+ messagePatientList.get(x).getLastName()+"  "+x);
                }
            }
        }
    }

    public void sendMessage(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        messageBox.setText("");
        if(messagePDropdown.getValue() == null){
            messageError.setText("Please select patient.");
        }
        else {
            if(messageText.getText().isEmpty()){
                messageError.setText("Please type a message.");
            }
            else {
                //Date date = new Date();
                loggedNurse = userService.searchUserByEmail(loggedEmail);
                String select = messagePDropdown.getValue().toString();
                int index = Integer.parseInt(select.substring(select.length() - 1));
                Patient selectedPatient = messagePatientList.get(index);
                String message = loggedNurse.getFirstName()+": "+messageText.getText();
                userService.addDate(selectedPatient.getId(), loggedNurse.getId(), message);
                messageError.setText("Message sent.");
                messageText.setText("");

                if(messagePatientList.isEmpty()){
                    messageError.setText("No messages found");
                }
                else {
                    if(null != messagePDropdown.getValue()) {
                        select = messagePDropdown.getValue().toString();
                        index = Integer.parseInt(select.substring(select.length() - 1));
                        selectedPatient = messagePatientList.get(index);
                        List<String> messages = userService.getMessages(selectedPatient.getId(), loggedNurse.getId());
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
