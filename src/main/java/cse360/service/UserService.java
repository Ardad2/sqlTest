package cse360.service;

import cse360.dao.UserDao;
import cse360.model.Doctor;
import cse360.model.Patient;
import cse360.model.User;
import cse360.model.Visit;

import javax.print.Doc;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class UserService {
    List<User> userList;

    private UserDao userDao;

    public UserService(UserDao userDao) {

        this.userDao = userDao;
    }

    // adds a new user to the database
    public void addUser(User user) throws SQLException, ClassNotFoundException {
        // get the user from the database based on the email
        User foundUser = userDao.getUserByEmailId(user.getEmail());
        if(foundUser != null){
            // the user already exists
            System.out.println("Trying to add duplicate user");
        }
        else {
            // the user doesn't exist yet
            userDao.createUser(user);
        }
    }

    // checks if the password given by the user matches the password in the database
    public boolean login(User user) throws SQLException, ClassNotFoundException {
        // get the user from the database
        User dbUser = userDao.getUserByEmailId(user.getEmail());
        // check if the database password matches the given password
        if(dbUser.getPassword().equals(user.getPassword())) {
            return true;
        }
        return false;
    }

    // returns the doctor with the specified first and last name
    public Doctor searchDoctor(String firstName, String lastName) throws SQLException, ClassNotFoundException {
       return userDao.searchDoctor(firstName, lastName);
    }
    // returns the doctor with the specified id
    public Doctor searchDoctor(String id) throws SQLException, ClassNotFoundException {
        return userDao.searchDoctor(id);
    }

    // returns the user with the specified email
    public User searchUserByEmail(String email) throws SQLException, ClassNotFoundException {
        return userDao.getUserByEmailId(email);
    }
    // returns a list of immunizations for the patient with the specified id
    public List<String> getImmunization(String id) throws SQLException {
        return userDao.getImmunizations(id);
    }

    // returns the list of patients with the given dob that belong to the doctor with the given id
    public List<Patient>  getDoctorsPatientList(String doctorId, String dob) throws SQLException, ClassNotFoundException {
       return userDao.getDoctorsPatientList(doctorId, dob);
    }

    // updates the given visit in the SQL file
    public void editVisit(Visit visit) throws SQLException {
        userDao.editVisit(visit);
    }

    // returns the patient with the given email
    public Patient getPatientByEmail(String email) throws SQLException, ClassNotFoundException {
        return userDao.searchPatient(email);
    }

    // adds a message to the SQL file
    public void addDate(String patientId, String userId, String message) throws SQLException {
        userDao.addDate(patientId, userId, message);
    }

    // returns a list of messages between the specified patient and user (doctor or nurse)
    public List<String> getMessages(String patientId, String userId) throws SQLException {
        return userDao.getMessageList(patientId, userId);
    }

    // returns a list of all doctors and nurses in the system
    public List<User> getDocNurseList() throws SQLException, ClassNotFoundException {
        return userDao.getDocNurse();
    }

}
