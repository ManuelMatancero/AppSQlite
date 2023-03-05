package com.manuelsarante.tareasqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.manuelsarante.dao.NotasDao;
import com.manuelsarante.database.AppDatabase;
import com.manuelsarante.domain.Notas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class Agregar extends AppCompatActivity {

    EditText titulo, desc;
    Button  guardar, cancel;
    ImageButton addImagen;
    ImageView imagen;
    Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        addImagen = findViewById(R.id.addImagen);
        imagen = findViewById(R.id.imagen);
        guardar = findViewById(R.id.guardar);
        cancel = findViewById(R.id.cancelar);
        titulo = findViewById(R.id.titulo);
        desc = findViewById(R.id.descripcion);
        AppDatabase db = AppDatabase.getInstance(Agregar.this);
        NotasDao notasDao = db.notasDao();

        addImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i , 3);

            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obteniendo la imagen desde el imageView


                //Obtengo el titulo
                String ttl = titulo.getText().toString();

                //Obtengo la Descripcion
                String des = desc.getText().toString();

                //Creo el Objeto Notas y paso por parametro los valores
                Notas nota = new Notas();
                nota.setTitulo(ttl);
                nota.setDescripcion(des);
                nota.setImagen(DataConverter.convertImageToByteArray(imageBitmap));

                //Agrego el objeto a la base de datos
                notasDao.insert(nota);




            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            try {
                 imageBitmap = uriToBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imagen.setImageBitmap(imageBitmap);
        }
    }

    public Bitmap uriToBitmap(Uri uri) throws IOException {
        ContentResolver contentResolver = getContentResolver();
        InputStream inputStream = contentResolver.openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }
}