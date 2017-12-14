package app.feed.mercyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.feed.mercyapp.ui.activities.FeedUploadActivity;

public class Login2Activity extends AppCompatActivity {
    Button loginsign;
    TextView et_register_repass,et_register_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        loginsign = findViewById(R.id.loginsign);
        loginsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_register_email.getText().toString().isEmpty()) {
                    Toast.makeText(Login2Activity.this, "Please enter your email",
                            Toast.LENGTH_SHORT).show();
                } else if (et_register_repass.getText().toString().isEmpty()) {
                    Toast.makeText(Login2Activity.this, "Please enter your password",
                            Toast.LENGTH_SHORT).show();
                }

                else {
                    switch (view.getId()) {
                        case R.id.loginsign:
                            //you select username from hiyo table ya users
                            String username ="admin";

                            if(et_register_repass.getText().toString().equalsIgnoreCase(username)){
                                startActivity(new Intent(Login2Activity.this, AdminActivity.class));
                            }else{
                                Toast.makeText(Login2Activity.this, "Please contact support for registration",
                                        Toast.LENGTH_SHORT).show();
                            }


                    }
                }
                finish();
            }
        });

        et_register_email = findViewById(R.id.et_register_email);
        et_register_repass = findViewById(R.id.et_register_repass);

    }
    }

