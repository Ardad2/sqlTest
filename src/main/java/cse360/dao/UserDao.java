package cse360.dao;
import cse360.model.Patient;
import cse360.model.Visit;
import cse360.util.MySQLConnectionUtil;
import cse360.model.Doctor;
import cse360.model.User;
import cse360.util.RandomNumberUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {


    public List<User> getDocNurse() throws SQLException, ClassNotFoundException {
        List<User>  userList = new ArrayList<>();
        final String query = "Select * from user WHERE user_type in ('1','2')";
        Connection connection = MySQLConnectionUtil.getConnection();
        System.out.println("connection "+connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println(resultSet);
        //connection.close();
        while (resultSet.next()){
            System.out.println("Name " + resultSet.getString("first_name"));
            User user = new User(resultSet.getString("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("dob"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_no"),
                    resultSet.getString("password"));
                user.setType(resultSet.getString("user_type"));
            userList.add(user);
        }
        return userList;
    }

    public User getUserByEmailId(String email) throws SQLException, ClassNotFoundException {
        final String query = "Select * from USER WHERE email ='"+email+"'";
        Connection connection = MySQLConnectionUtil.getConnection();
        System.out.println("connection "+connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println(resultSet);
        //connection.close();
        User user =null;
       if(resultSet.next()){
            System.out.println("Name " + resultSet.getString("first_name"));
             user = new User(resultSet.getString("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("dob"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_no"),
                    resultSet.getString("password"));
             user.setType(resultSet.getString("user_type"));
        }
        return user;
    }

    public boolean checkForUserByType(String type, String email) throws SQLException, ClassNotFoundException {
        final String query = "Select * from USER WHERE email ='"+email+"' AND user_type ='"+type+"'";
        Connection connection = MySQLConnectionUtil.getConnection();
        System.out.println("connection "+connection);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        System.out.println(resultSet);
        //connection.close();
        User user =null;
        if(resultSet.next()){
          return true;
        }
        return false;
    }


    public void createUser(User user) throws SQLException, ClassNotFoundException {

        Connection connection = MySQLConnectionUtil.getConnection();
        String insertQuery = "INSERT INTO USER (id,first_name,last_name,dob,email,phone_no,password, user_type) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareCall(insertQuery);
        preparedStatement.setString(1,user.getId());
        preparedStatement.setString(2,user.getFirstName());
        preparedStatement.setString(3,user.getLastName());
        preparedStatement.setString(4, user.getDOB());
        preparedStatement.setString(5,user.getEmail());
        preparedStatement.setString(6,user.getPhoneNumber());
        preparedStatement.setString(7,user.getPassword());
        preparedStatement.setString(8, user.getType());

        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Created success fully");
        }
    }

    public void updateUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String insertQuery = "UPDATE user set password = ?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareCall(insertQuery);
        preparedStatement.setString(1, user.getPassword());
        preparedStatement.setString(2, user.getId());


        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Updated successfully");
        }
    }

    public void editPatient(String id, String insurance, String pharmacy, String phoneNumber) throws SQLException, ClassNotFoundException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String updateUserQuery = "UPDATE user SET phone_no=?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareCall(updateUserQuery);
        preparedStatement.setString(1, phoneNumber);
        preparedStatement.setString(2, id);


        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("User Updated successfully");
        }

        String updatePatientQuery = "UPDATE patient SET insurance=?, pharmacy=? WHERE user_id = ?";
        preparedStatement = connection.prepareCall(updatePatientQuery);
        preparedStatement.setString(1, insurance);
        preparedStatement.setString(2, pharmacy);
        preparedStatement.setString(3, id);

        update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Patient Updated successfully");
        }


    }


    public Doctor searchDoctor(String first_name, String last_name) throws SQLException, ClassNotFoundException {
        final String query = "SELECT * from USER WHERE first_name='"+first_name+"' AND last_name='"+last_name+
                "' AND user_type='"+1 +"'";
        Connection connection = MySQLConnectionUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Doctor foundDoctor = null;
        while(resultSet.next()){
            foundDoctor = new Doctor(resultSet.getString("id"),resultSet.getString("first_name"),
                    resultSet.getString("last_name"),resultSet.getString("dob"), resultSet.getString("email"),
                    resultSet.getString("phone_no"),resultSet.getString("password"));
            foundDoctor.setType(resultSet.getString("user_type"));

        }
        return foundDoctor;
    }

    public Doctor searchDoctor(String id) throws SQLException, ClassNotFoundException {
        final String query = "SELECT * from USER WHERE id='"+id+"' AND user_type= '1'";
        Connection connection = MySQLConnectionUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Doctor foundDoctor = null;
        while(resultSet.next()){
            foundDoctor = new Doctor(resultSet.getString("id"),resultSet.getString("first_name"),
                    resultSet.getString("last_name"),resultSet.getString("dob"), resultSet.getString("email"),
                    resultSet.getString("phone_no"),resultSet.getString("password"));
            foundDoctor.setType(resultSet.getString("user_type"));

        }
        return foundDoctor;
    }

    public List<String> getImmunizations(String id) throws SQLException {
        final String query = "SELECT * from immunization WHERE user_id='"+id+"'";
        Connection connection = MySQLConnectionUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<String> immunizationList = new ArrayList<>();
        String add = "";
        while(resultSet.next()){
           add = "Date: "+resultSet.getString("date") + "\t\tVaccine: "+resultSet.getString("vaccine");
           immunizationList.add(add);
        }
        return immunizationList;
    }

    public void editUser(String id, String phoneNo) throws SQLException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String updateUserQuery = "UPDATE user SET phone_no=?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareCall(updateUserQuery);
        preparedStatement.setString(1, phoneNo);
        preparedStatement.setString(2, id);

        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("User Updated successfully");
        }

    }

    public List<Patient> getDoctorsPatientList(String doctorId, String dob) throws SQLException, ClassNotFoundException {
        List<Patient>  patientList = new ArrayList<>();
        final String query = "Select * from Patient p, User u WHERE u.user_type='3' AND u.dob='"+dob+"' AND " +
                "p.doctor_id='"+doctorId+"' AND p.user_id=u.id";
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

    public void editVisit(Visit visit) throws SQLException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String updateUserQuery = "UPDATE visit SET height=?, weight=?, blood_pressure=?," +
                "body_temp=?, diagnostic=?, medications=?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareCall(updateUserQuery);
        preparedStatement.setString(1, visit.getHeight());
        preparedStatement.setString(2, visit.getWeight());
        preparedStatement.setString(3, visit.getBP());
        preparedStatement.setString(4, visit.getBodyTemp());
        preparedStatement.setString(5, visit.getDiagnostic());
        preparedStatement.setString(6, visit.getMedication());
        preparedStatement.setString(7, visit.getId());

        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Visit Updated successfully");
        }
    }

    public Patient searchPatient(String email) throws SQLException, ClassNotFoundException {
        final String query = "SELECT * from user u, patient p WHERE " +
                "u.email='" + email + "' AND u.id=p.user_id" +
                " AND user_type='"+3+"'";
        Connection connection = MySQLConnectionUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        Patient foundPatient = null;
        while(resultSet.next()){
            foundPatient = new Patient(resultSet.getString("u.id"),resultSet.getString("u.first_name"),
                    resultSet.getString("u.last_name"),resultSet.getString("u.dob"),
                    resultSet.getString("u.email"),resultSet.getString("u.phone_no"),
                    resultSet.getString("u.password"), resultSet.getString("p.insurance"),
                    resultSet.getString("p.pharmacy"),resultSet.getString("p.doctor_id"));

        }
        return foundPatient;
    }

    public void addDate(String patientId, String userId, String message) throws SQLException {
        Connection connection = MySQLConnectionUtil.getConnection();
        String updateUserQuery = "INSERT INTO messages (id,patient_id,user_id,message,date) " +
                "values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareCall(updateUserQuery);
        preparedStatement.setString(1, RandomNumberUtil.getRandomNumber());
        preparedStatement.setString(2, patientId);
        preparedStatement.setString(3, userId);
        preparedStatement.setString(4, message);
        preparedStatement.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));

        long update = preparedStatement.executeLargeUpdate();
        if(update>0){
            System.out.println("Messages Updated successfully");
        }
    }

    public List<String> getMessageList(String patient_id, String user_id) throws SQLException {
        final String query = "SELECT * from messages WHERE patient_id='"+patient_id+
                "' AND user_id='"+user_id+"' ORDER BY date";
        Connection connection = MySQLConnectionUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<String> messageList = new ArrayList<>();
        String add = "";
        while(resultSet.next()){
            add = resultSet.getString("message");
            messageList.add(add);
        }
        return messageList;
    }
}
