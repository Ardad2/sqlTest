package cse360.model;

import java.util.Date;

public class User {
    
    /*This is the parent class which represents all user accounts created in the database. This class will be used for inheritance by classes
    which represent other types of users such as patient, nurse, and doctor. */
    
    //Variables used to represent the details of the user.
    
    private String id;
    private String firstName;
    private String lastName;
    private String DOB;
    private String email;
    private String phoneNumber;
    private String password;
    private String type; //"type" variable indicates whether the user account will be a patient, nurse, or a doctor.
    private int age;

    public String getType() {
        //Getter function that returns the user's type (patient, nurse or doctor) of the current user.
        
        return type; 
    }

    public void setType(String type) {
        
        //Setter functions that sets the user's type (patient, nurse or doctor) of the current user.
        
        this.type = type;
    }

    public User(String id, String firstName, String lastName, String DOB, String email, String phoneNumber, String password) {
        
        //This is a constructor for the user objects.
        
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        age = 0;
        this.type = "0";
    }

    public User (String firstName, String lastName, String DOB, String email, String phoneNumber, String password) {
        
        //This is a constructor for the user objects but without the ID and type.
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int calculateAge(String date){
        
        //This function calculates the age of the user by subtracting his/her year of birth from the current year (2021).
        
        return (2021-Integer.parseInt(date.substring(6)));
    }

    public String getId() {
        
        //Returns the value stored as the ID of the current user.
        
        return id;
    }

    public void setId(String id) {
        
        //Sets the value stored as the ID of the current user to the value passed in the parameters.
        
        this.id = id;
    }

    public String getFirstName() {
        
        //Returns the value stored as the first name of the current user.
        
        return firstName;
    }

    public void setFirstName(String firstName) {
        
         //Sets the first name of the user to the the one passed in the parameters.
        
        this.firstName = firstName;
    }

    public String getLastName() {
        
      //Returns the value stored as the last name of the current user.
        
        return lastName;
    }

    public void setLastName(String lastName) {
        
        //SEts the last name of the user to the one passed in the parameters.
        
        this.lastName = lastName;
    }

    public String getDOB() {
        
        //Returns the value stored as the date of birth of the current user.
        
        return DOB;
    }

    public void setDOB(String DOB) {
        
        //Sets the date of birth of the user to the one passed in the parameters.
        
        this.DOB = DOB;
    }

    public String getEmail() {
        
        //Returns the value stored as the email of the current user.
        
        return email;
    }

    public void setEmail(String email) {
        
        //Sets the email of the user to the one passed in the paramters.
        
        this.email = email;
    }

    public String getPhoneNumber() {
        
        //Returns the value stored as the phone number of the current user.
        
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        
        //Sets the phone number of the user to the one passed in the parameters.
        
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        
        //Returns the value of the password set by the user.
        
        return password;
    }

    public void setPassword(String password) {
        
        //Sets the password of the user to the one passed through the parameters.
        
        this.password = password;
    }

    public int getAge() {
        
        //Returns the value stored as the age of the user.
        
        return age;
    }

    public void setAge(int age) {
        
        //Set the age of the user to the one passed in the parameters.
        
        this.age = age;
    }
}
