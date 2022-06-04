package com.example.onlaynmagazin.Adapters;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.onlaynmagazin.Category;
import com.example.onlaynmagazin.MainActivity6;
import com.example.onlaynmagazin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>  {

    Context context;
    ArrayList<Category> categoryArrayList;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.category_layout,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        holder.textView.setText(categoryArrayList.get(position).getCategoryname());
        Glide.with(context)
                .load(categoryArrayList.get(position).getImageuri())
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(view -> {
            Intent intent=new Intent(context, MainActivity6.class);
            intent.putExtra("categoryname",categoryArrayList.get(position).getCategoryname());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder  extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;
        LinearLayout linearLayout;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressbar1);
            imageView=itemView.findViewById(R.id.imageviewcategoryimage1);
            textView=itemView.findViewById(R.id.textviewcategoryname1);
            linearLayout=itemView.findViewById(R.id.linerlay2);
        }
    }

}

