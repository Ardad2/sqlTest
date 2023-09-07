package cse360.model;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User{
    
    //This is a sub-class of the User class which represents the Patient type of user which has specialized functions and privileges.
    
    //These variables represent the additional details the patient class has apart from those that have been inherited from the user.
    
    private String insurance;
    private String pharmacy;
    private List<Visit> visitList;
    private String doctorId;
    private String nurseId;


    public Patient(String id, String firstName, String lastName,
                   String DOB, String email, String phoneNumber, String password, String insurance,
                   String pharmacy, String doctorId) {
        
        //This is a constructor for the patient class which creates a new patient object using the entered details.
        
        super(id, firstName, lastName, DOB, email, phoneNumber, password); //The user constructor is called for details which are inherted from it by the patient.
        this.insurance = insurance;
        this.pharmacy = pharmacy;
        this.visitList = new ArrayList<>(); //A list which stores details of the medical visits by the patient.
        this.doctorId = doctorId;
        this.setType("3"); //The user type is set to 3 to indicate it is a patient type of user.
    }

    public String getDoctorId() {
        
        //Getter function which returns the ID of the doctor assigned to the patient user.
        
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        
        //Setter function which changes the ID of the doctor assigned to the patient user.
        
        this.doctorId = doctorId;
    }

    public String getNurseId() {
        
        //Getter function which returns the ID of the nurse assigned to the patient user.
        
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        
        //Setter function which changes the ID of the nurse assigned to the patient user.
        
        this.nurseId = nurseId;
    }

    public String getInsurance() {
        
        //Getter function which returns the insurance of the patient.
        
        return insurance;
    }

    public void setInsurance(String insurance) {
        
        //Setter function which sets the insurance of the patient to a given amount.
        
        this.insurance = insurance;
    }

    public String getPharmacy() {
        
        //Getter function which returns the pharmacy of the patient.
        
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        
        //Setter function which sets the name of the pharmacy of the patient to an entered value.
        
        this.pharmacy = pharmacy;
    }

    public List<Visit> getVisitList() {
        
        //Getter function which returns the array list containing details of the visits by the patient.
        
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        
        //Replaces and adds values of the entered list onto the visit list.
        
        this.visitList = visitList;
    }
}
