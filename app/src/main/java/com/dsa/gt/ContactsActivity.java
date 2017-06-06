package com.dsa.gt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dsa.chat.ChatType;
import com.dsa.contacts.AppContacts;
import com.dsa.model.AppContact;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    AppContacts appContacts;
    ContactsItemAdapter contactsItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactAddActivityIntent = new Intent(getApplicationContext(), ContactsAddActivity.class);
                startActivity(contactAddActivityIntent);
            }
        });

        appContacts=new AppContacts(this);
        contactsItemAdapter=new ContactsItemAdapter(this, appContacts.getMyContacts());
        ListView contactsListView = (ListView)findViewById(R.id.contactsListView);
        contactsListView.setAdapter(contactsItemAdapter);

        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppContact contact=appContacts.getMyContacts().get(position);
                Intent messageIntent=new Intent(getApplicationContext(), MessageActivity.class);
                messageIntent.putExtra(IntentExtra.MSG_TYPE, ChatType.CONTACT.toString());
                messageIntent.putExtra(IntentExtra.MSG_TO_UID, contact.getUid());
                messageIntent.putExtra(IntentExtra.MSG_TO_NAME, contact.getDisplayName());
                startActivity(messageIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        contactsItemAdapter.setItems(appContacts.getMyContacts());
        contactsItemAdapter.notifyDataSetChanged();
    }
}
