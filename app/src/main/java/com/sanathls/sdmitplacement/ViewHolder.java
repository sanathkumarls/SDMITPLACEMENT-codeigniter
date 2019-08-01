package com.sanathls.sdmitplacement;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView recycler_text;

    public ViewHolder(@NonNull View itemView)
    {
        super(itemView);

        recycler_text=itemView.findViewById(R.id.recycler_text);


    }
}
