package com.dsa.chat;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dsa.contacts.AppContacts;
import com.dsa.firebasedl.MessageRDB;
import com.dsa.gt.IntentAction;
import com.dsa.localdl.MessageLDB;
import com.dsa.model.AppContact;
import com.dsa.model.AppMessage;

import java.util.List;
import java.util.Map;

/**
 * Created by amalroshand on 08/06/17.
 */

public class MessageReceiver {

    Context context;
    MessageLDB messageLDB;

    public MessageReceiver(Context context)
    {
        this.context=context;
        messageLDB=new MessageLDB(context);

    }
    public void receiveMessage(AppMessage message){
        switch (message.getMessageType())
        {
            case CONTACT_REQUEST:
                AppContact otherContact=new AppContact((Map<String, String>) message.getMessage());
                AppContacts appContacts=new AppContacts(context);
                appContacts.receiveContactRequest(otherContact);
                break;
            case TEXT_CHAT:

                break;
            default:

                break;
        }
        if(!messageLDB.exists(String.valueOf(message.getSenderMsgId()), message.getContactUid())) {
            messageLDB.insert(message);
            MessageRDB.getInstance().delete(message.getKey());

        }else{
            MessageRDB.getInstance().delete(message.getKey());
        }
    }
}
