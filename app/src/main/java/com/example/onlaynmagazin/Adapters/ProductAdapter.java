package com.example.onlaynmagazin.Adapters;


import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.onlaynmagazin.Category;
import com.example.onlaynmagazin.MainActivity5;
import com.example.onlaynmagazin.MainActivity7;
import com.example.onlaynmagazin.Product;
import com.example.onlaynmagazin.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {

    Context context;
    ArrayList<Product> productarraylist;

    public ProductAdapter(Context context, ArrayList<Product> productarraylist) {
        this.context = context;
        this.productarraylist = productarraylist;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.product_layout,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        holder.textView.setText(productarraylist.get(position).getProductname());
        Glide.with(context)
                .load(productarraylist.get(position).getProductimage())
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

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder=new AlertDialog.Builder(context);
//                builder.setTitle(productarraylist.get(position).getProductname());
//                builder.setMessage(productarraylist.get(position).getProductdescription()+"\n"
//                +productarraylist.get(position).getProductprice());
//                builder.create().show();
                Intent intent =new Intent(context, MainActivity7.class);
                intent.putExtra("image",productarraylist.get(position).getProductimage());
                intent.putExtra("text",productarraylist.get(position).getProductname()+"\n"+productarraylist.get(position).getProductdescription()+"\n"
                +productarraylist.get(position).getProductprice());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productarraylist.size();
    }

    public class ProductViewHolder  extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;
        ProgressBar progressBar;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar=itemView.findViewById(R.id.progressbar3);
            imageView=itemView.findViewById(R.id.imageviewcategoryimage3);
            textView=itemView.findViewById(R.id.textviewcategoryname3);
            linearLayout=itemView.findViewById(R.id.linerlay3);
        }
    }

}

