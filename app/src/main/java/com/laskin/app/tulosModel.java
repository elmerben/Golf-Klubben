package com.laskin.app;

public class tulosModel {

    private int vayla1, vayla2, vayla3;
    private int tulosPariin, lyonnitYht;

    private int kierrosID;
    private DataBaseHelper dataBaseHelper;

    public tulosModel(int vayla1, int vayla2, int vayla3, int tulosPariin, int lyonnitYht, int kierrosID) {
        this.vayla1 = vayla1;
        this.vayla2 = vayla2;
        this.vayla3 = vayla3;
        this.tulosPariin = tulosPariin;
        this.lyonnitYht = lyonnitYht;
        this.kierrosID = kierrosID;
       // this.dataBaseHelper = dataBaseHelper;
    }


    @Override
    public String toString() {
        return "tulosModel{" +
                "vayla1=" + vayla1 +
                ", vayla2=" + vayla2 +
                ", vayla3=" + vayla3 +
                ", tulosPariin=" + tulosPariin +
                ", lyonnitYht=" + lyonnitYht +
                ", kierrosID=" + kierrosID +
                '}';
    }

    public tulosModel() {
    }

    public int getVayla1() {
        return vayla1;
    }

    public void setVayla1(int vayla1) {
        this.vayla1 = vayla1;
    }

    public int getVayla2() {
        return vayla2;
    }

    public void setVayla2(int vayla2) {
        this.vayla2 = vayla2;
    }

    public int getVayla3() {
        return vayla3;
    }

    public void setVayla3(int vayla3) {
        this.vayla3 = vayla3;
    }

    public int getTulosPariin() {
        return tulosPariin;
    }

    public void setTulosPariin(int tulosPariin) {
        this.tulosPariin = tulosPariin;
    }

    public int getLyonnitYht() {
        return lyonnitYht;
    }

    public void setLyonnitYht(int lyonnitYht) {
        this.lyonnitYht = lyonnitYht;
    }

    public int getKierrosID() {
        return kierrosID;
    }

    public void setKierrosID(int kierrosID) {
        this.kierrosID = kierrosID;
    }





}
