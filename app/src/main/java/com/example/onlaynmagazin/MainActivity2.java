package com.example.onlaynmagazin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.onlaynmagazin.databinding.ActivityMain2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Userlarim");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Подажди");

        binding.buttonregister.setOnClickListener(view -> {
            progressDialog.show();
            binding.buttonregister.setEnabled(false);
            firebaseAuth.createUserWithEmailAndPassword(binding.edittext3.getText().toString(),binding.edittext4.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.hide();
                        Toast.makeText(MainActivity2.this, "Registratsiya bo'lding axir", Toast.LENGTH_SHORT).show();
                        user=new User();
                        user.setName(binding.edittext1.getText().toString());
                        user.setSurname(binding.edittext2.getText().toString());
                        user.setEmail(binding.edittext3.getText().toString());
                        user.setPassword(binding.edittext4.getText().toString());
                        user.setUseruid(firebaseAuth.getCurrentUser().getUid());
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);
                        binding.buttonregister.setEnabled(false);
                        startActivity(new Intent(MainActivity2.this,MainActivity.class));
                    }
                }
            });
        });
    }
}