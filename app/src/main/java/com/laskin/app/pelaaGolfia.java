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

    private Button tallenna;

    private Button peruuta;

    private TextView parLaskuri;
    private TextView lyontiLaskuri;

    private int[] parTulokset = {5, 3, 4};
    private EditText[] lyonnitVaylat = new EditText[3];
    private EditText[] parVaylat = new EditText[3];
    private int lopullinenPar = 0;
    private int kierrosID = 0;

    private boolean peruutaKaynnissa = false;

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


    private void handlePeruuta() {
        if (!peruutaKaynnissa) {
            peruutaKaynnissa = true;
        }
        finish();
    }

    private void handleTallenna() {



        kierrosID++;
        tulosModel TulosModel;

        try {
            int lyonnit1 = Integer.parseInt(lyonnitVaylat[0].getText().toString());
            int lyonnit2 = Integer.parseInt(lyonnitVaylat[1].getText().toString());
            int lyonnit3 = Integer.parseInt(lyonnitVaylat[2].getText().toString());

            int par = lopullinenPar;
            int lyonnit = lyonnit1 + lyonnit2 + lyonnit3;

            TulosModel = new tulosModel(lyonnit1, lyonnit2, lyonnit3, par, lyonnit, kierrosID);
            Toast.makeText(this, TulosModel.toString(), Toast.LENGTH_LONG).show();


        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Virheellinen syöte.", Toast.LENGTH_LONG).show();
            TulosModel = new tulosModel(-1, -1, -1 , -1 , -1 , -1 );
        }

        DataBaseHelper dataBaseHelper  = new DataBaseHelper(pelaaGolfia.this);


        boolean success = dataBaseHelper.addOne(TulosModel);

        Toast.makeText(pelaaGolfia.this, "Success= " + success, Toast.LENGTH_LONG).show();

        for (EditText editText : lyonnitVaylat) {
            editText.setText("");
        }
        parLaskuri.setText("E");
        lyontiLaskuri.setText("");


//
// String tiedostonimi = "tulokset.txt";
// String kansioNimi = "Data";
//
// File kansio = new File(getFilesDir(), kansioNimi);
// if (!kansio.exists()) {
//     kansio.mkdir();
// }
// File tiedosto = new File(kansio, tiedostonimi);
//
//
//
// FileOutputStream fos = new FileOutputStream(tiedosto, true);
// OutputStreamWriter kirjoittaja = new OutputStreamWriter(fos);
//
//
// for (int i = 0; i < parTulokset.length; i++) {
//     String rivi = "Väylä " + (i + 1) + ": Par " + parTulokset[i] + ", Lyönnit " + lyonnitVaylat[i].getText().toString() + "\n";
//     kirjoittaja.write(rivi);
// }
//
// kirjoittaja.close();
// fos.close();
//
// String tallennusPaikka = tiedosto.getAbsolutePath();
//
// Log.d("TiedostonPolku", "Tiedosto tallennettu osoitteeseen: " + tallennusPaikka);
// // Toast.makeText(this, "Tiedot tallennettu onnistuneesti", Toast.LENGTH_SHORT).show();
// Toast.makeText(this, "Tiedosto tallennettu: " + tallennusPaikka, Toast.LENGTH_SHORT).show();        }catch(IOException e){
//     e.printStackTrace();
//     Toast.makeText(this, "Tallennus epäonnistui", Toast.LENGTH_SHORT).show();
// }



    }



}







