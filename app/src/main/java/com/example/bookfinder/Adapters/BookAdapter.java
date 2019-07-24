package com.example.bookfinder.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookfinder.Objects.BookObject;
import com.example.bookfinder.R;
import com.example.bookfinder.WebActivity;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.CustomViewHolder> {

    private List<BookObject> dataList;
    private Activity activity;

    public BookAdapter(Activity activity, List<BookObject> dataList) {
        this.activity = activity;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        TextView tvTitle;
        TextView tvSubtitle;
        TextView tvPrice;
        TextView tvURL;
        private ImageView ivImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvTitle = mView.findViewById(R.id.tvTitle);
            tvSubtitle = mView.findViewById(R.id.tvSubtitle);
            ivImage = mView.findViewById(R.id.ivImage);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_book_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.tvTitle.setText(dataList.get(position).getTitle());
        holder.tvSubtitle.setText(dataList.get(position).getSubtitle());

        Picasso.Builder builder = new Picasso.Builder(activity);
        builder.downloader(new OkHttp3Downloader(activity));
        builder.build().load(dataList.get(position).getImage())
                .placeholder((R.drawable.defaultbook))
                .error(R.drawable.defaultbook)
                .into(holder.ivImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(activity,
                        dataList.get(position).getTitle(),
                        dataList.get(position).getSubtitle(),
                        dataList.get(position).getPrice(),
                        dataList.get(position).getImage(),
                        dataList.get(position).getUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void showDialog(final Activity activity, String title, String subtitle, String price, String image, final String url) {
        final Dialog dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvSubtitle = dialog.findViewById(R.id.tvSubtitle);
        TextView tvPrice = dialog.findViewById(R.id.tvPrice);
        ImageView ivImage = dialog.findViewById(R.id.ivImage);
        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);
        tvPrice.setText("Price: "+price);

        Picasso.Builder builder = new Picasso.Builder(activity);
        builder.downloader(new OkHttp3Downloader(activity));
        builder.build().load(image)
                .placeholder((R.drawable.defaultbook))
                .error(R.drawable.defaultbook)
                .into(ivImage);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnComprar);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("URL",url);
                activity.startActivity(intent);
            }
        });
        dialog.show();
    }
}
