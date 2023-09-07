package cse360.controllers;

import cse360.model.Patient;
import cse360.service.UserService;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;

public class ImmunizationController {

    private UserService userService;
    private List<String> records;
    public TextArea allVaccineRecords;


    public void initService(UserService userService, String id, String firstName, String lastName, String dob) throws SQLException {
        this.userService = userService;
        records = userService.getImmunization(id);

        StringBuilder result = new StringBuilder("First name: "+firstName+ "\tLast name:  "+lastName+"\tDOB: "+dob+"\n");
        for (String s:records
             ) {
            result.append(s+"\n");
        }
        allVaccineRecords.setText(result.toString());
    }
}
