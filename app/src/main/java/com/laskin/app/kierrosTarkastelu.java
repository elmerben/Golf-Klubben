package com.laskin.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
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
import java.util.List;

import android.widget.Button;


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
        kierrosLista();

        painike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePalaa();
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
    public void kierrosLista(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper((kierrosTarkastelu.this));
        List<tulosModel> kaikki = dataBaseHelper.listaaKaikki();
        pelaajaArrayAdapter = new ArrayAdapter<tulosModel>(kierrosTarkastelu.this, android.R.layout.simple_list_item_1, kaikki);
        listView.setAdapter(pelaajaArrayAdapter);

    }



}
