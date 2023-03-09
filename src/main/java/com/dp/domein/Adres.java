package com.dp.domein;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        if (postcode != null) {
            this.postcode = postcode;
        }
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        if (huisnummer != null) {
            this.huisnummer = huisnummer;
        }
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        if (straat != null) {
            this.straat = straat;
        }
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        if (woonplaats != null) {
            this.woonplaats = woonplaats;
        }
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public void setReizigerId(int reizigerId) {
        this.reizigerId = reizigerId;
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s %s, %s, %d",
                getId(),
                getPostcode(),
                getHuisnummer(),
                getStraat(),
                getWoonplaats(),
                getReizigerId());
    }
}
