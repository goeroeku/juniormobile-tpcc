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
import com.aiczone.bookapp.model.Book;
import com.aiczone.bookapp.utils.Helper;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private Context context;
    private List<Book> books;

    public BookAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }


    private void delete(Book book, int position) {
        AppDatabase.getInstance(context).bookDao().delete(book);

        books.remove(book);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, getItemCount()); // optional
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_data, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Book book = books.get(position);

        holder.tvTitle.setText(book.title);
        String formatedDate = Helper.formatDMYLong(book.dateOfIssue);
        holder.tvDateOfIssue.setText(Helper.getString(context, R.string.lb_date_of_issue) + ": " + formatedDate);
        holder.tvISBN.setText(Helper.getString(context, R.string.lb_isbn) + ": " + book.isbn);
        holder.tvCategory.setText(Helper.getString(context, R.string.lb_category) + ": " + book.category);

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
                                delete(book, position);
                            }
                        });
                builder.setNegativeButton("Batal", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("book", book);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (books != null) ? books.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDateOfIssue, tvCategory, tvISBN;
        ImageButton bnDelete;
        LinearLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDateOfIssue = itemView.findViewById(R.id.tv_date_of_issue);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvISBN = itemView.findViewById(R.id.tv_isbn);

            bnDelete = itemView.findViewById(R.id.bnDelete);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
