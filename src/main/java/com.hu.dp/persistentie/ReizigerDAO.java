package com.hu.dp.persistentie;

import com.hu.dp.domein.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(String date);
    List<Reiziger> findAll();
}
