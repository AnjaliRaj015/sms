package com.main.utils;
import com.main.model.user;

public class session {
    private static user loggedInUser;

    public static user getLoggedInUser() {
        System.out.println("Getting logged-in user: " + loggedInUser);
        return loggedInUser;
    }

    public static void setLoggedInUser(user user) {
        System.out.println("Getting logged-in user: " + loggedInUser);
        loggedInUser = user;
    }
}
