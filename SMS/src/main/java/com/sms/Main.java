package main.java.com.sms;

import main.java.com.sms.gui.loginform;
import main.java.com.sms.db.database;

public class Main {
    public static void main(String[] args) {
        database.connect();
        new loginform();
    }
}

