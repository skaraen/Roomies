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

public class GroupAdapter extends ArrayAdapter<Group> {
    int mResource;
    Context mContext;
    TextView textHead;

    public GroupAdapter(@NonNull Context context, int resource, @NonNull List<Group> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name=getItem(position).getName();
        LayoutInflater inflater=LayoutInflater.from(mContext);
        if(convertView==null){
            convertView=inflater.inflate(mResource,parent,false);
        }
        textHead=convertView.findViewById(R.id.textHead);
        textHead.setText(name);
        return convertView;
    }
}
