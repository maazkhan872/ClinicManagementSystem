/*package com.clinic.utils;
import com.clinic.models.User;

public class SessionManager {
    private static User currentUser;

    public static void setCurrentUser(User user) { currentUser = user; }
    public static User getCurrentUser() { return currentUser; }
    public static void cleanUserSession() { currentUser = null; }
}*/

package com.clinic.utils;

import com.clinic.models.User;

public class SessionManager {

    // Existing variable, controllers me already use ho raha hai
    private static User currentUser;

    // ----------------- EXISTING METHODS -----------------
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void cleanUserSession() {
        currentUser = null;
    }

    // ----------------- NEW METHOD -----------------
    // Check if any user is logged in
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Optional: logout alias (same as cleanUserSession)
    public static void logout() {
        cleanUserSession();
    }
}