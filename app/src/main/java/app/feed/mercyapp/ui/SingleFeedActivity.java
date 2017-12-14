package app.feed.mercyapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.feed.mercyapp.R;
import app.feed.mercyapp.models.responses.FeedResponse;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.ContactsContract.CommonDataKinds.Organization.TITLE;

public class SingleFeedActivity extends AppCompatActivity {
    // private FloatingActionButton share;
    public static final String CONTENT = "tvSingleTitle";
    public static final String SCRIPTURES = "tvSingleScriptures";
    @BindView(R.id.toolbar_singleView)
    Toolbar toolbarSingleView;
    @BindView(R.id.uv_single_image)
    ImageView uvSingleImage;
    @BindView(R.id.tv_single_title)
    TextView tvSingleTitle;
    @BindView(R.id.tv_single_scriptures)
    TextView tvSingleScriptures;
    @BindView(R.id.tv_single_content)
    TextView tvSingleContent;
    private FloatingActionButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_feed);
        Toolbar toolbarAbout = findViewById(R.id.toolbar_singleView);
        setSupportActionBar(toolbarAbout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        ButterKnife.bind(this);
        share = (FloatingActionButton) findViewById(R.id.floatingActionButton_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, getIntent().getExtras().getString(TITLE));
                i.putExtra(Intent.EXTRA_TEXT, "News Tittle: \n" + getIntent().getExtras().getString(SCRIPTURES) + "\n"
                        + "News Feed:\n" + getIntent().getExtras().getString(CONTENT));
                startActivity(Intent.createChooser(i, "Share via"));

            }
        });

        populate();

    }

    private void populate() {

        final FeedResponse feedResponse = (FeedResponse) getIntent().getSerializableExtra("feed");
        tvSingleTitle.setText(feedResponse.getTittle());
        tvSingleContent.setText(feedResponse.getFeed());
        tvSingleScriptures.setText(feedResponse.getDate());

        new android.os.Handler().post(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(SingleFeedActivity.this)
                        .load(feedResponse.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                        .centerCrop()
                        .crossFade()
                        .into(uvSingleImage);
            }
        });
    }

}
