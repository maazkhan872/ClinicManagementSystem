package com.clinic.utils;

import com.clinic.models.User;

public class SessionManager {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void cleanUserSession() {
        currentUser = null;
    }

    // Check if any user is logged in
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        cleanUserSession();
    }
}