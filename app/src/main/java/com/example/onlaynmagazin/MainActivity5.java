package com.example.onlaynmagazin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;

import com.example.onlaynmagazin.Adapters.ProductAdapter;
import com.example.onlaynmagazin.databinding.ActivityMain5Binding;
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

public class MainActivity5 extends AppCompatActivity {

    ActivityMain5Binding binding;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    StorageReference storageReference;
    Uri mImageUri;
    ProgressDialog progressDialog;
    ProductAdapter productAdapter;
    ArrayList<Product> productArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        productArrayList=new ArrayList<>();

        Intent intent=getIntent();
        String categoryname=intent.getStringExtra("categoryname");
        binding.textviewcategoryname2.setText(categoryname);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Products").child(categoryname);
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("AllProducts");
        storageReference= FirebaseStorage.getInstance().getReference().child("Products");

        binding.imageviewselectedproduct.setOnClickListener(view -> {
            openFileChooser();
        });

        binding.buttonproductadd.setOnClickListener(view -> {
            uploadimage();
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Product product=dataSnapshot.getValue(Product.class);
                    productArrayList.add(product);
                }

                productAdapter=new ProductAdapter(MainActivity5.this,productArrayList);
                binding.recyclerview3.setLayoutManager(new GridLayoutManager(MainActivity5.this,3));
                binding.recyclerview3.setAdapter(productAdapter);
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
            Picasso.get().load(mImageUri).centerCrop().fit().into(binding.imageviewselectedproduct);
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
                            Product product=new Product();
                            product.setProductimage(uri.toString());
                            product.setProductname(binding.edittextproductyname.getText().toString());
                            product.setProductdescription(binding.edittextproductyDescription.getText().toString());
                            product.setProductprice(binding.edittextproductyPrice.getText().toString());
                            String uploadkey=databaseReference.push().getKey();
                            product.setUploadkey(uploadkey);
                            databaseReference.child(uploadkey).setValue(product);

                            Product product2=new Product();
                            product2.setProductimage(uri.toString());
                            product2.setProductname(binding.edittextproductyname.getText().toString());
                            product2.setProductdescription(binding.edittextproductyDescription.getText().toString());
                            product2.setProductprice(binding.edittextproductyPrice.getText().toString());
                            String uploadkey2=databaseReference2.push().getKey();
                            product.setUploadkey(uploadkey2);
                            databaseReference2.child(uploadkey2).setValue(product2);
                        }
                    });
                }

            });
        }

    }

}