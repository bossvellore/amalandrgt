package com.dsa.firebasedl;

import com.dsa.model.AppContact;
import com.dsa.model.AppMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

/**
 * Created by amalroshand on 01/06/17.
 */

public class MessageRDB {
    private static final MessageRDB ourInstance = new MessageRDB();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth fbAuth;
    private FirebaseUser currentUser;

    public static MessageRDB getInstance() {
        return ourInstance;
    }

    private MessageRDB() {
        fbAuth=FirebaseAuth.getInstance();
        currentUser=fbAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("messages");
    }

    public DatabaseReference getReference() {
        return reference;
    }

    /**
     * save the contact to my contact list
     * @param message
     */
    public void send(AppMessage message, String messageContact)
    {
        reference.child(messageContact).push().setValue(message);
    }

    public void delete(String uid)
    {
        reference.child(uid).removeValue();
    }


    public DatabaseReference getMyInboxReference()
    {
        return reference.child(currentUser.getUid());
    }

}
