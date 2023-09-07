package cse360.model;

public class Visit {

    private String patientId;
    private String date;
    private String height;
    private String weight;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(String bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public String getBP() {
        return BP;
    }

    public void setBP(String BP) {
        this.BP = BP;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    private String bodyTemp;

    public Visit(String id, String patientId, String date, String height, String weight, String bodyTemp, String BP, String comments) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.bodyTemp = bodyTemp;
        this.BP = BP;
        this.comments = comments;
    }

    private String BP;
    private String comments;
    private String diagnostic;
    private String medication;

}
