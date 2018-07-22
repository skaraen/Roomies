package com.example.karaens.roomies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    int mResource;
    Context mContext;
    TextView userName,userEmail;

    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name=getItem(position).getName();
        String email=getItem(position).getEmail();
        LayoutInflater inflater=LayoutInflater.from(mContext);
        if(convertView==null){
            convertView=inflater.inflate(mResource,parent,false);
        }
        userName=convertView.findViewById(R.id.userName);
        userEmail=convertView.findViewById(R.id.userEmail);
        userName.setText(name);
        userEmail.setText(email);
        return convertView;
    }
}
