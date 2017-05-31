package com.dsa.contacts;

import com.dsa.firebasedl.UserRDB;
import com.dsa.model.AppUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amalroshand on 31/05/17.
 */

public class AppContacts {
    List<AppUser> myContacts;
    List<AppUser> contactsSearchResults;
    AppContactsListener appContactsListener;
    UserRDB userRDB;

    public AppContacts()
    {
        contactsSearchResults=new ArrayList<AppUser>();
        userRDB=UserRDB.getInstance();
    }

    public void setAppContactsListener(AppContactsListener appContactsListener) {
        this.appContactsListener = appContactsListener;
    }

    public List<AppUser> getMyContacts() {
        return myContacts;
    }

    public List<AppUser> getContactsSearchResults() {
        return contactsSearchResults;
    }

    public void search(String searchText)
    {
        Query searchQuery=userRDB.getReference().orderByChild("displayName").startAt(searchText).limitToFirst(50);
        searchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactsSearchResults.clear();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    contactsSearchResults.add(userSnapshot.getValue(AppUser.class));
                }
                appContactsListener.onAppContactsSearchResult();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
