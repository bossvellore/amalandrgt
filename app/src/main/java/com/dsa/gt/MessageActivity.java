package com.dsa.gt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.dsa.chat.ChatListener;
import com.dsa.chat.ChatManager;
import com.dsa.chat.MailBox;
import com.dsa.model.AppMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MessageActivity extends AppCompatActivity implements ChatListener {

    Bundle bundle;
    ChatManager chatManager;
    String contactUid;
    String contactName;
    String chatType;
    ListView messageListView;
    MessageItemAdapter messageItemAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        bundle=getIntent().getExtras();
        contactUid = bundle.getString(IntentExtra.MSG_TO_UID);
        contactName = bundle.getString(IntentExtra.MSG_TO_NAME);
        setTitle(contactName);
        chatType = bundle.getString(IntentExtra.MSG_TYPE);
        chatManager=new ChatManager(this, contactUid);
        chatManager.setChatListener(this);

        messageItemAdapter=new MessageItemAdapter(this, chatManager.getMessageList());
        messageListView = (ListView) findViewById(R.id.messageListView);
        messageListView.setAdapter(messageItemAdapter);
        final EditText messageEditTxt=(EditText)findViewById(R.id.messageEditTxt);
        Button sendMessageBtn = (Button)findViewById(R.id.sendMessageBtn);
        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppMessage message=new AppMessage();
                message.setContactUid(contactUid);

                message.setMsgText(messageEditTxt.getText().toString());

                chatManager.send(message);
            }
        });

    }

    @Override
    public void onChat() {
        messageItemAdapter.notifyDataSetChanged();
    }
}
