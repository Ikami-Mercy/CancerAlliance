import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import app.feed.mercyapp.ui.MainActivity;
import app.feed.mercyapp.R;

public class SliderActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.white)
                .buttonsColor(R.color.colorPrimary)
                .image(R.drawable.label)
                .title("Welcome to Cancer Alliance App")
                .description("An app that enriches you")
                .build());

        addSlide(new SlideFragmentBuilder()
                .image(R.drawable.learn)
                .backgroundColor(R.color.white)
                .buttonsColor(R.color.colorPrimary)
                .title("Enjoy and learn about Cancer")
                .description("Share with your friends")
                .build());


       /* addSlide(new SlideFragmentBuilder()
                .image(R.drawable.chalo)
                .backgroundColor(R.color.white)
                .buttonsColor(R.color.colorPrimaryDark)
                .title("Pastor Charles")
                .description("He is the founder of Slice of today. A group that aims at spreading the Gospel to every corner of the world." +
                        " ...for so long as it is TODAY we should encourage each other ")
                .build());*/
    }

    @Override
    public void onFinish() {
        super.onFinish();
        //   Toast.makeText(this, "Try this library in your project! :)", Toast.LENGTH_SHORT).show();

        SahredPreferences sahredPreferences = new SahredPreferences(SliderActivity.this);

        sahredPreferences.setFirstTimeLaunch(false);

        startActivity(new Intent(SliderActivity.this, MainActivity.class));
        finish();
    }
}
