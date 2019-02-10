package com.themaskedbit.tempcontact;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.CallLog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    private List<Contact> contactsList;
    private HashMap<Integer,Integer> callTypeMap;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number, count;
        public ImageView photo, callTypeIcon;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            number = (TextView) view.findViewById(R.id.number);
            photo = (ImageView) view.findViewById(R.id.photo);
            callTypeIcon = (ImageView) view.findViewById(R.id.call_type);
        }
    }


    public ContactsAdapter(List<Contact> contactsList) {
        this.contactsList = contactsList;
        this.callTypeMap = new HashMap<Integer,Integer>();
        this.callTypeMap.put(CallLog.Calls.INCOMING_TYPE, android.R.drawable.sym_call_incoming);
        this.callTypeMap.put(CallLog.Calls.MISSED_TYPE, android.R.drawable.sym_call_missed);
        this.callTypeMap.put(CallLog.Calls.OUTGOING_TYPE, android.R.drawable.sym_call_outgoing);
        this.callTypeMap.put(CallLog.Calls.REJECTED_TYPE, android.R.drawable.sym_call_missed);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contact contact = contactsList.get(position);
        if (contact.getCount() != 1){
            if(contact.getName() == null) {
                holder.name.setText("Unknown Number (" + String.valueOf(contact.getCount()) + ")");
            }
            else {
                holder.name.setText(contact.getName() + " (" + String.valueOf(contact.getCount()) + ")");
            }
        }
        else {
            if(contact.getName() == null) {
                holder.name.setText("Unknown Number");
            }
            else {
                holder.name.setText(contact.getName());
            }
        }

        holder.number.setText(contact.getNumber());
        if(contact.getPhoto()== null){
            holder.photo.setImageResource(R.mipmap.contacts);
        }
        else
            holder.photo.setImageURI(Uri.parse(contact.getPhoto()));

        //ToDO
        holder.callTypeIcon.setImageResource(this.callTypeMap.get(contact.getType()));
//        else {
//            holder.photo.setImageBitmap(getBitmapFromURL(contact.getPhoto()));
//            Log.e("log", contact.getPhoto());
//        }
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }}

}
