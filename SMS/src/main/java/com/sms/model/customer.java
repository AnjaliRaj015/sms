package main.java.com.sms.model;

public class customer extends user {
    private String address;
    private String name;
    private String email;
    private String phone;

    // Constructor to initialize all fields
    public customer(String username, String password, String name, String email, String phone, String address) {
        this.setUsername(username);
        this.setPassword(password);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and setters for all fields
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
