package com.tony.daytwo_videodatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST = 1;
    private VideoView videoView;
    private EditText eName, eDesc;
    private Button bUpload;
    private Uri videUri;
    private DatabaseReference mRef;
    private StorageReference storageReference;
    MediaController mediaController;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        eName = findViewById(R.id.edt_name);
        eDesc = findViewById(R.id.edt_description);
        bUpload = findViewById(R.id.btn_upload);
        mRef = FirebaseDatabase.getInstance().getReference("Videos");
        storageReference = FirebaseStorage.getInstance().getReference("Videos");
        mLoadingBar = new ProgressDialog(this);

        mediaController =  new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseVideo();
            }
        });

        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadVideo();
            }
        });


    }


    private void ChooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            videUri = data.getData();
            videoView.setVideoURI(videUri);
        }
    }

    private String getFileExt(Uri videUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(videUri));
    }

    private void UploadVideo() {
        if (videUri != null){
            mLoadingBar.setTitle("Uploading Video");
            mLoadingBar.setMessage("Please Wait While Video Uploads");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            storageReference.child(System.currentTimeMillis() + "." + getFileExt(videUri)).putFile(videUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mLoadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Successful!!", Toast.LENGTH_SHORT).show();
                    String name = eName.getText().toString().trim();
                    String desc = eDesc.getText().toString();
                    String videoUrl = taskSnapshot.getUploadSessionUri().toString();
                    Member member = new Member(name, desc, videoUrl);

                    mRef.push().setValue(member);
                    eName.setText("");
                    eDesc.setText("");
                    videoView.setVideoURI(null);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to Upload!!Try Again", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(this, "No File Selected!!!", Toast.LENGTH_SHORT).show();
        }
    }

}