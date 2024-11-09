package model;

public class Loan {
    private int id;
    private int memberId;
    private String loanType;
    private double amount;
    private double interestRate;
    private int repaymentPeriod; // in months
    private double monthlyRepayment;


    private String guarantorName;
    private int guarantorID;
    private double guaranteedAmount;



    public Loan(int id, int memberId, String loanType, double amount, double interestRate, int repaymentPeriod,int guarantorID,double guaranteedAmount) {
        this.id = id;
        this.memberId = memberId;
        this.loanType = loanType;
        this.amount = amount;
        this.interestRate = interestRate;
        this.repaymentPeriod = repaymentPeriod;
        this.guarantorID = guarantorID;
        this.guaranteedAmount = guaranteedAmount;
        this.monthlyRepayment = calculateMonthlyRepayment();
    }

    public Loan(int id, int memberId, String loanType, double amount, double interestRate, int repaymentPeriod,double monthlyRepayment) {
        this.id = id;
        this.memberId = memberId;
        this.loanType = loanType;
        this.amount = amount;
        this.interestRate = interestRate;
        this.repaymentPeriod = repaymentPeriod;
        this.monthlyRepayment = monthlyRepayment;
    }


    public Loan(int memberId, String loanType, double amount, double interestRate, int repaymentPeriod, int guarantorID, double guaranteedAmount) {

        this.memberId = memberId;
        this.loanType = loanType;
        this.amount = amount;
        this.interestRate = interestRate;
        this.repaymentPeriod = repaymentPeriod;
        this.guarantorID = guarantorID;
        this.guaranteedAmount = guaranteedAmount;
        this.monthlyRepayment = calculateMonthlyRepayment();
    }

    private double calculateMonthlyRepayment() {
        return (amount * (1 + (interestRate / 100 * repaymentPeriod))) / repaymentPeriod;
    }

    public void setGuaranteedAmount(double guaranteedAmount) {
        this.guaranteedAmount = guaranteedAmount;
    }

    public void setGuarantorID(int guarantorID) {
        this.guarantorID = guarantorID;
    }


    public String getGuarantorName() {
        return guarantorName;
    }

    public void setGuarantorName(String guarantorName) {
        this.guarantorName = guarantorName;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getGuarantorID() {
        return guarantorID;
    }

//    public void setGuarantorID(int guarantorID) {
//        this.guarantorID = guarantorID;
//    }


    public double getGuaranteedAmount() {
        return guaranteedAmount;
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
