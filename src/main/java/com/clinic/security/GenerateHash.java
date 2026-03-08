package com.clinic.security;

public class GenerateHash {
    public static void main(String[] args) {
        String plain = "doc123"; 
        String hash = PasswordUtil.hashPassword(plain);
        System.out.println("Hashed password: " + hash);
    }
}
