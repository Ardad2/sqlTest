package cse360.service;

import cse360.dao.UserDao;
import cse360.dao.NurseDao;
import cse360.model.Patient;
import cse360.model.Visit;

import java.sql.SQLException;
import java.util.List;

public class NurseService {

    private UserDao userDao;
    private NurseDao nurseDao;

    // constructor
    public NurseService(UserDao userDao, NurseDao nurseDao){
        this.userDao = userDao;
        this.nurseDao = nurseDao;
    }
    // adds a new patient to the SQL database
    public boolean createPatient(Patient patient) throws SQLException, ClassNotFoundException {
        //search user by email id
        //if user is not null than user already exists
        String user_id;
        if(userDao.getUserByEmailId(patient.getEmail()) != null){
            System.out.println("User already exists");
            return false;
        }
        else{
            userDao.createUser(patient);
            user_id = userDao.getUserByEmailId(patient.getEmail()).getId();
            nurseDao.createPatient(patient, user_id);
            return true;
        }
    }

    // returns a list of patients based on date of birth
    public List getSpecifiedPatientList(String dob) throws SQLException, ClassNotFoundException {
        return nurseDao.getPatientList(dob);
    }

    // edits a patient's profile information and updates the SQL file
    public void editPatient(String id, String insurance, String pharmarcy, String phoneNumber) throws SQLException, ClassNotFoundException {
        userDao.editPatient(id, insurance, pharmarcy, phoneNumber);
    }

    // creates a new visit object and adds it to the SQL file
    public void createVisit(Visit visit) throws SQLException {
        nurseDao.addVisit(visit);
    }

    // returns a list of visits for the specified patient id
    public List<Visit> getVisitList(String id) throws SQLException {
        return nurseDao.getVisitList(id);
    }

    // adds the new immunization information to the SQL file
    public void addImmunization(String id, String date, String vaccine) throws SQLException {
        nurseDao.addImmunization(id, date, vaccine);
    }

    // edits the specified user's phone number and updates the SQL file
    public void editProfile(String id, String phoneNo) throws SQLException {
        userDao.editUser(id, phoneNo);
    }
}
