package com.dp.persistentie;

import com.dp.domein.Adres;
import com.dp.domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO {
    private Connection conn;

    public AdresDAOsql(Connection conn) {
        this.conn = conn;
    }

    public boolean save(Adres adres) {
        String query = "INSERT INTO adres VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pst = conn.prepareStatement(query);

            pst.setInt(1, adres.getId());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6, adres.getReizigerId());

            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("(Toevoeging adres mislukt)");
        }
        return false;
    }

    public boolean update(Adres adres) {
        String query = "UPDATE adres " +
                "SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ?" +
                "WHERE adres_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, adres.getPostcode());
            pst.setString(2, adres.getHuisnummer());
            pst.setString(3, adres.getStraat());
            pst.setString(4, adres.getWoonplaats());
            pst.setInt(5, adres.getReizigerId());
            pst.setInt(6, adres.getId());

            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.printf("Update van adres ID %d is mislukt", adres.getId());
        }
        return false;
    }

    public boolean delete(Adres adres) {
        String query = "DELETE FROM adres WHERE adres_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, adres.getId());

            System.out.println(String.format(
                    "Adres met ID %d succesvol uit de DB verwijderd",
                    adres.getId()));
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Verwijdering van adres mislukt");
        }
        return false;
    }

    public Adres findByReiziger(Reiziger reiziger) {
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        Adres adres = new Adres();

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                adres.setId(rs.getInt(1));
                adres.setPostcode(rs.getString(2));
                adres.setHuisnummer(rs.getString(3));
                adres.setStraat(rs.getString(4));
                adres.setWoonplaats(rs.getString(5));
                adres.setReizigerId(rs.getInt(6));
            }

            System.out.println(String.format(
                    "Reiziger met ID %d succesvol uit de DB verwijderd",
                    reiziger.getId()));
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adres;
    }

    public List<Adres> findAll() {
        List<Adres> adresList = new ArrayList<>();
        String query = "SELECT * FROM adres";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Adres adres = new Adres();
                adres.setId(rs.getInt(1));
                adres.setPostcode(rs.getString(2));
                adres.setHuisnummer(rs.getString(3));
                adres.setStraat(rs.getString(4));
                adres.setWoonplaats(rs.getString(5));
                adres.setReizigerId(rs.getInt(6));

                adresList.add(adres);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adresList;
    }
}
