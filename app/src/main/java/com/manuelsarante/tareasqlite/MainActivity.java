package com.manuelsarante.tareasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manuelsarante.dao.NotasDao;
import com.manuelsarante.database.AppDatabase;
import com.manuelsarante.domain.Notas;

public class MainActivity extends AppCompatActivity {

    EditText titulo, descripcion;
    Button guardar;
    TextView res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getInstance(MainActivity.this);
        NotasDao notasDao = db.notasDao();

        titulo = findViewById(R.id.titulo);
        descripcion = findViewById(R.id.descripcion);
        guardar = findViewById(R.id.guardar);
        res = findViewById(R.id.res);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Notas nota = new Notas();
                nota.setTitulo(titulo.getText().toString());
                nota.setDescripcion(descripcion.getText().toString());
                notasDao.insertNota(nota);


                res.setText(notasDao.getNota(1).getTitulo());


            }
        });

    }
}