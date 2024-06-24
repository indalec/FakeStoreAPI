package com.example.fakestoreapi2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FakeStoreViewHolder extends RecyclerView.ViewHolder {


    private final ImageView itemImageView;
    private final TextView titleView;
    private final TextView categoryView;
    //    private final TextView description;
    private final TextView priceView;

//    private final TextView rate;
//    private final TextView rateCount;
//    private final TextView image;


    public FakeStoreViewHolder(@NonNull View itemView) {
        super(itemView);

        itemImageView = itemView.findViewById(R.id.itemImageView);

        titleView = itemView.findViewById(R.id.title);
        categoryView = itemView.findViewById(R.id.category);
        priceView = itemView.findViewById(R.id.price);


    }

    public ImageView getItemImageView() {
        return itemImageView;
    }

    public TextView getTitleView() {
        return titleView;
    }

    public TextView getCategoryView() {
        return categoryView;
    }

    public TextView getPriceView() {
        return priceView;
    }
}
