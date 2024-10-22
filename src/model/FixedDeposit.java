package model;

public class FixedDeposit {
    private int id;
    private int memberId;
    private double depositAmount;
    private double interestRate = 0.6; // Fixed interest rate

    public FixedDeposit(int id, int memberId, double depositAmount) {
        this.id = id;
        this.memberId = memberId;
        this.depositAmount = depositAmount;
    }

    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
