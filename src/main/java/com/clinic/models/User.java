package com.clinic.models;

public class User {

     private int userId;
     private String username;
     private String password;
     private int roleId;
     private String email;

     public User(int userId, String username, int roleId, String email) {
     this.userId = userId;
     this.username = username;
     this.roleId = roleId;
     this.email = email;
}
     
    // Getters/Setters 

    public int getUserId() {
    return userId;
}

    public void setUserId(int userId) {
    this.userId = userId;
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

    public int getRoleId() {
    return roleId; 
}

    public void setRoleId(int roleId) {
    this.roleId = roleId;
}

    public String getEmail() {
    return email;
}

    public void setEmail(String email) {
    this.email = email;
}
}