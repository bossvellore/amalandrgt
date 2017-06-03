package com.dsa.gt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dsa.model.AppMessage;

import java.util.List;

/**
 * Created by amalroshand on 31/05/17.
 */

public class MessageItemAdapter extends BaseAdapter {
    Activity activity;
    List<AppMessage> items;

    public MessageItemAdapter(Activity activity, List<AppMessage> items)
    {
        this.activity=activity;
        this.items=items;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView; // re-use an existing view, if one is available
        if (view == null) // otherwise create a new one
            view = activity.getLayoutInflater().inflate(R.layout.adapter_message_item, null);
        AppMessage appContact=items.get(position);
        TextView displayNameTextView = (TextView)view.findViewById(R.id.messageTextView);
        displayNameTextView.setText(appContact.getMsgText());
        return view;
    }
}
