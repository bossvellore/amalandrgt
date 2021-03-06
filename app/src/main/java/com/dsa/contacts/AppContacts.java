package com.dsa.contacts;

import android.content.Context;

import com.dsa.chat.ChatManager;
import com.dsa.chat.MessageType;
import com.dsa.firebasedl.ContactsRDB;
import com.dsa.firebasedl.UserRDB;
import com.dsa.gt.DateHelper;
import com.dsa.localdl.ContactsLDB;
import com.dsa.model.AppContact;
import com.dsa.model.AppMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amalroshand on 31/05/17.
 */

public class AppContacts {
    List<AppContact> myContacts;
    List<AppContact> contactsSearchResults;
    AppContactsListener appContactsListener;
    UserRDB userRDB;
    Context context;
    ContactsLDB contactsLDB;

    public AppContacts(Context context)
    {
        this.context=context;
        contactsSearchResults=new ArrayList<AppContact>();
        userRDB=UserRDB.getInstance();
        contactsLDB=new ContactsLDB(context);
    }

    public void setAppContactsListener(AppContactsListener appContactsListener) {
        this.appContactsListener = appContactsListener;
    }

    public List<AppContact> getMyContacts() {
        return contactsLDB.all();
    }

    public List<AppContact> getContactsSearchResults() {
        return contactsSearchResults;
    }

    public void search(String searchText)
    {
        Query searchQuery=userRDB.getReference().orderByChild(AppContact.CHILD_DISPLAY_NAME).startAt(searchText).limitToFirst(50);
        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactsSearchResults.clear();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    contactsSearchResults.add(userSnapshot.getValue(AppContact.class));
                }
                appContactsListener.onAppContactsSearchResult();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * save the otherContact to my contact list(with status accepted),
     * and save my contact to others contact list with status pending
     * @param otherContact
     */
    public void sendContactRequest(AppContact otherContact)
    {
        ContactsRDB contactsRDB=ContactsRDB.getInstance();
        AppContact myContact=new AppContact();

        FirebaseUser me= FirebaseAuth.getInstance().getCurrentUser();
        myContact.setUid(me.getUid());
        myContact.setDisplayName(me.getDisplayName());
        myContact.setPhotoUrl(me.getPhotoUrl().toString());
        myContact.setMyStatus(AppContactStatus.PENDING);
        myContact.setOtherStatus(AppContactStatus.ACCEPTED);
        myContact.setTimeStamp(ServerValue.TIMESTAMP);
        contactsRDB.saveOtherContact(myContact, otherContact.getUid());

        otherContact.setMyStatus(AppContactStatus.ACCEPTED);
        otherContact.setOtherStatus(AppContactStatus.PENDING);
        otherContact.setTimeStamp(DateHelper.getCurrentUnixTimeStamp());
        contactsLDB.insert(otherContact);
        contactsRDB.save(otherContact);

        //Notify the other contact
        ChatManager chatManager=new ChatManager(context, otherContact.getUid());
        AppMessage contactRequestMessage=new AppMessage();
        contactRequestMessage.setMessageType(MessageType.CONTACT_REQUEST);
        contactRequestMessage.setMessage(myContact);
        chatManager.send(contactRequestMessage);
    }

    /**
     * receive otherContact request
     */
    public void receiveContactRequest(AppContact otherContact)
    {
        if(!contactsLDB.exists(otherContact.getUid())) {
            contactsLDB.insert(otherContact);
        }
    }

    public boolean exists(String uid)
    {
        return contactsLDB.exists(uid);
    }

    /**
     * Set new contact request listener
     */
    public void setNewContactRequestListener(ChildEventListener listener)
    {
        AppContact contact=contactsLDB.getLastContact();
        String startTimeStamp=null;
        if(contact != null)
            if(contact.getTimeStamp()!=null)
                startTimeStamp=contact.getTimeStamp().toString();
        ContactsRDB.getInstance().setNewContactListener(listener, startTimeStamp);
    }
}
