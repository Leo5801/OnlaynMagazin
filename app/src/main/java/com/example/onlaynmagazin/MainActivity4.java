package com.example.onlaynmagazin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.onlaynmagazin.Adapters.CategoryAdapter;
import com.example.onlaynmagazin.Adapters.CategoryAdapter2;
import com.example.onlaynmagazin.databinding.ActivityMain4Binding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {

    ActivityMain4Binding binding;
    Uri mImageUri;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    CategoryAdapter2 categoryAdapter2;
    ArrayList<Category> categoryArrayList;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryArrayList=new ArrayList<>();

        recyclerView=findViewById(R.id.recyclerview2);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Upload Image");
        progressDialog.setMessage("Please Wait...");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Categorys");
        storageReference= FirebaseStorage.getInstance().getReference().child("Categorys");

        binding.imageviewselected.setOnClickListener(view -> {
            openFileChooser();
        });

        binding.buttoncategoryadd.setOnClickListener(view -> {
            uploadimage();
        });

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Categorys");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    category=dataSnapshot.getValue(Category.class);
                    categoryArrayList.add(category);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity4.this,3));
                categoryAdapter2=new CategoryAdapter2(MainActivity4.this,categoryArrayList);
                recyclerView.setAdapter(categoryAdapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).centerCrop().fit().into(binding.imageviewselected);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime =  MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadimage(){

        if (mImageUri!=null){
            progressDialog.show();
            StorageReference filereference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            filereference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            progressDialog.hide();
                            Category category=new Category();
                            category.setImageuri(uri.toString());
                            category.setCategoryname(binding.edittextcategoryname.getText().toString());
                            String uploadkey=databaseReference.push().getKey();
                            category.setUploadkey(uploadkey);
                            databaseReference.child(uploadkey).setValue(category);

                        }
                    });
                }

            });
        }

    }
}