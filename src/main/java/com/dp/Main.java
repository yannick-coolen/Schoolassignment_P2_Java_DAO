package com.dp;

import com.dp.domein.Adres;
import com.dp.persistentie.AdresDAO;
import com.dp.persistentie.AdresDAOsql;
import com.dp.domein.Reiziger;
import com.dp.persistentie.ReizigerDAO;
import com.dp.persistentie.ReizigerDAOsql;

import io.github.cdimascio.dotenv.Dotenv;

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
        Dotenv dotenv = Dotenv.load();

        String jdbcurl = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = dotenv.get("POSTGRES_SECRET");

        try {
            if (connection == null) {
                connection = DriverManager.getConnection(jdbcurl,username,password);

                System.out.println("Verbonden met de database");
                System.out.println("=======================");
                System.out.println("Connected");

                ReizigerDAOsql reizigerDAOsql = new ReizigerDAOsql(connection);
//                testReizigerDAO(reizigerDAOsql);

                AdresDAOsql adresDAOsql = new AdresDAOsql(connection);
                testAdresDAO(adresDAOsql, reizigerDAOsql);
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

    public static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        Reiziger reiziger1 = new Reiziger(
                77, "A", "", "Steen", Date.valueOf("1982-03-14"));

        // Start Create
        rdao.save(reiziger1);

        Adres adres1 = new Adres();
        adres1.setId(6);
        adres1.setPostcode("2367FG");
        adres1.setStraat("Teststraat");
        adres1.setHuisnummer("54");
        adres1.setWoonplaats("Den Haag");
        adres1.setReizigerId(reiziger1.getId());

        adao.save(adres1);
        // End Create

        // Start Read
        System.out.println("\n---------- Test AdresDAO -------------");
        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Adres a : adressen) {
            System.out.println(a);
        }

        System.out.println();
        System.out.println("[Test] ReizigerDAO.findByReiziger() geeft de volgende adressen:");
        System.out.println(adao.findByReiziger(reiziger1) + "\n");
        // End Read

        // Start Delete
        var verwijderAdres = new Adres();
        verwijderAdres.setId(6);
        adao.delete(verwijderAdres);

        var verwijderReiziger = new Reiziger();
        verwijderReiziger.setId(77);
        rdao.delete(verwijderReiziger);
        // End Delete
    }
}
