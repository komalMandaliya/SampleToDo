package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MyActivity extends Activity
{

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapters;
    ListView lvItems;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        lvItems =(ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapters = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapters);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();

    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        items.remove(i);
                        itemsAdapters.notifyDataSetChanged();
                        writeItems();
                        return true;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.layout.main, menu);
        return true;
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItems);
        String itemText = etNewItem.getText().toString();
        itemsAdapters.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile))  ;
        } catch(IOException e){
             items = new ArrayList<String>();
        }
    }

    public void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
