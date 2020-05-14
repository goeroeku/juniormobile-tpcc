package com.aiczone.samapp.adapter;

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

import com.aiczone.samapp.FormActivity;
import com.aiczone.samapp.R;
import com.aiczone.samapp.db.AppDatabase;
import com.aiczone.samapp.model.Asset;
import com.aiczone.samapp.utils.Helper;

import java.util.List;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.ViewHolder> {

    private Context context;
    private List<Asset> assets;

    public AssetAdapter(Context context, List<Asset> assets) {
        this.context = context;
        this.assets = assets;
    }


    private void delete(Asset asset, int position) {
        AppDatabase.getInstance(context).assetDao().delete(asset);

        assets.remove(asset);
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
        final Asset asset = assets.get(position);

        holder.tvName.setText(asset.name + " (" + asset.code + ")");
        String formatedDate = Helper.formatDMYLong(asset.dateOfEntry);
        holder.tvDateOfEntry.setText(Helper.getString(context, R.string.lb_date_of_entry) + ": " + formatedDate);
        holder.tvLocation.setText(Helper.getString(context, R.string.lb_location) + ": " + asset.location);
        holder.tvCategory.setText(Helper.getString(context, R.string.lb_category) + ": " + asset.category);
        holder.tvQty.setText(Helper.getString(context, R.string.lb_qty) + ": " + asset.qty);

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
                                delete(asset, position);
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
                intent.putExtra("asset", asset);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (assets != null) ? assets.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateOfEntry, tvCategory, tvLocation, tvQty;
        ImageButton bnDelete;
        LinearLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDateOfEntry = itemView.findViewById(R.id.tv_date_of_entry);
            tvCategory = itemView.findViewById(R.id.tv_category);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvQty = itemView.findViewById(R.id.tv_qty);

            bnDelete = itemView.findViewById(R.id.bnDelete);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
