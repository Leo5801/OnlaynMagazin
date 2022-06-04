package com.example.onlaynmagazin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.onlaynmagazin.Adapters.ProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);



        Intent intent=getIntent();
        productArrayList=new ArrayList<>();
        String categoryname=intent.getStringExtra("categoryname");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);
        recyclerView=findViewById(R.id.recyclerview5);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product=dataSnapshot.getValue(Product.class);
                    productArrayList.add(product);
                }

                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity6.this,3));
                productAdapter=new ProductAdapter(MainActivity6.this,productArrayList);
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}