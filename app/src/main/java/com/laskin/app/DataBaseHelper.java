package com.laskin.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String KIERROS_TULOS = "KIERROS_TULOS";
    public static final String COLUMN_LYONNIT_1 = "LYONNIT1";
    public static final String COLUMN_LYONNIT_2 = "LYONNIT2";
    public static final String COLUMN_LYONNIT_3 = "LYONNIT3";
    public static final String COLUMN_PARTULOS = "PARTULOS";
    public static final String COLUMN_KOKONAISLYONNIT = "KOKONAISLYONNIT";
    public static final String COLUMN_KIERROSID = "KIERROSID";

    /**
     * Luo uuden tietokanta-apurin DataBaseHelper sovellukselle.
     */
    public DataBaseHelper(@Nullable Context context) {
        super(context, "tulokset.db" , null, 1);
    }


    /**
     * Metodia kutsutaan tietokantaa käytettäessä ensimmäistä kertaa.
     * Metodi sisältää koodin uuden tietokannan luomiselle ja taulujen määrittelylle.
     * @param db SQLiteDatabase-objekti, joka mahdollistaa tietokannan käsittelyn.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " +
                KIERROS_TULOS +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LYONNIT_1 +
                " INT, " + COLUMN_LYONNIT_2 +
                " INT, " + COLUMN_LYONNIT_3 +
                " INT, " + COLUMN_PARTULOS +
                " INT, " + COLUMN_KOKONAISLYONNIT +
                " INT, " + COLUMN_KIERROSID + " INT)";
        db.execSQL(createTableStatement);



    }

    /**
     * Metodia kutsutaan, jos tietokannan versio muuttuu.
     * Tarkoitus estää aiempien käyttäjien sovellusten hajoaminen
     * tietokannan suunnittelua muuttaessa.
     * @param sqLiteDatabase - SQLiteDatabase - objekti
     * @param i - Vanhan tietokannan versio
     * @param i1 - Uuden tietokannan versio
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Lisää uuden kierroksen tietokantaan.
     * @param tulosModel - objekti, jonka sisällä golfkierroksen tiedot.
     * @return boolean - Palauttaa "True", jos lisääminen onnistui. Muuten palauttaa "False".
     */
    public boolean addOne(tulosModel tulosModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LYONNIT_1, tulosModel.getVayla1());
        cv.put(COLUMN_LYONNIT_2, tulosModel.getVayla2());
        cv.put(COLUMN_LYONNIT_3, tulosModel.getVayla3());
        cv.put(COLUMN_PARTULOS, tulosModel.getTulosPariin());
        cv.put(COLUMN_KOKONAISLYONNIT, tulosModel.getLyonnitYht());

        long insert = db.insert(KIERROS_TULOS, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;

        }

    }



    /**
     * Hakee golfkierrosten tulokset tietokannasta ja palauttaa ne listana.
     * @return List<tulosModel> -Lista kierrostuloksista.
     */
    public List<tulosModel> listaaKaikki() {
        List<tulosModel> palautaLista = new ArrayList<>();
        //String queryString = "SELECT * FROM " + KIERROS_TULOS;
        String queryString = "SELECT " + COLUMN_LYONNIT_1 + ", " +
                COLUMN_LYONNIT_2 + ", " +
                COLUMN_LYONNIT_3 + ", " +
                COLUMN_PARTULOS + ", " +
                COLUMN_KOKONAISLYONNIT + ", " +
                COLUMN_KIERROSID +
                " FROM " + KIERROS_TULOS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {

                int lyonnit1 = cursor.getInt(0);
                int lyonnit2 = cursor.getInt(1);
                int lyonnit3 = cursor.getInt(2);
                int parTulos = cursor.getInt(3);
                int kokonaisTulos = cursor.getInt(4);
                int kierrosID = cursor.getInt(5);

                tulosModel uusiTulos = new tulosModel(lyonnit1, lyonnit2, lyonnit3, parTulos, kokonaisTulos, kierrosID);
                palautaLista.add(uusiTulos);
            } while (cursor.moveToNext());
        }
        else {

        }
        cursor.close();
        db.close();

        return palautaLista;

    }


}
