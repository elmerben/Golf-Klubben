package com.laskin.app;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.text.Editable;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class pelaaGolfia extends Activity {

    private Button tallenna; //Tallennusnappula

    private Button peruuta; // Peruutusnappula

    private TextView parLaskuri; // Kenttä, johon tulee par-tulos
    private TextView lyontiLaskuri; // Kenttä kokonaislyönneille

    private int[] parTulokset = {5, 3, 4}; // Jokaiselle väylälle asetetut PAR-tulokset
    private EditText[] lyonnitVaylat = new EditText[3]; // Kentät lyöntien kirjaamiselle
    private EditText[] parVaylat = new EditText[3]; // Kentät par-tulosten kirjaamiselle
    private int lopullinenPar = 0; // Kokonais-par-tulos
    private int kierrosID = 0; // Kierroksen tunnusnumero

    private boolean peruutaKaynnissa = false; // Peruutuspainikkeen tilan seuranta.

    private int kenttaParTulos = kenttaParTulos(parTulokset);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kierrostulos);
        peruuta = findViewById(R.id.peruuta);


        parLaskuri = findViewById(R.id.parLaskuri);
        lyontiLaskuri = findViewById(R.id.lyontiLaskuri);
        lyonnitVaylat[0] = findViewById(R.id.lyonnit1);
        lyonnitVaylat[1] = findViewById(R.id.lyonnit2);
        lyonnitVaylat[2] = findViewById(R.id.lyonnit3);

        for (int i = 0; i < lyonnitVaylat.length; i++) {
            final int luku = i;
            lyonnitVaylat[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    paivitaTulokset(i, i1, i2);

                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        }

        peruuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePeruuta();
            }
        });

        Button tallennaPainike = findViewById(R.id.tallenna);
        tallennaPainike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTallenna();
            }

        });


    }

    /**
     * Päivittää aktiivisesti näytettävät tulokset
     * lyöntien muokkauksen yhteydessä
     * @param luku - Muokattu kenttä
     * @param luku2 - Muutoksen alkukohta
     * @param luku3 - Muutoksen pituus
     */
    private void paivitaTulokset(int luku, int luku2, int luku3) {
        int lyonnitYhteensa = 0;
        int parYhteensa = 0;
        int lopullinenPar = 0;
        int lyonnitArvo = 0;

        for (int i = 0; i < lyonnitVaylat.length; i++) {
            // Ottaa kunkin laatikon arvon ja muuttaa sen tekstiksi
            String lyonnitTeksti = lyonnitVaylat[i].getText().toString();


            if (!TextUtils.isEmpty(lyonnitTeksti)) {
                lyonnitArvo = Integer.parseInt(lyonnitTeksti);
                parYhteensa = lyonnitArvo - parTulokset[i];
                lopullinenPar += parYhteensa;
            } else {
                lyonnitArvo = 0;
            }

            lyonnitYhteensa += lyonnitArvo;


        }

        if (lopullinenPar == 0) {
            parLaskuri.setText("E");
        } else {
            parLaskuri.setText(" " + lopullinenPar);
        }
        lyontiLaskuri.setText(" " + lyonnitYhteensa);

    }

    private int kenttaParTulos(int[] parTulokset){
        int kentanPar = 0;
        for (int luku: parTulokset) {
            kentanPar += luku;

        }
        return kentanPar;

    }

    /**
     * Käsittelee tapahtuman, kun käyttäjä painaa "Peruuta".
     */
    private void handlePeruuta() {
        if (!peruutaKaynnissa) {
            peruutaKaynnissa = true;
        }
        finish();
    }

    /**
     * Käsittelee tapahtuman käyttäjän painaessa "Tallenna".
     */
    private void handleTallenna() {



        kierrosID++;
        tulosModel TulosModel;

        try {
            int lyonnit1 = Integer.parseInt(lyonnitVaylat[0].getText().toString());
            int lyonnit2 = Integer.parseInt(lyonnitVaylat[1].getText().toString());
            int lyonnit3 = Integer.parseInt(lyonnitVaylat[2].getText().toString());


            int lyonnit = lyonnit1 + lyonnit2 + lyonnit3;
            int par = lyonnit - kenttaParTulos;

            TulosModel = new tulosModel(lyonnit1, lyonnit2, lyonnit3, par, lyonnit, kierrosID);
            Toast.makeText(this, TulosModel.toString(), Toast.LENGTH_LONG).show();


        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Virheellinen syöte.", Toast.LENGTH_LONG).show();
            TulosModel = new tulosModel(-1, -1, -1 , -1 , -1 , -1 );
        }

        DataBaseHelper dataBaseHelper  = new DataBaseHelper(pelaaGolfia.this);


        boolean success = dataBaseHelper.addOne(TulosModel);

        //Toast.makeText(pelaaGolfia.this, "Success= " + success, Toast.LENGTH_SHORT).show();

        for (EditText editText : lyonnitVaylat) {
            editText.setText("");
        }
        parLaskuri.setText("E");
        lyontiLaskuri.setText("");



    }



}







