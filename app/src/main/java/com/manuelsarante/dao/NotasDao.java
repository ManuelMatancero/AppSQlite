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
    Notas getById(int id);

   @Update
   void update(Notas nota);

    @Insert
    void insert(Notas nota);

    @Delete
    void delete(Notas nota);


}
