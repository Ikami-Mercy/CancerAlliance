package app.feed.mercyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.feed.mercyapp.ui.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
Button loginsign;
TextView et_register_email,et_register_repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        loginsign = findViewById(R.id.loginsign);
        loginsign.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginsign:
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));

        }
        }


}
