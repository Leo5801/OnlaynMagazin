package com.example.onlaynmagazin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.onlaynmagazin.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Подажди");

        binding.buttonlogin.setOnClickListener(view -> {
            progressDialog.show();
            binding.buttonlogin.setEnabled(false);
            firebaseAuth.signInWithEmailAndPassword(binding.edittext5.getText().toString(),binding.edittext6.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()){
                      Intent intent=new Intent(MainActivity.this,MainActivity3.class);
                      String uid=firebaseAuth.getCurrentUser().getUid();
                      intent.putExtra("uid",uid);
                      startActivity(intent);
                      progressDialog.hide();
                      binding.buttonlogin.setEnabled(true);
                  }else {
                      progressDialog.hide();
                      binding.edittext5.setError("Error");
                      binding.edittext6.setError("Error");
                      binding.buttonlogin.setEnabled(true);
                  }
                }
            });
        });

        binding.buttonregister2.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this,MainActivity2.class);
            startActivity(intent);
        });
    }
}