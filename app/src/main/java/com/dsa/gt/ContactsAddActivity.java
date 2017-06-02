package com.dsa.gt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.dsa.contacts.AppContacts;
import com.dsa.contacts.AppContactsListener;
import com.dsa.model.AppContact;

import java.util.List;

public class ContactsAddActivity extends AppCompatActivity implements AppContactsListener{
    AppContacts appContacts;
    ListView contactsSearchResultListView;
    ContactsSearchItemAdapter contactsSearchItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_add);
        appContacts=new AppContacts(this);
        appContacts.setAppContactsListener(this);

        contactsSearchResultListView = (ListView) findViewById(R.id.contactsSearchResultListView);
        contactsSearchItemAdapter = new ContactsSearchItemAdapter(this, appContacts.getContactsSearchResults());
        contactsSearchResultListView.setAdapter(contactsSearchItemAdapter);
        contactsSearchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppContact contact=appContacts.getContactsSearchResults().get(position);
                appContacts.sendContactRequest(contact);
            }
        });

        SearchView searchView = (SearchView)findViewById(R.id.contactsSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //search(query);
                appContacts.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //search(newText);
                return false;
            }
        });
    }

    @Override
    public void onAppContactsSearchResult() {
        contactsSearchItemAdapter.notifyDataSetChanged();
        System.out.print("on result called..");
    }
}
