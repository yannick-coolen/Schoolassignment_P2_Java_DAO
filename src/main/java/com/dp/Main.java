package com.dp;

import com.dp.persistentie.ReizigerDAO;
import com.dp.persistentie.ReizigerDAOsql;

import com.dp.domein.Reiziger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) {
        getConnection();
        closeConnection();
    }

    private static Connection getConnection() {
        String jdbcurl = /* LET OP!*/ "jdbc:postgresql://<naam van de host>:<naam van de port>/<naam van de DB>";
        String username = /* LET OP!*/ "<Voer de naam van de username in om in de PostgreSQL DB in te loggen>";
        String password = /* LET OP!*/ "<Voer de wachtwoord in om in de PostgreSQL DB in te loggen>";
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(jdbcurl,username,password);

                System.out.println("Connected to the database");
                System.out.println("=======================");

                ReizigerDAOsql reizigerDAOsql = new ReizigerDAOsql(connection);
                testReizigerDAO(reizigerDAOsql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");
        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        // initiate new object of Reiziger
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        // save
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        for (int i = 1; i <= 5 ; i++) {
            System.out.println("[Test] " + rdao.findById(i));
        }

        System.out.println();
        System.out.println("==== findById ====");
        System.out.println("[Test] " + rdao.findById(77));
        System.out.println();

        System.out.println("==== findByGbdatum ====");
        System.out.println("[Test] " + rdao.findByGbdatum("2002-09-17"));
        System.out.println("[Test] " + rdao.findByGbdatum("2002-10-22"));
        System.out.println("[Test] " + rdao.findByGbdatum("1998-08-11"));
        System.out.println();

        // update
        System.out.println("=== update reiziger ====");
        Reiziger updateReiziger = new Reiziger(
                77, "A", "", "Steen", Date.valueOf("1982-03-14"));
        rdao.update(updateReiziger);

        // delete (ALLEEN TOEPASSEN ALS ER EEN REIZIGER MET ID 77 OF ANDERE GELIJK ID AAN DE DB IS TOEGEVOEGD)
        System.out.println("==== verwijder reiziger ====");
        var verwijderReiziger = new Reiziger();
        verwijderReiziger.setId(77);
        rdao.delete(verwijderReiziger);
    }
}
