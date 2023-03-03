package com.manuelsarante.domain;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Notas {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "titulo")
    public String titulo;

    @ColumnInfo(name = "descripcion")
    public String descripcion;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] imagen;

    public Notas() {
    }

    public Notas(String titulo, String descripcion, byte[] imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;

    }

    public Notas(int id, String titulo, String descripcion, byte[] imagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
       return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
   }
}
