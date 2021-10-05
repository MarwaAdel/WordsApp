package com.example.wordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.ToggleButton;

import com.example.wordsapp.Adapter.WordsAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    public WordsAdapter adapter;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.toggle)
    ToggleButton toggle;
    String htmlData;
    Map<String, Integer> map = new HashMap<String, Integer>();
    Map<String, Integer> newMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
                    htmlData = stringBuffer.toString();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            String[] WC = htmlData.toString().split("\\s+");
                            List<String> words = Arrays.asList(WC);
                            Log.e("species", words + "");

                            for (String s : WC) {
                                map.put(s, Collections.frequency(words, s));
                            }
//                            for (Map.Entry<String, Integer> entry : map.entrySet()) {
//                                Log.e("map", entry.getKey() + " : " + entry.getValue());
//                            }
                            adapter = new WordsAdapter(map);
                            dialog.dismiss();
                            listView.setAdapter(adapter);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggle.isChecked()) {
                   newMap= sortMap(map);
                    adapter = new WordsAdapter(newMap);
                    listView.setAdapter(adapter);
                } else {
                    LinkedHashMap<String, Integer> reversed = new LinkedHashMap<>();
                    String[] keys = newMap.keySet().toArray(new String[newMap.size()]);
                    for (int i = keys.length - 1; i >= 0; i--) {
                        reversed.put(keys[i], newMap.get(keys[i]));
                    }
                    adapter = new WordsAdapter(reversed);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    public Map<String, Integer> sortMap(Map<String, Integer> unsortMap) {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
                unsortMap.entrySet());

        // sort list based on comparator
        Collections.sort(list, new Comparator<Object>() {
            @SuppressWarnings("unchecked")
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o1).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o2).getValue());
            }
        });

        // put sorted list into map again
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


}

