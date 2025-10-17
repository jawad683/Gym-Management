package models;

public class Member {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String joinDate;
    private String membershipType;

    public Member() {}

    public Member(String name, String phone, String email, String membershipType) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.membershipType = membershipType;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
}