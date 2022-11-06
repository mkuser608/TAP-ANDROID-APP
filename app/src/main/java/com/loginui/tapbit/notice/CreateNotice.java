package com.loginui.tapbit.notice;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.loginui.tapbit.GlobalVariable;
import com.loginui.tapbit.R;
import com.loginui.tapbit.uploadhelp.SingleUploadBroadcastReceiver;
import com.loginui.tapbit.utill.PathUtil;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class CreateNotice extends AppCompatActivity implements SingleUploadBroadcastReceiver.Delegate {

    EditText noticeTitle;
    Button uploadButton;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_PDF_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    private String UPLOAD_URL = GlobalVariable.link + "createNewNotice";
    private String token;
    private static final String TAG = "AndroidUploadService";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);

        noticeTitle = findViewById(R.id.noticeTitle);
        uploadButton = findViewById(R.id.buttonUpload);
        Intent intent = getIntent();
        token = intent.getStringExtra("message_key");
        requestStoragePermission();



        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void ShowFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Title"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            System.out.println(filepath);

        }
    }

    public void selectPDF(View view) {

        ShowFileChooser();
    }


    private void uploadImage() {
        String noticeTitle1 = noticeTitle.getText().toString().trim();

        try {
            String path = PathUtil.getPath(this, filepath);
            System.out.println(path);
            String uploadId = UUID.randomUUID().toString();
            uploadReceiver.setDelegate(this);
            uploadReceiver.setUploadID(uploadId);
             new MultipartUploadRequest(this, uploadId, UPLOAD_URL)
                    .addHeader("Authorization",token)
                    .addFileToUpload(path, "file")
                    .addParameter("title", noticeTitle1)
                    .setMaxRetries(3)
                    .startUpload();




        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.getMessage(), ex);
        }



    }

    private final SingleUploadBroadcastReceiver uploadReceiver =
            new SingleUploadBroadcastReceiver();

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    @Override
    public void onProgress(int progress) {
        String temp = String.valueOf(progress);
        Toast.makeText(this, temp, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        System.out.println(uploadedBytes);
        System.out.println(totalBytes);
    }

    @Override
    public void onError(Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        if(serverResponseCode == 201){
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
            uploadButton.setText("Uploaded");
            uploadButton.setEnabled(false);
            uploadButton.setBackgroundColor(Color.GREEN);
            uploadButton.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onCancelled() {
        System.out.println("canceled");
    }
}