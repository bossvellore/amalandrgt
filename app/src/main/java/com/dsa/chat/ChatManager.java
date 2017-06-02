package com.dsa.chat;

import com.dsa.firebasedl.MessageRDB;
import com.dsa.model.AppMessage;

/**
 * Created by amalroshand on 02/06/17.
 */

public class ChatManager {

    public void send(AppMessage message)
    {
        MessageRDB messageRDB=MessageRDB.getInstance();
        messageRDB.send(message);
    }
}
