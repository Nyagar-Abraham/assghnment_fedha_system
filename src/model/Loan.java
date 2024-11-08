package model;

public class Loan {
    private int id;
    private int memberId;
    private String loanType;
    private double amount;
    private double interestRate;
    private int repaymentPeriod; // in months
    private double monthlyRepayment;
    private int[] guarantorID;


    public Loan(int id, int memberId, String loanType, double amount, double interestRate, int repaymentPeriod) {
        this.id = id;
        this.memberId = memberId;
        this.loanType = loanType;
        this.amount = amount;
        this.interestRate = interestRate;
        this.repaymentPeriod = repaymentPeriod;
        this.monthlyRepayment = calculateMonthlyRepayment();
    }

    private double calculateMonthlyRepayment() {
        return (amount * (1 + (interestRate / 100 * repaymentPeriod))) / repaymentPeriod;
    }

    public int[] getGuarantorIDs() {
        return guarantorID;
    }

    public void setGuarantorID(int[] guarantorID) {
        this.guarantorID = guarantorID;
    }


    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getLoanType() {
        return loanType;
    }

    public double getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public double getMonthlyRepayment() {
        return monthlyRepayment;
    }
}
