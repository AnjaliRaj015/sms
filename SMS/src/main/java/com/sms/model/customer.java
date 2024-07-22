package main.java.com.sms.model;

public class customer extends user {
    private String address;

    public customer(String username, String password, String address) {
        this.setUsername(username);
        this.setPassword(password);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
