package com.stedi.randomimagegenerator.sample;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.Holder> {
    private final List<Bitmap> images = new ArrayList<>();

    public void add(Bitmap bitmap) {
        images.add(bitmap);
        notifyItemInserted(images.size() - 1);
    }

    public void clear() {
        images.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.imageView.setImageBitmap(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static final class Holder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public Holder(ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }
}
