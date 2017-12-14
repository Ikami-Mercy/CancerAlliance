package app.feed.mercyapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import app.feed.mercyapp.About;
import app.feed.mercyapp.AdminActivity;
import app.feed.mercyapp.EventsFragment;
import app.feed.mercyapp.FeedBackFragment;
import app.feed.mercyapp.Login2Activity;
import app.feed.mercyapp.LoginActivity;
import app.feed.mercyapp.PartnersActivity;
import app.feed.mercyapp.ui.fragments.HomeFragment;
import app.feed.mercyapp.R;
import app.feed.mercyapp.ViewpagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.toolbarLoanStatuses)
    Toolbar toolbarLoanStatuses;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    TextView text,about;
    private SlidingRootNav slidingRootNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarLoanStatuses);
        getSupportActionBar().setTitle("");
        setUpVp();
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbarLoanStatuses)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.side_bar_menu)
                .inject();
                text = findViewById(R.id.textVidfdfdfdfdew6);
                 about = findViewById(R.id.tsdsextView6);
                text.setOnClickListener(this);
                about.setOnClickListener(this);
    }


    private void setUpVp() {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                ViewpagerAdapter viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager());
                viewpagerAdapter.addFragment(new HomeFragment(), "Home");
                viewpagerAdapter.addFragment(new EventsFragment(), "Events");
                viewpagerAdapter.addFragment(new FeedBackFragment(), "Feedback");

                viewpager.setAdapter(viewpagerAdapter);

            }
        });

        tabs.setupWithViewPager(viewpager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            startActivity(new Intent(MainActivity.this, About.class));
            return true;
        }

        if (id == R.id.action_partners) {
            startActivity(new Intent(MainActivity.this, PartnersActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.textVidfdfdfdfdew6:
        startActivity(new Intent(MainActivity.this, Login2Activity.class));

    }
        switch (view.getId()) {
            case R.id.tsdsextView6:
                startActivity(new Intent(MainActivity.this, About.class));

        }}

}
