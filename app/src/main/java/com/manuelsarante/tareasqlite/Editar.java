package com.manuelsarante.tareasqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.CaseMap;
import android.net.Uri;
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

import java.io.IOException;
import java.io.InputStream;

public class Editar extends AppCompatActivity {

    Button guardar, cancelar;
    EditText titulo, desc;
    ImageButton addImage;
    ImageView imagen;
    Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        titulo = findViewById(R.id.titulo);
        desc = findViewById(R.id.descripcion);
        addImage = findViewById(R.id.addImagen);
        imagen = findViewById(R.id.imagen);
        guardar = findViewById(R.id.guardar);
        cancelar = findViewById(R.id.cancelar);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        NotasDao notasDao = db.notasDao();

        Notas notaSelected = notasDao.getById(id);

        titulo.setText(notaSelected.getTitulo());
        desc.setText(notaSelected.getDescripcion());
        if(notaSelected.getImagen()==null){
            imagen.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_error_24));
        }else{
            imagen.setImageBitmap(DataConverter.convertByteArrayToImage(notaSelected.getImagen()));
        }

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i , 3);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(titulo.getText().toString().trim().equalsIgnoreCase("")){
                    titulo.setError("Este campo no puede estar vacio");
                }else if(desc.getText().toString().trim().equalsIgnoreCase("")){
                    desc.setError("Este campo no puede estar vacio");
                }else if(imagen.getDrawable() == null){
                    Toast.makeText(getApplicationContext(),
                            "Debe seleccionar una imagen",
                            Toast.LENGTH_SHORT).show();

                }else{
                    //Obtengo el titulo
                    String ttl = titulo.getText().toString();

                    //Obtengo la Descripcion
                    String des = desc.getText().toString();

                    //Creo el Objeto Notas y paso por parametro los valores
                    //Creo el Objeto Notas y paso por parametro los valores
                    Notas nota = new Notas();
                    nota.setId(id);
                    nota.setTitulo(ttl);
                    nota.setDescripcion(des);
                    Drawable drawable = imagen.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    nota.setImagen(DataConverter.convertImageToByteArray(bitmap));


                    //Actualizo el registro en la base de datos
                    notasDao.update(nota);

                    finish();

                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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