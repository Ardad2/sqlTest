package cse360.model;

import java.util.ArrayList;
import java.util.List;

//This is a sub-class of the User which represents a Nurse and has specialized functions.

public class Nurse extends User{

    //An array list which holds the list of the details of the patients managed by the current nurse user.
    private List<Patient> patientList;

    public Nurse(String id, String firstName, String lastName, String DOB, String email, String phoneNumber, String password) {
        
        //This is the constructor for creating a nurse object with the given details.
        
        super(id, firstName, lastName, DOB, email, phoneNumber, password); //Call the constructor of user for details inherited from user.
        this.patientList = new ArrayList<>(); //Create the patient list with details of patients managed.
        this.setType("2"); //Set the type of the user to 2 to indicate its a nurse tpe.
    }
}
