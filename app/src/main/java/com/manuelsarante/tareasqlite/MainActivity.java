package com.manuelsarante.tareasqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.manuelsarante.dao.NotasDao;
import com.manuelsarante.database.AppDatabase;
import com.manuelsarante.domain.Notas;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button agregar;
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
            byte[] imageData;
            //Busca una vista para popular los datos
            TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
            TextView desc = (TextView) convertView.findViewById(R.id.desc);
            ImageView imagen = convertView.findViewById(R.id.imagen);
            ImageButton eliminar = convertView.findViewById(R.id.borrar);
            ImageButton editar = convertView.findViewById(R.id.editar);

            //Popula los datos dentro de la plantilla
            titulo.setText(String.valueOf(notas.getTitulo()));
            desc.setText(notas.getDescripcion());
            imageData = notas.getImagen();
            if(imageData == null){
                imagen.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_error_24));
            }else{
                Bitmap imgBitmap = DataConverter.convertByteArrayToImage(imageData);
                imagen.setImageBitmap(imgBitmap);
            }

            //onclick para eliminar una nota
            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Confirmación");
                    builder.setMessage("¿Estás seguro de que quieres eliminar esta nota?");
                    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción que se ejecutará si el usuario confirma
                            Notas notaSelected = getItem(position);
                            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                            NotasDao notasDao = db.notasDao();
                            notasDao.delete(notaSelected);
                            mostrarNotas();
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Acción que se ejecutará si el usuario cancela
                        }
                    });
                    builder.show();


                }
            });

            //onclick para editar una nota
            editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Notas notaSelected = getItem(position);
                    Intent intent = new Intent(getApplicationContext(), Editar.class);
                    intent.putExtra("id", notaSelected.getId());
                    startActivity(intent);
                }
            });

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

    @Override
    protected void onResume() {
        super.onResume();
        mostrarNotas();
    }
}