package com.dp.persistentie;

import com.dp.domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOsql(Connection conn) {
        this.conn = conn;
    }

    // create reiziger
    public boolean save(Reiziger reiziger) {
        String query = "INSERT INTO reiziger VALUES (?, ?, ?, ?, ? )";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());
            pst.setString(2, reiziger.getNaam());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("(Toevoeging mislukt)");
        }
        return false;
    }

    // update reiziger
    public boolean update(Reiziger reiziger) {
        String query = "UPDATE reiziger " +
                "SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? " +
                "WHERE reiziger_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, reiziger.getNaam());
            pst.setString(2, reiziger.getTussenvoegsel());
            pst.setString(3, reiziger.getAchternaam());
            pst.setDate(4, reiziger.getGeboortedatum());
            pst.setInt(5, reiziger.getId());

            System.out.println(String.format("Update van ID gebruiker %d succesvol voltooid",
                    reiziger.getId()));

            pst.executeUpdate();
            pst.close();
            return true;
        } catch(SQLException e) {
            e.printStackTrace();
            System.err.println("Update mislukt");
        }
        return false;
    }

    // delete reiziger
    public boolean delete(Reiziger reiziger) {
        String query = "DELETE FROM reiziger WHERE reiziger_id = ? NOTNULL";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getId());

//            System.out.println(String.format("Reiziger met ID %d succesvol uit de DB verwijderd",
//                    reiziger.getId()));
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.printf("Verwijdering van reiziger %d mislukt",
                    reiziger.getId());
        }
        return false;
    }

    // // return all reizigers based on id
    public Reiziger findById(int id) {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        Reiziger reiziger = new Reiziger();

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                reiziger.setId(rs.getInt(1));
                reiziger.setNaam(rs.getString(2));
                reiziger.setTussenvoegsel(rs.getString(3));
                reiziger.setAchternaam(rs.getString(4));
                reiziger.setGeboortedatum(rs.getDate(5));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reiziger;
    }

    // return all reizigers based on Dob
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigerList = new ArrayList<>();
        String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
        Reiziger reiziger = new Reiziger();

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDate(1, Date.valueOf(datum));
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                reiziger.setId(rs.getInt(1));
                reiziger.setNaam(rs.getString(2));
                reiziger.setTussenvoegsel(rs.getString(3));
                reiziger.setAchternaam(rs.getString(4));
                reiziger.setGeboortedatum(rs.getDate(5));

                reizigerList.add(reiziger);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reizigerList;
    }

    // return all reizigers
    public List<Reiziger> findAll() {
        List<Reiziger> reizigerList = new ArrayList<>();
        String query = "SELECT * FROM reiziger";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(rs.getInt(1));
                reiziger.setNaam(rs.getString(2));
                reiziger.setTussenvoegsel(rs.getString(3));
                reiziger.setAchternaam(rs.getString(4));
                reiziger.setGeboortedatum(rs.getDate(5));

                reizigerList.add(reiziger);
            }
            rs.close();
            pst.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return reizigerList;
    }
}
