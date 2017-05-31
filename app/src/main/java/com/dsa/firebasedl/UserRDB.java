package com.dsa.firebasedl;

import com.dsa.model.AppUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by amalroshand on 31/05/17.
 */

public class UserRDB {
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth fbAuth;
    private FirebaseUser user;
    static UserRDB instance;

    private UserRDB()
    {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
    }

    public static UserRDB getInstance()
    {
        if(instance == null)
        {
            instance = new UserRDB();
        }
        return instance;
    }
    public void save(AppUser user)
    {
        reference.child(user.getUid()).setValue(user);
    }

    public void delete(String uid)
    {
        reference.child(uid).removeValue();
    }
    public DatabaseReference getReference() {
        return reference;
    }
}
