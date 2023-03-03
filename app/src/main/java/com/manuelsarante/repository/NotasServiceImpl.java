package com.manuelsarante.repository;

import android.content.Context;

import androidx.room.Dao;

import com.manuelsarante.dao.NotasDao;
import com.manuelsarante.database.AppDatabase;
import com.manuelsarante.domain.Notas;
import com.manuelsarante.tareasqlite.MainActivity;

import java.util.List;

public class NotasServiceImpl implements NotasDao{

    NotasDao notasDao;

    @Override
    public void insertNota(Notas nota) {
        notasDao.insertNota(nota);
    }

    @Override
    public List<Notas> getAll() {
        return notasDao.getAll();
    }

    @Override
    public Notas getNota(int id) {
        return notasDao.getNota(id);
    }

    @Override
    public void updateNota(int id, String titulo, String descripcion, byte[] imagen) {
        notasDao.updateNota(id, titulo, descripcion, imagen);
    }

    @Override
    public void deleteNota(Notas nota) {
        notasDao.deleteNota(nota);
    }
}
