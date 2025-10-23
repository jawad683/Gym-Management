package models;

public class Trainer {
    private int id;
    private String name;
    private String specialization;
    private String phone;
    private double salary;

    public Trainer() {}

    public Trainer(String name, String specialization, String phone, double salary) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.salary = salary;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}