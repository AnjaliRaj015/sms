package main.java.com.sms.model;

public class user {
    protected static int id;
    protected String full_name;
    protected String username;
    protected String password;
    protected String role;
    protected String email; 
    protected String phone;
    protected String address;

    // Getters and Setters
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        user.id = id;
    }
    public String getName() {
        return full_name;
    }

    public void setName(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return this.full_name;
    }
}
