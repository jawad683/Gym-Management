package models;

public class WorkoutPlans {
    private int id;
    private String planName;
    private String duration; // e.g., 1 Month, 3 Months, 1 Year
    private int price; // in INR
    private String description;
    private String features;
    private String status; // Active, Inactive

    public WorkoutPlans() { }

    public WorkoutPlans(String planName, String duration, int price) {
        this.planName = planName;
        this.duration = duration;
        this.price = price;
        this.status = "Active";
        setDefaultDetails();
    }

    public WorkoutPlans(String planName, String duration, int price, String description, String features) {
        this.planName = planName;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.features = features;
        this.status = "Active";
    }

    private void setDefaultDetails() {
        switch (this.duration.toLowerCase()) {
            case "1 month":
            case "monthly":
                this.description = "1 Month Gym Membership";
                this.features = "Gym Access, Basic Equipment, Locker Room";
                break;
            case "3 months":
            case "quarterly":
                this.description = "3 Months Gym Membership";
                this.features = "Gym Access, All Equipment, 2 PT Sessions";
                break;
            case "1 year":
            case "yearly":
                this.description = "1 Year Gym Membership";
                this.features = "Full Gym Access, Unlimited PT, Diet Plan";
                break;
            default:
                this.description = "Custom Gym Membership";
                this.features = "Basic Gym Access";
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) {
        this.duration = duration;
        setDefaultDetails();
    }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return planName + " - " + duration + " - ₹" + price;
    }
}