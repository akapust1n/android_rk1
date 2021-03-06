package com.example.alexey.myapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceHelper.MemResultListener {

    private Storage storage;
    private final static String CATEGORY = "leo-cheers";
    private ImageView iv;
    private EditText topText, botText;
    private ActionBar actionBar;
    private String appName;

    static {
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectActivityLeaks()
                .penaltyLog()
                .penaltyDeath()
                .build()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storage = Storage.getInstance(getApplicationContext());
        appName = getResources().getString(R.string.app_name);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
            actionBar.setTitle(appName + " - " + getCategory());
            invalidateOptionsMenu();
        }
        topText = (EditText) findViewById(R.id.topText);
        botText = (EditText) findViewById(R.id.botText);
        Button btnCity = (Button) findViewById(R.id.btn_getMem);
        iv = (ImageView) findViewById(R.id.iv1);

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMemes();
            }
        });

    }

    private void getMemes() {
        Meme mem = new Meme(topText.getText().toString(), botText.getText().toString(), getCategory());
        ServiceHelper.getInstance(this).getMemes(this, this, mem);
    }

    private String getCategory() {
        String category = storage.loadCurrentTopic();
        String[] categories = getResources().getStringArray(R.array.array_categories);

        if (!category.isEmpty())
            return category;
        else if (categories.length > 0)
            return categories[0];

        return CATEGORY;
    }

    private void updateMem() {

        String mem = storage.getLastSavedMem();
        if (mem != null) {
            byte[] img = Base64.decode(mem, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            if (bitmap != null) {
                iv.setImageBitmap(bitmap);
            }

        } else {
            System.out.println("Error mem update");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemes();
        updateMem();
        actionBar.setTitle(appName + " - " + getCategory());
    }

    @Override
    protected void onStop() {
        ServiceHelper.getInstance(this).removeListener();
        super.onStop();
    }

    @Override
    public void onMemResult(final boolean success) {
        if (success)
            updateMem();
        else
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.info),
                    Toast.LENGTH_SHORT).show();
    }
}
