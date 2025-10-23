package models;

public class Payment {
    private int id;
    private int memberId;
    private double amount;
    private String paymentDate;
    private String paymentType;

    public Payment() {}

    public Payment(int memberId, double amount, String paymentType) {
        this.memberId = memberId;
        this.amount = amount;
        this.paymentType = paymentType;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }
}