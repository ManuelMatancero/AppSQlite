package com.manuelsarante.tareasqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.params.MandatoryStreamCombination;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.manuelsarante.dao.NotasDao;
import com.manuelsarante.database.AppDatabase;
import com.manuelsarante.domain.Notas;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText titulo, descripcion;
    Button agregar;
    TextView res;
    ListView lv;
    NotasAdapter adapter;

    private NotasDao notasDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = AppDatabase.getInstance(MainActivity.this);
        notasDao = db.notasDao();
        lv = findViewById(R.id.lvlist);
        agregar = findViewById(R.id.btnAddNew);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Agregar.class);
                startActivity(i);
            }
        });

        mostrarNotas();


    }

    public class NotasAdapter extends ArrayAdapter<Notas> {


        public NotasAdapter(Context context, ArrayList<Notas> notas) {
            super(context, 0 , notas);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Obtiene los datos desde su posicion
            Notas notas = getItem(position);

            //Verifica si una vista existente esta siendo reutilizada, de lo contrario muestrame la vista
            if (convertView==null){

                convertView = LayoutInflater.from(getContext()).inflate(R.layout.tasks,parent,false);
            }

            //Busca una vista para popular los datos
            TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);
            ImageView imagen = convertView.findViewById(R.id.imagen);
            //Popula los datos dentro de la plantilla
            titulo.setText(String.valueOf(notas.getTitulo()));
            desc.setText(notas.getDescripcion());
            byte[] imageData = notas.getImagen();
            Bitmap imgBitmap = DataConverter.convertByteArrayToImage(imageData);
            imagen.setImageBitmap(imgBitmap);

            //devuelve la vista completa para mostrarla en pantalla
            return convertView;
        }

    }

    public void mostrarNotas(){
        List<Notas> notasList = notasDao.getAll();;
        ArrayList<Notas> miListaNotas = new ArrayList<Notas>();

        miListaNotas.addAll(notasList);

        //Construye el recurso de datos
        ArrayList<Notas> arrayofNotas = new ArrayList<Notas>();
        //Crea un adaptador para convertir el array a vista
        adapter = new NotasAdapter(MainActivity.this, arrayofNotas);
        // agrega el adaptador al listView
        lv.setAdapter(adapter);
        adapter.addAll(miListaNotas);
    }


}