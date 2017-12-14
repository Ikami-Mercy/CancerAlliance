package app.feed.mercyapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.feed.mercyapp.ui.activities.FeedUploadActivity;
import app.feed.mercyapp.utills.Util;

public class EventUploadActivity extends AppCompatActivity implements View.OnClickListener {
ImageButton imageButton_event;
    TextView ev_title, ev_venue, ev_when,ev_time,ev_desc;
    Button submit;
    int MY_READ_PERMISSION_REQUEST_CODE = 1;
    int PICK_IMAGE_REQUEST = 2;

    ProgressDialog loading;
    private Uri uri;
    private String downloadUri;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_upload);
        imageButton_event = findViewById(R.id.imageButton_event);
        imageButton_event.setOnClickListener(this);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ev_title.getText().toString().isEmpty()) {
                    Toast.makeText(EventUploadActivity.this, "Please add a tittle",
                            Toast.LENGTH_SHORT).show();
                } else if (ev_venue.getText().toString().isEmpty()) {
                    Toast.makeText(EventUploadActivity.this, "Please add a venue",
                            Toast.LENGTH_SHORT).show();
                }

                else if (ev_desc.getText().toString().isEmpty()) {
                    Toast.makeText(EventUploadActivity.this, "Please describe the event",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    // addDetails();
                    uploadDetails();
                }
            }
        });

        ev_title = findViewById(R.id.ev_title);
        ev_venue = findViewById(R.id.ev_venue);
        ev_when = findViewById(R.id.ev_when);
        ev_time = findViewById(R.id.ev_time);
        ev_desc= findViewById(R.id.ev_desc);

        TextView ev_when=  findViewById(R.id.ev_when);
        ev_when.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
    }

    private void uploadDetails() {


        progressDialog = new ProgressDialog(EventUploadActivity.this);
        progressDialog.setMessage("Uploading event...");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            finish();
            }
        }, 5000);

    }

    @Override
    public void onClick(View view) {
        if (ContextCompat.checkSelfPermission(EventUploadActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_READ_PERMISSION_REQUEST_CODE);
            } else {
            }
        }


        }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && reqCode == PICK_IMAGE_REQUEST) {
            Util.compressInputImage(data, this, imageButton_event);
            uri = data.getData();
        } else {
            // gracefully handle failure
            Log.w("debug", "Warning: activity result not ok");
        }
        return ;

    }
    public void getDate() {
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(EventUploadActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                ev_when.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }
}

