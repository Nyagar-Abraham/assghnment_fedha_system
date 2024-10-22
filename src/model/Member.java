package model;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private double registrationFee;
    private double totalShares;

    public Member(int id, String firstName, String lastName, double registrationFee, double totalShares) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationFee = registrationFee;
        this.totalShares = totalShares;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public double getTotalShares() {
        return totalShares;
    }
}
