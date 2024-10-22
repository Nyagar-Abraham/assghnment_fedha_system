package model;

public class Dividend {
    private int id;
    private int memberId;
    private double amount;

    public Dividend(int id, int memberId, double amount) {
        this.id = id;
        this.memberId = memberId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public double getAmount() {
        return amount;
    }
}
