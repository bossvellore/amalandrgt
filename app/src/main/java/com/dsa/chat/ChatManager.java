package com.dsa.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dsa.firebasedl.MessageRDB;
import com.dsa.gt.IntentAction;
import com.dsa.gt.IntentExtra;
import com.dsa.localdl.MessageLDB;
import com.dsa.model.AppMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amalroshand on 02/06/17.
 */

public class ChatManager extends BroadcastReceiver {
    Context context;
    MessageLDB messageLDB;
    List<AppMessage> messageList;
    String contactUid;
    ChatListener chatListener;
    LocalBroadcastManager localBroadcastManager;
    public ChatManager(Context context, String ContactUid)
    {
        this.context=context;
        this.contactUid = ContactUid;
        messageLDB=new MessageLDB(context);
        messageList=messageLDB.getMessagesFrom(contactUid);
        localBroadcastManager=LocalBroadcastManager.getInstance(context);
        IntentFilter messageIntentFilter = new IntentFilter(IntentAction.INCOMING_MESSAGE);
        localBroadcastManager.registerReceiver(this, messageIntentFilter);
    }
    public void send(AppMessage message)
    {
        MessageRDB messageRDB=MessageRDB.getInstance();
        messageRDB.send(message);
        messageLDB.insert(message);
        this.messageList.add(message);
        this.chatListener.onChat();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppMessage message=new AppMessage();
        message.setMsgText(intent.getStringExtra(IntentExtra.MSG_TEXT));
        message.setMailBox(MailBox.valueOf(intent.getStringExtra(IntentExtra.MSG_MAIL_BOX)));
        message.setContactUid(intent.getStringExtra(IntentExtra.MSG_FROM_UID));
        message.setTimestamp(intent.getStringExtra(IntentExtra.MSG_TIME_STAMP));
        this.messageList.add(message);
        chatListener.onChat();
    }

    public void setChatListener(ChatListener chatListener) {
        this.chatListener = chatListener;
    }

    public List<AppMessage> getMessageList() {
        return messageList;
    }
}
