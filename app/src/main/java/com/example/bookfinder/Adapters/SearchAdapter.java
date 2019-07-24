package com.example.bookfinder.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bookfinder.Objects.SearchObject;
import com.example.bookfinder.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {

    private List<SearchObject> searchList;
    private Context context;
    private SearchView searchView;

    public SearchAdapter(Context context, List<SearchObject> searchList, SearchView searchView){
        this.context = context;
        this.searchList = searchList;
        this.searchView = searchView;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        final View mView;

        TextView tvTitle;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvTitle = mView.findViewById(R.id.tvTitle);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_search_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.tvTitle.setText(searchList.get(position).getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery(searchList.get(position).getTitle(),true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

}
