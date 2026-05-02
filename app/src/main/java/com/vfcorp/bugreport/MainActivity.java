package com.vfcorp.bugreport;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private ImageView imgPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imgPreview = findViewById(R.id.imgPreview);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnSend = findViewById(R.id.btnSend);
        EditText editDescription = findViewById(R.id.editDescription);


        btnCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_CODE);
            } else {
                abrirCamera();
            }
        });

        btnSend.setOnClickListener(v -> {
            if (editDescription.getText().toString().isEmpty()) {
                Toast.makeText(this, "Descreva o erro antes de enviar.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Reporte enviado com sucesso para VF CORP!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void abrirCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (Exception e) {
            Toast.makeText(this, "Erro: Aplicativo de câmera não encontrado no emulador.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgPreview.setImageBitmap(imageBitmap);
        }
    }
}