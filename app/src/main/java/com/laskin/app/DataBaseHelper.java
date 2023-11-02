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

    public DataBaseHelper(@Nullable Context context) {
        super(context, "tulokset.db" , null, 1);
    }

    // this is called the first time a database is accessed. There should be
    // code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + KIERROS_TULOS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_LYONNIT_1 + " INT, " + COLUMN_LYONNIT_2 + " INT, " + COLUMN_LYONNIT_3 + " INT, " + COLUMN_PARTULOS + " INT, " + COLUMN_KOKONAISLYONNIT + " INT, " + COLUMN_KIERROSID + " INT)";
        db.execSQL(createTableStatement);



    }
    // this is called if the database version number changes.
    // It prevents previous users apps from breaking when you change the
    // database desing.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

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
    public List<tulosModel> listaaKaikki() {
        List<tulosModel> palautaLista = new ArrayList<>();
        String queryString = "SELECT * FROM " + KIERROS_TULOS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int kierrosID = cursor.getInt(0);
                int lyonnit1 = cursor.getInt(1);
                int lyonnit2 = cursor.getInt(2);
                int lyonnit3 = cursor.getInt(3);
                int parTulos = cursor.getInt(4);
                int kokonaisTulos = cursor.getInt(5);

                tulosModel uusiTulos = new tulosModel(kierrosID, lyonnit1, lyonnit2, lyonnit3, parTulos, kokonaisTulos);
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