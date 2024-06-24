package com.example.fakestoreapi2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class FakeStoreAdapter extends RecyclerView.Adapter<FakeStoreViewHolder> {
    //on createViewHolder it is inflating(injecting) itemView .xml


    List<Product> products;

    public interface OnItemClickListener {
        void onClick(View view, int position);

    }

    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    public FakeStoreAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public FakeStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new FakeStoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FakeStoreViewHolder holder, @SuppressLint("RecyclerView") int position) {

//        holder.getItemImageView().setImage;

        new Thread(() -> {
            InputStream inputStream = null;
            try {
                inputStream = new URL(products.get(position).getImage()).openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);

            //Handler: Object that allows you to sent messages to a specific thread
            //Looper: The main Looper is the UI thread
            new Handler(Looper.getMainLooper()).post(() -> {
                holder.getItemImageView().setImageBitmap(bitmap);
            });

        }).start();



        holder.getTitleView().setText(products.get(position).getTitle());
        holder.getCategoryView().setText(products.get(position).getCategory());

        double price = products.get(position).getPrice();
        String formattedPrice = String.format("%.2f", price);
        holder.getPriceView().setText(formattedPrice + " €"); //We convert it into a String by putting the empty quotation marks
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(holder.itemView, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}
