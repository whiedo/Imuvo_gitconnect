package com.example.sco.imuvo.Model;

public class Lection {
    private long sqlID;
    private int number;
    private String language;


    public Lection(long sqlID, int number, String language) {
        this.sqlID = sqlID;
        this.number = number;
        this.language = language;
    }

    public Lection() {

    }

    public long getSqlID() {
        return sqlID;
    }

    public void setSqlID(long sqlID) {
        this.sqlID = sqlID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
