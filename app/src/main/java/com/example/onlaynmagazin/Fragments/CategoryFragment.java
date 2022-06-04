package com.example.onlaynmagazin.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.onlaynmagazin.Adapters.CategoryAdapter;
import com.example.onlaynmagazin.Category;
import com.example.onlaynmagazin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<Category> categoryArrayList;
    ArrayList<String> namecategoryArrayList;
    Category category;
    CategoryAdapter categoryAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView=view.findViewById(R.id.recyclerview1);
        categoryArrayList=new ArrayList<>();
        namecategoryArrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Categorys");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    category=dataSnapshot.getValue(Category.class);
                    categoryArrayList.add(category);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                categoryAdapter=new CategoryAdapter(getContext(),categoryArrayList);
                recyclerView.setAdapter(categoryAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}