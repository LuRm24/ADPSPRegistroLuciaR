package org.example.adpspregistroluciar.classes;


import org.bson.Document;

import java.util.List;

public class User {
    private String username;
    private byte[] passwordHash;
    private String name;
    private Integer age;
    private List<String> emails;
    private Address address;

    public User(String username, byte[] passwordHash, String name, Integer age, List<String> emails, Address address) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.age = age;
        this.emails = emails;
        this.address = address;
    }




}