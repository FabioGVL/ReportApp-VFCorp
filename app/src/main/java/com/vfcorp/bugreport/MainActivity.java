package com.vfcorp.bugreport;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    public static ArrayList<Report> reportsEnviados = new ArrayList<>();

    private EditText editDescription;
    private ImageView imgPreview;
    private boolean imagemCapturada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editDescription = findViewById(R.id.editDescription);
        imgPreview = findViewById(R.id.imgPreview);
        Button btnSend = findViewById(R.id.btnSend);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnPensarQA = findViewById(R.id.btnPensarQA);

        btnCamera.setOnClickListener(v -> abrirCamera());

        btnSend.setOnClickListener(v -> {
            String desc = editDescription.getText().toString();
            if (desc.isEmpty() || !imagemCapturada) {
                Toast.makeText(this, "Descreva o problema e tire uma foto.", Toast.LENGTH_LONG).show();
                return;
            }

            reportsEnviados.add(new Report(desc));

            new AlertDialog.Builder(this)
                    .setTitle("Recebemos seu report")
                    .setMessage("Seu feedback foi recebido e encaminhado para o setor técnico da Pensar Consultoria, obrigado!")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("Entendido", (d, w) -> limparCampos())
                    .show();
        });

        btnPensarQA.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Verificando permissão para acesso da área técnica...");
            dialog.setCancelable(false);
            dialog.show();

            new Handler().postDelayed(() -> {
                dialog.dismiss();
                startActivity(new Intent(this, DashboardActivity.class));
            }, 4000);
        });
    }

    private void abrirCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            imgPreview.setImageBitmap((Bitmap) data.getExtras().get("data"));
            imagemCapturada = true;
        }
    }

    private void limparCampos() {
        editDescription.setText("");
        imgPreview.setImageBitmap(null);
        imagemCapturada = false;
    }
}