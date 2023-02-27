package com.hu.dp.domein;

import java.sql.Date;

public class Reiziger {
    private int id;
    private String voorletters, tussenvoegsel, achternaam;
    private Date geboortedatum;

    public Reiziger() { }

    public Reiziger(
            int id,
            String voorletters,
            String tussenvoegsel,
            String achternaam,
            Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaam() {
        return voorletters;
    }

    public void setNaam(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        return String.format("%d: %s.%s %s, ",
                getId(),
                getNaam(),
                getTussenvoegsel() == null || getTussenvoegsel().equals("")
                        ? "" : " " + getTussenvoegsel(),
                getAchternaam()) + getGeboortedatum();
    }
}
