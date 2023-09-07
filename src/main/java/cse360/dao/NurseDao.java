package cse360.dao;

import cse360.model.Patient;
import cse360.model.User;
import cse360.model.Visit;
import cse360.util.MySQLConnectionUtil;
import cse360.util.RandomNumberUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NurseDao {


    public void createPatient(Patient patient, String user_id) throws SQLException, ClassNotFoundException {

        Connection connection = MySQLConnectionUtil.getConnection();
        String insertQuery = "INSERT INTO PATIENT (id,insurance,pharmacy,user_id,doctor_id) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareCall(insertQuery);
        preparedStatement.setString(1, RandomNumberUtil.getRandomNumber());
        preparedStatement.setString(2,patient.getInsurance());
        preparedStatement.setString(3,patient.getPharmacy());
        preparedStatement.setString(4,user_id);
        preparedStatement.setString(5,patient.getDoctorId());

        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Patient Created success fully");
        }
    }

    public List<Patient> getPatientList(String dob) throws SQLException, ClassNotFoundException {
        List<Patient>  patientList = new ArrayList<>();
        final String query = "Select * from Patient p, User u WHERE u.user_type='3' AND u.dob='"+dob+"' AND " +
                "p.user_id=u.id";
        Connection connection = MySQLConnectionUtil.getConnection();
        System.out.println("connection "+connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println(resultSet);
        //connection.close();
        while (resultSet.next()){
            System.out.println("Name " + resultSet.getString("u.first_name"));
            Patient patient = new Patient(resultSet.getString("u.id"),
                    resultSet.getString("u.first_name"),
                    resultSet.getString("u.last_name"),
                    resultSet.getString("u.dob"),
                    resultSet.getString("u.email"),
                    resultSet.getString("u.phone_no"),
                    resultSet.getString("u.password"),
                    resultSet.getString("p.insurance"),
                    resultSet.getString("p.pharmacy"),
                    resultSet.getString("p.doctor_id"));
            patient.setType(resultSet.getString("u.user_type"));
            patientList.add(patient);
        }
        return patientList;
    }

    public void addVisit(Visit visit) throws SQLException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String insertQuery = "INSERT INTO visit (id,date,height,weight,blood_pressure,body_temp,comments,patient_id) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareCall(insertQuery);
        preparedStatement.setString(1, visit.getId());
        preparedStatement.setString(2, visit.getDate());
        preparedStatement.setString(3, visit.getHeight());
        preparedStatement.setString(4, visit.getWeight());
        preparedStatement.setString(5, visit.getBP());
        preparedStatement.setString(6, visit.getBodyTemp());
        preparedStatement.setString(7, visit.getComments());
        preparedStatement.setString(8, visit.getPatientId());

        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Visit Created success fully");
        }

    }

    public List<Visit> getVisitList(String id) throws SQLException {
        List<Visit>  visitList = new ArrayList<>();
        final String query = "Select * from visit where patient_id='"+id+"'";
        Connection connection = MySQLConnectionUtil.getConnection();
        System.out.println("connection "+connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println(resultSet);
        //connection.close();
        while (resultSet.next()){
            Visit visit = new Visit(resultSet.getString("id"),
                    resultSet.getString("patient_id"),
                    resultSet.getString("date"),
                    resultSet.getString("height"),
                    resultSet.getString("weight"),
                    resultSet.getString("body_temp"),
                    resultSet.getString("blood_pressure"),
                    resultSet.getString("comments"));
            visit.setDiagnostic(resultSet.getString("diagnostic"));
            visit.setMedication(resultSet.getString("medications"));
            visitList.add(visit);
        }
        return visitList;
    }

    public void addImmunization(String id, String date, String vaccine) throws SQLException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String insertQuery = "INSERT INTO immunization (id,user_id,vaccine,date) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareCall(insertQuery);
        preparedStatement.setString(1, RandomNumberUtil.getRandomNumber());
        preparedStatement.setString(2, id);
        preparedStatement.setString(3, vaccine);
        preparedStatement.setString(4, date);
        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Vaccine recorded success fully");

    }

}}
