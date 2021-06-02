package com.raccoon.contactsapp;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private List<Contact> contacts;
    private Context context;

    public ContactsAdapter(Context context,List<Contact> contacts)
    {
        this.context=context;
        this.contacts=contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view,parent,false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView NameView,NumberView;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            NameView=itemView.findViewById(R.id.name_title);
            NumberView=itemView.findViewById(R.id.phone_nu);
        }
        public void bind(Contact contacts)
        {
            NameView.setText(contacts.getName());
            NumberView.setText(contacts.getNumber());
        }

        @Override
        public void onClick(View view) {
            int pos=this.getAbsoluteAdapterPosition();
            Contact ct=contacts.get(pos);

            Intent in=new Intent(context, ProfileActivity.class);
            in.putExtra("ContactName",ct.getName());
            in.putExtra("ContactNumber",ct.getNumber());
            in.putExtra("User_No",ContactListActivity.userNo);
            context.startActivity(in);
            ((ContactListActivity)context).finish();
        }
    }

}
