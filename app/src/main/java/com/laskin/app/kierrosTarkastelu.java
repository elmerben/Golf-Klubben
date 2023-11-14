package com.laskin.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.Manifest;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.widget.Button;
import android.widget.Toast;


public class kierrosTarkastelu extends Activity {


    private Switch rulla;
    private TextView textView;
    private ListView listView; // Listanäkymä kierroksille
    private Button painike; // Palaa-näppäin, jotta käyttäjä pääsee takaisin

    private Boolean peruutaKaynnissa = false; //Palaa-painikkeen tilan seuranta
    ArrayAdapter pelaajaArrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vanhat_kierrokset);
        listView = findViewById(R.id.kierrosLista);
        painike = findViewById(R.id.painikeKierros);
        pelaajaArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<tulosModel>());
        listView = findViewById(R.id.kierrosLista);
        listView.setAdapter(pelaajaArrayAdapter);
        kierrosLista();

        painike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePalaa();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                tulosModel clickedTulos = (tulosModel) parent.getItemAtPosition(position);
                DataBaseHelper dbHelper = new DataBaseHelper(kierrosTarkastelu.this);
                boolean poistettu = dbHelper.deleteOne(clickedTulos);

                if (poistettu) {
                    kierrosLista();


                    // Näytä ilmoitus poistamisesta
                    Toast.makeText(kierrosTarkastelu.this, "Poistettu " + clickedTulos.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    // Näytä ilmoitus, jos poistaminen epäonnistui
                    Toast.makeText(kierrosTarkastelu.this, "Tuloksen poistaminen epäonnistui.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

            /**
             * Käsittelee "Palaa"-painikkeen toiminnallisuuden
             */
            private void handlePalaa() {
                if (!peruutaKaynnissa) {
                    peruutaKaynnissa = true;
                }
                finish();
            }

            /**
            * Näyttää pelatut kierrokset listana.
            */
            public void kierrosLista() {
                DataBaseHelper db = new DataBaseHelper(kierrosTarkastelu.this);
                List<tulosModel> kaikki = db.listaaKaikki();
                pelaajaArrayAdapter.clear();
                pelaajaArrayAdapter.addAll(kaikki);
                pelaajaArrayAdapter.notifyDataSetChanged();
                listView.setAdapter(pelaajaArrayAdapter);
            }

        }

