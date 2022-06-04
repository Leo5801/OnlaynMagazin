package com.example.onlaynmagazin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.onlaynmagazin.Fragments.AccountFragment;
import com.example.onlaynmagazin.Fragments.CategoryFragment;
import com.example.onlaynmagazin.Fragments.HomeFragment;
import com.example.onlaynmagazin.Fragments.OrderFragment;
import com.example.onlaynmagazin.databinding.ActivityMain3Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {
    DatabaseReference databaseReference;
    ActivityMain3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent=getIntent();
        binding.textviewusername.setText("Username");
        String uid=intent.getStringExtra("uid");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Userlarim").child(uid);


        getSupportFragmentManager().beginTransaction().replace(R.id.linerlay1,new HomeFragment()).commit();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username=snapshot.child("name").getValue()+"";
                String usersurname=snapshot.child("surname").getValue()+"";
                binding.textviewusername.setText(username+" " + usersurname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.imageviewadd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity3.this,MainActivity4.class));
        });

        binding.bottomnavigationview1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.item1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.linerlay1,new HomeFragment()).commit();
                        break;

                    case R.id.item2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.linerlay1,new CategoryFragment()).commit();
                        break;

                    case R.id.item3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.linerlay1,new OrderFragment()).commit();
                        break;

                    case R.id.item4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.linerlay1,new AccountFragment()).commit();
                        break;
                }

                return true;
            }
        });
    }
}