package com.example.rifqimahfuzh.rifqimahfuzh_1202150019_modul6.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Umam on 3/30/2018.
 */

@IgnoreExtraProperties
public class User {

    public String username;

    public String email;



    public User() {

        // Default constructor required for calls to DataSnapshot.getValue(User.class)

    }



    public User(String username, String email) {

        this.username = username;

        this.email = email;

    }
}
