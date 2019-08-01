package com.sanathls.sdmitplacement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
{

    private List<Title> titles;

    public RecyclerAdapter(List<Title> titles)
    {
        this.titles=titles;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Title sampletitle=titles.get(position);

        holder.recycler_text.setText(sampletitle.title);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
