package com.example.alexey.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends AppCompatActivity {
    private Storage storage;
    private String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        storage = Storage.getInstance(getApplicationContext());
        categories = getResources().getStringArray(R.array.array_categories);
        ListView lvCategories = (ListView) findViewById(R.id.lv_categories);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, categories);

        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 0 && position < categories.length)
                    closeActivity(categories[position]);
                else
                    closeActivity("tzuyu");
            }
        });
    }

    private void closeActivity(String categoryName) {
        storage.saveCurrentTopic(categoryName);
        finish();
    }
}
