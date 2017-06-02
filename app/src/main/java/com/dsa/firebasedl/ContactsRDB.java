package com.dsa.firebasedl;

import com.dsa.model.AppContact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by amalroshand on 01/06/17.
 */

public class ContactsRDB {
    private static final ContactsRDB ourInstance = new ContactsRDB();

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth fbAuth;
    private FirebaseUser currentUser;

    public static ContactsRDB getInstance() {
        return ourInstance;
    }

    private ContactsRDB() {
        fbAuth=FirebaseAuth.getInstance();
        currentUser=fbAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("contacts").child(currentUser.getUid());
    }

    public DatabaseReference getReference() {
        return reference;
    }

    /**
     * save the contact to my contact list
     * @param contact
     */
    public void save(AppContact contact)
    {
        reference.child(contact.getUid()).setValue(contact);
    }

    public void delete(String uid)
    {
        reference.child(uid).removeValue();
    }

    /**
     * save my contact to other users contacts list.
     * @param myContact
     * @param otherUid
     */
    public void saveOtherContact(AppContact myContact, String otherUid)
    {
        DatabaseReference othersContactReference = database.getReference("contacts").child(otherUid);
        othersContactReference.child(myContact.getUid()).setValue(myContact);

    }
}
