package model;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private double registrationFee;
    private double totalShares;

    public Member(int id,int age, String firstName, String lastName, double registrationFee, double totalShares) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationFee = registrationFee;
        this.totalShares = totalShares;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
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
