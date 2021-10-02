package com.example.wordsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements WordAdapter.OnItemClickedListner, NumberAdapter.OnItemClickedListner {
    ProgressDialog dialog;
    WordAdapter adapter;
    NumberAdapter adapter2;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;
    Integer number;
    ArrayList<Integer> number2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(MainActivity.this);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        dialog = ProgressDialog.show(MainActivity.this, "",
                getString(R.string.loading_please_wait), true);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL google = null;
                    try {
                        google = new URL("https://www.instabug.com");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    BufferedReader in = null;
                    try {
                        in = new BufferedReader(new InputStreamReader(google.openStream()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String input = null;
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        try {
                            if (!((input = in.readLine()) != null)) break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stringBuffer.append(input);
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                    String htmlData = stringBuffer.toString();
                    Log.e("species",htmlData+"");

                    String[] WC = htmlData.toString().split("\\s+");
                    List<String> species = Arrays.asList(WC);
                    adapter = new WordAdapter(MainActivity.this, species);
                    adapter.submitList(species);

                    Set<String> uniqueWords = new HashSet<String>(species);
                    for (String word : uniqueWords) {
                        Log.e("marwaList2", Collections.frequency(species, word) + "");
                        number = Collections.frequency(species, word);
                        number2.add(number);
                    }
                    adapter2 = new NumberAdapter(MainActivity.this, number2);
                    adapter2.submitList(number2);
                    recyclerView.setAdapter(adapter);
                    recyclerView2.setAdapter(adapter2);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

}