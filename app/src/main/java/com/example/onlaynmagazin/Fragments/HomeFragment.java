package com.example.onlaynmagazin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.onlaynmagazin.Adapters.AllProductAdapter;
import com.example.onlaynmagazin.Product;
import com.example.onlaynmagazin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    AllProductAdapter allProductAdapter;
    DatabaseReference databaseReference;
    ArrayList<Product> allproductArrayList;
    ArrayList<String> namecategoryarraylist;
    Spinner spinner;
    ArrayAdapter arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home2, container, false);

        recyclerView=view.findViewById(R.id.recyclerview4);
        spinner=view.findViewById(R.id.spinner1);
        namecategoryarraylist=new ArrayList<>();
        allproductArrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("AllProducts");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allproductArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    allproductArrayList.add(product);
                }

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                allProductAdapter=new AllProductAdapter(getContext(),allproductArrayList);
                recyclerView.setAdapter(allProductAdapter);

                namecategoryarraylist.clear();
                for (int i = 0; i < allproductArrayList.size(); i++) {
                    namecategoryarraylist.add(allproductArrayList.get(i).getProductname());
                }
                arrayAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,namecategoryarraylist);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}