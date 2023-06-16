package com.example.insectmvp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.insectmvp.R;
import com.example.insectmvp.contract.InsectContract;

import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ImageViewHolder> {
    private static List<Integer> imageList ;

    private InsectContract.ViewHolderClickPresenter viewHolderClickPresenter;



    public GridAdapter( InsectContract.ViewHolderClickPresenter viewHolderClickPresenter) {

        this.viewHolderClickPresenter = viewHolderClickPresenter;
    }


    public void setImageList(List<Integer> imageList) {

        this.imageList = imageList;

    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_card, parent, false);

        return new ImageViewHolder(view,viewHolderClickPresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int imageRes = imageList.get(position);

        holder.onBind(imageRes);


    }

    @Override
    public int getItemCount() {

        return imageList.size();

    }

    static class ImageViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private CardView cardView;
        private ImageView imageView;
        private InsectContract.ViewHolderClickPresenter viewHolderClickPresenter;

        ImageViewHolder(@NonNull View itemView, InsectContract.ViewHolderClickPresenter viewHolderClickPresenter) {

            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.card_image);
       this.viewHolderClickPresenter=viewHolderClickPresenter;
            cardView.setOnClickListener(this);

        }


        void onBind(int imageRes) {
            int adapterPosition = getAdapterPosition();
            imageView.setTransitionName(String.valueOf(imageList.get(adapterPosition)));
            // 使用Glide加载图片
            Glide.with(itemView)
                    .load(imageRes)
                    .listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                            Target<Drawable> target, boolean isFirstResource) {
                    viewHolderClickPresenter.onLoadCompleted(imageView, adapterPosition);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                        target, DataSource dataSource, boolean isFirstResource) {
                    viewHolderClickPresenter.onLoadCompleted(imageView, adapterPosition);
                    return false;
                }
            }).into(imageView);

            // Set the string value of the image resource as the unique transition name for the view.
            imageView.setTransitionName(String.valueOf(imageRes));
        }


        @Override
        public void onClick(View view) {
            viewHolderClickPresenter.onItemClicked(view, getAdapterPosition());
        }
    }
}
