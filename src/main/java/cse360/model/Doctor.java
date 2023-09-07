package cse360.model;

import java.util.ArrayList;
import java.util.List;

//This is a sub-class of the User which represents a Doctor and has specialized functions.

public class Doctor extends User{
    
    //An array list which holds the list of the details of the patients managed by the current Doctor user.
    
    private List<Patient> patientList;

    public Doctor(String id, String firstName, String lastName, String DOB, String email, String phoneNumber, String password) {
        
          //This is the constructor for creating a Doctor object with the given details.
        
        super(id, firstName, lastName, DOB, email, phoneNumber, password); //Call the constructor of user for details inherited from user.
        this.patientList = new ArrayList<>(); //Create the patient list with details of patients managed.
        this.setType("1"); //Set the type of the user to 1 to indicate its a doctor tpe.
    }
}
