package com.masbi.cobmnn;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


import java.io.IOException;
import java.nio.file.FileStore;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
//    private de.hdodenhof.circleimageview.CircleImageView circleImageView;
    EditText etnama, etalamat, ettelp, etemail;
    Uri uriImage;
    ProgressBar pUploadImage;
    String profileImageUrl;
    FirebaseAuth mAuth;

    private static final int CHOOSE_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//      //  imageView = (ImageView) findViewById(R.id.imageView3);
////        circleImageView = (CircleImageView) findViewById(R.id.imageView3);
//        pUploadImage = (ProgressBar) findViewById(R.id.pUploadImage);
//        etnama = (EditText) findViewById(R.id.editTextNama);
//        etalamat = (EditText) findViewById(R.id.editTextAlamat);
//        ettelp = (EditText) findViewById(R.id.editTextTelp);
//        etemail = (EditText) findViewById(R.id.editTextEmail);
//        mAuth = FirebaseAuth.getInstance();
////        loadUserInformation();
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pilihImage();
//            }
//        });
//    }
//
////    protected void onStart() {
////        super.onStart();
////        if (mAuth.getCurrentUser() == null) {
////            finish();
////            Intent keHome = new Intent(EditProfile.this, Login.class);
////            keHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            startActivity(keHome);
////        }
////    }
//
////    private void loadUserInformation() {
////        FirebaseUser user = mAuth.getCurrentUser();
////        String photoUrl = user.getPhotoUrl().toString();
////        String displayNama = user.getDisplayName();
////        String displayAlamat = user.getDisplayName();
////        String displayTelp = user.getDisplayName();
////        String displayEmail = user.getDisplayName();
////        if (user != null) {
////            if (user.getPhotoUrl() != null) {
//////                Glide.with(this)
//////                        .load(user.getPhotoUrl().toString())
//////                        .into(imageView);
////            } else if (user.getDisplayName() != null) {
////                etnama.setText(displayNama);
////                etalamat.setText(displayAlamat);
////                ettelp.setText(displayTelp);
////                etemail.setText(displayEmail);
////            }
////        }
////
////    }
//
//    public void btSimpanP(View view) {
//        simpanProfile();
//    }
//
//    private void simpanProfile() {
//        String displayNama = etnama.getText().toString();
//        String displayAlamat = etalamat.getText().toString();
//        String displayTelp = ettelp.getText().toString();
//        String displayEmail = etemail.getText().toString();
//        if (displayNama.isEmpty()) {
//            etnama.setError("Nama harus diisi");
//            etnama.requestFocus();
//            return;
//        } else if (displayAlamat.isEmpty()) {
//            etalamat.setError("Alamat harus diisi");
//            etalamat.requestFocus();
//            return;
//        } else if (displayTelp.isEmpty()) {
//            ettelp.setError("No. telp harus diisi");
//            ettelp.requestFocus();
//            return;
//        } else if (displayEmail.isEmpty()) {
//            etemail.setError("Nama harus diisi");
//            etemail.requestFocus();
//            return;
//        }
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null && profileImageUrl != null) {
//            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
//                    .setDisplayName(displayNama).setDisplayName(displayAlamat).setDisplayName(displayTelp)
//                    .setDisplayName(displayEmail).build();
//            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(EditProfile.this, "Profile sudah di update", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
//    public void pilihImage() {
////        Intent pilihImg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////        startActivityForResult(pilihImg, RESULT_LOAD_IMAGE);
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(2, 2)
//                .start(EditProfile.this);
//        System.out.println("Pilih Image ");
////        pilihImg.setType("image/*");
////        pilihImg.setAction(Intent.ACTION_GET_CONTENT);
////        startActivityForResult(Intent.createChooser(pilihImg, "Pilih gambar", CHOOSE_IMAGE));
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("Request Image");
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK &&data != null && data.getData() != null && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            //uriImage = data.getData();
//            if (resultCode == RESULT_OK) {
//                uriImage = result.getUri();
////                circleImageView.setImageURI(uriImage);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//                Toast.makeText(EditProfile.this, "" + error, Toast.LENGTH_SHORT).show();
//            }
////            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImage);
////                imageView.setImageBitmap(bitmap);
////                System.out.println("Proses Upload Image");
////                uploadImage();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//    }
//
//    public void uploadImage() {
//        System.out.println("Upload Image");
//        final StorageReference profileImage = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
//        if (uriImage != null) {
//            pUploadImage.setVisibility(View.VISIBLE);
//            profileImage.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    pUploadImage.setVisibility(View.GONE);
//                    profileImageUrl = taskSnapshot.getDownloadUrl().toString();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    pUploadImage.setVisibility(View.GONE);
//                    Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
    }
}
