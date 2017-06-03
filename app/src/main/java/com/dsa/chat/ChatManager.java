package com.dsa.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dsa.firebasedl.MessageRDB;
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
    public ChatManager(Context context, String ContactUid)
    {
        this.context=context;
        this.contactUid = ContactUid;
        messageLDB=new MessageLDB(context);
        messageList=messageLDB.getMessagesFrom(contactUid);
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

    }

    public void setChatListener(ChatListener chatListener) {
        this.chatListener = chatListener;
    }

    public List<AppMessage> getMessageList() {
        return messageList;
    }
}
