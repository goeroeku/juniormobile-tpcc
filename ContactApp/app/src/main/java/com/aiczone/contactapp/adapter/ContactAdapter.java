package com.aiczone.contactapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aiczone.contactapp.R;
import com.aiczone.contactapp.model.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Contact contact = contacts.get(position);

        holder.tvName.setText(contact.name);
        holder.tvDateOfBirth.setText(contact.dateOfBirth);
        holder.tvGender.setText(contact.gender);
        holder.tvProfession.setText(contact.profession);
        holder.tvEmailPhone.setText(contact.email + " | " + contact.phone);
    }

    @Override
    public int getItemCount() {
        return (contacts != null) ? contacts.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateOfBirth, tvProfession, tvGender, tvEmailPhone;
        ImageButton bnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDateOfBirth = itemView.findViewById(R.id.tv_date_of_birth);
            tvProfession = itemView.findViewById(R.id.tv_profession);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvEmailPhone = itemView.findViewById(R.id.tv_email_phone);

            bnDelete = itemView.findViewById(R.id.bnDelete);
        }
    }
}
