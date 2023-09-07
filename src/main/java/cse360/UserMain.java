package cse360;


import cse360.controllers.LoginController;
import cse360.dao.NurseDao;
import cse360.dao.UserDao;

import cse360.model.Doctor;
import cse360.model.Nurse;
import cse360.service.LoginService;
import cse360.service.NurseService;
import cse360.service.UserService;
import cse360.util.RandomNumberUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class UserMain  extends Application {

    public static void main(String[] args) {
           launch(args);

        }

//        UserDao userDao = new UserDao();
//        NurseDao nurseDao = new NurseDao();
//        NurseService nurseService = new NurseService(userDao, nurseDao);
//        UserService userService = new UserService(userDao);
//        LoginService loginService = new LoginService(userDao);
//        try {
//            Doctor doctor = new Doctor(RandomNumberUtil.getRandomNumber(), "Devansh", "Bhavsar",
//                    "05/08/2002", "devansh8502@gmail.com", "4807291494", null);
//            Nurse nurse = new Nurse(RandomNumberUtil.getRandomNumber(), "Aashi", "Bhavsar", "12/17/2010",
//                    "aashi.er@gmail.com", "4801234565", null);
//
//
//            userService.addUser(doctor);
//            userService.addUser(nurse);
//            User doctorUser = userDao.getUserByEmailId(doctor.getEmail());
//            User nurseUser = userDao.getUserByEmailId(nurse.getEmail());
//
//            Patient patient = new Patient(RandomNumberUtil.getRandomNumber(), "Neha", "Bhavsar",
//                    "02/20/1979", "neha.er@gmail.com", "4804557740", null, "Aetna",
//                    "CVS", doctorUser.getId(), nurseUser.getId());
//
//            nurseService.addPatientService(patient);
//
//            int output = loginService.setAccount("devansh8502@gmail.com", "12345679", "12345679");
//            if (output == -1) {
//                System.out.println("account already exists");
//            }
//            if (output == -2) {
//                System.out.println("password is inappropriate or password don't match");
//            }
//            if (output == 1) {
//                System.out.println("account password set successfully");
//            }
//
//            User loggedInUser = loginService.login("devansh8502@gmail.com", "12345678", "Doctor");
//            System.out.println("...");
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        UserDao userDao = new UserDao();
        NurseDao nurseDao = new NurseDao();
        NurseService nurseService = new NurseService(userDao, nurseDao);
        UserService userService = new UserService(userDao);
        LoginService loginService = new LoginService(userDao);

        Doctor d1 = new Doctor(RandomNumberUtil.getRandomNumber(), "Meredith", "Grey",
                    "05/08/1980", "meredith@gmail.com", "4805781294", "doctormeredith");
        Doctor d2 = new Doctor(RandomNumberUtil.getRandomNumber(), "Grace", "Chavez",
                "12/23/1982", "grace@gmail.com", "4559083285", "doctorgrace");
        Nurse n1 = new Nurse(RandomNumberUtil.getRandomNumber(), "Jasmine", "Thomas", "12/17/1969",
                    "jasmine@gmail.com", "3337891285", "nursejasmine");
        Nurse n2 = new Nurse(RandomNumberUtil.getRandomNumber(), "Mike", "Carter", "08/01/1998",
                "nike@gmail.com", "4339047659", "nurseMike");
        userDao.createUser(d1); userDao.createUser(d2); userDao.createUser(n1); userDao.createUser(n2);

        primaryStage.setTitle("Login");

        URL url = getClass().getResource("/login.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.initService(primaryStage, loginService, nurseService, userService);
        Scene loginScene = new Scene(root);
        primaryStage.setScene(loginScene);
        primaryStage.show();

    }
}

