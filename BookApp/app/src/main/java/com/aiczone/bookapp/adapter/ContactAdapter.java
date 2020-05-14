package com.aiczone.bookapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aiczone.bookapp.FormActivity;
import com.aiczone.bookapp.R;
import com.aiczone.bookapp.db.AppDatabase;
import com.aiczone.bookapp.model.Contact;
import com.aiczone.bookapp.utils.Helper;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }


    private void delete(Contact contact, int position){
        AppDatabase.getInstance(context).contactDao().delete(contact);

        contacts.remove(contact);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, getItemCount()); // optional
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Contact contact = contacts.get(position);

        holder.tvName.setText(contact.name);
        String formatedDate = Helper.formatDMYLong(contact.dateOfBirth);
        holder.tvDateOfBirth.setText("Tgl. lahir: " + formatedDate);
        holder.tvGender.setText("Jenis kelamin: " + contact.gender);
        holder.tvProfession.setText("Pekerjaan: " + contact.profession);
        holder.tvEmailPhone.setText("CP: " + contact.email + " | " + contact.phone);

        holder.bnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Hapus data?");
                builder.setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                delete(contact, position);
                            }
                        });
                builder.setNegativeButton("Batal",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("contact",contact);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (contacts != null) ? contacts.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateOfBirth, tvProfession, tvGender, tvEmailPhone;
        ImageButton bnDelete;
        LinearLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDateOfBirth = itemView.findViewById(R.id.tv_date_of_birth);
            tvProfession = itemView.findViewById(R.id.tv_profession);
            tvGender = itemView.findViewById(R.id.tv_gender);
            tvEmailPhone = itemView.findViewById(R.id.tv_email_phone);

            bnDelete = itemView.findViewById(R.id.bnDelete);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
