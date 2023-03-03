package com.manuelsarante.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.manuelsarante.domain.Notas;
import java.util.List;

@Dao
public interface NotasDao {

    @Query("SELECT * FROM Notas")
    List<Notas> getAll();

    @Query("SELECT * FROM Notas WHERE id=:id")
    Notas getNota(int id);

    @Query("UPDATE Notas SET titulo= :titulo, descripcion= :descripcion, imagen = :imagen WHERE id=:id")
    void updateNota(int id, String titulo, String descripcion, byte[] imagen);

    @Insert
    void insertNota(Notas nota);

    @Delete
    void deleteNota(Notas nota);


}
