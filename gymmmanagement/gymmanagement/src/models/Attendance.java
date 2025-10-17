package models;

public class Attendance {
    private int id;
    private int memberId;
    private String checkIn;
    private String checkOut;
    private String date;
    private String memberName; // For display purposes

    public Attendance() {}

    public Attendance(int memberId, String checkIn, String checkOut, String date) {
        this.memberId = memberId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.date = date;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getCheckIn() { return checkIn; }
    public void setCheckIn(String checkIn) { this.checkIn = checkIn; }

    public String getCheckOut() { return checkOut; }
    public void setCheckOut(String checkOut) { this.checkOut = checkOut; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
}