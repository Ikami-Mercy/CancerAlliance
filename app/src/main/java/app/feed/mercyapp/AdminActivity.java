package app.feed.mercyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import app.feed.mercyapp.ui.activities.FeedUploadActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnFeed,btnEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbarAbout = findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbarAbout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        btnFeed = findViewById(R.id.btnFeed);
        btnFeed.setOnClickListener(this);
        btnEvent = findViewById(R.id.btnEvent);
        btnEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnFeed:
                startActivity(new Intent(AdminActivity.this, FeedUploadActivity.class));

        }
        switch (view.getId()) {
            case R.id.btnEvent:
                startActivity(new Intent(AdminActivity.this, EventUploadActivity.class));

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(AdminActivity.this, About.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}