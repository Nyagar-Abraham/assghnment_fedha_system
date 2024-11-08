package model;

public class Member {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private double registrationFee;
    private double totalShares;

    public Member(int id,int age, String firstName, String lastName,  String email, String password,double registrationFee, double totalShares) {
        this.id = id;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationFee = registrationFee;
        this.totalShares = totalShares;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setAge(int age) {
        this.age = age;
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
