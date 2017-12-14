package app.feed.mercyapp.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import app.feed.mercyapp.*;
import app.feed.mercyapp.Config;
import app.feed.mercyapp.connection.ApiClient;
import app.feed.mercyapp.connection.Apinterface;
import app.feed.mercyapp.connection.ErrorUtils;
import app.feed.mercyapp.models.requests.FeedUploadRequest;
import app.feed.mercyapp.models.responses.FeedUploadResponse;
import app.feed.mercyapp.ui.fragments.DatePickerFragment;
import app.feed.mercyapp.utills.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedUploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int DATE_PICKER = 10;
    ImageButton imageButton_post;
    TextView tittle, dates, news,eventdates;
    EditText et_date;
    Button post;
    int MY_READ_PERMISSION_REQUEST_CODE = 1;
    int PICK_IMAGE_REQUEST = 2;
    ProgressDialog loading;
    private Uri uri;
    private String downloadUri;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_upload);
        imageButton_post = findViewById(R.id.imageButton_post);
        imageButton_post.setOnClickListener(this);

        post = findViewById(R.id.submit);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tittle.getText().toString().isEmpty()) {
                    Toast.makeText(FeedUploadActivity.this, "Please add a tittle",
                            Toast.LENGTH_SHORT).show();
                } else if (news.getText().toString().isEmpty()) {
                    Toast.makeText(FeedUploadActivity.this, "Please add a news feed",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // addDetails();
                    uploadDetails();
                }
            }
        });



        tittle = findViewById(R.id.et_title);
        eventdates = findViewById(R.id.et_date);
        news = findViewById(R.id.et_desc);
        TextView eventdates=  findViewById(R.id.et_date);
        eventdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });
        eventdates.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if (b) {

                }

            }
        });

    }
    //calendar
    public void getDate() {
        final Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(FeedUploadActivity.this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                mcurrentDate.set(Calendar.YEAR, selectedyear);
                mcurrentDate.set(Calendar.MONTH, selectedmonth);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                eventdates.setText(sdf.format(mcurrentDate.getTime()));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select date");
        mDatePicker.show();
    }
    private void uploadDetails() {


        progressDialog = new ProgressDialog(FeedUploadActivity.this);
        progressDialog.setMessage("Uploading news feed...");
        progressDialog.show();


        final String title = tittle.getText().toString().trim();
        final String date = eventdates.getText().toString().trim();
        final String feed = news.getText().toString().trim();


        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("Posts");
        mStorageRef.child(Util.generateString()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                FeedUploadRequest feedUploadRequest = new FeedUploadRequest();
                feedUploadRequest.setTittle(title);
                feedUploadRequest.setDate(date);
                feedUploadRequest.setFeed(feed);
                feedUploadRequest.setImage(taskSnapshot.getDownloadUrl().toString());


                // downloadUri =taskSnapshot.getDownloadUrl().toString() ;
                Util.writeLog("downloadurl", taskSnapshot.getDownloadUrl().toString());

                postData(feedUploadRequest);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Util.writeLog(FeedUploadActivity.class.getSimpleName(), "Error in uploading image!Try later or post without image");
                // App.showError(getActivity(), );
            }
        });


    }


    private void postData(FeedUploadRequest feedUploadRequest) {

        Apinterface apinterface = ApiClient.getClient().create(Apinterface.class);
        Call<FeedUploadResponse> call = apinterface.save_feed(feedUploadRequest);
        call.enqueue(new Callback<FeedUploadResponse>() {
            @Override
            public void onResponse(Call<FeedUploadResponse> call, Response<FeedUploadResponse> response) {

                progressDialog.dismiss();
                if (response.code() == 200) {

                    startActivity(new Intent(FeedUploadActivity.this, AdminActivity.class));
                    finish();

                } else {
                    Toast.makeText(FeedUploadActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FeedUploadResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(FeedUploadActivity.this, new ErrorUtils().parseOnFailure(t), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addDetails() {

        final String title = tittle.getText().toString().trim();
        final String date = eventdates.getText().toString().trim();
        final String feed = news.getText().toString().trim();


        class AddClient extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("Posts");
                mStorageRef.child(Util.generateString()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        downloadUri = taskSnapshot.getDownloadUrl().toString();
                        Util.writeLog("downloadurl", downloadUri);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Util.writeLog(FeedUploadActivity.class.getSimpleName(), "Error in uploading image!Try later or post without image");
                        // App.showError(getActivity(), );
                    }
                });

                loading = ProgressDialog.show(FeedUploadActivity.this, "Adding...", "Wait...", false, false);


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast.makeText(FeedUploadActivity.this, "" +
                        "Successfully Posted", Toast.LENGTH_LONG).show();

                nextAc();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();

                params.put(app.feed.mercyapp.Config.KEY_Date, date);
                params.put(Config.KEY_Feed, feed);
                params.put(Config.KEY_Tittle, title);
                params.put(Config.KEY_Image, downloadUri);

                Util.writeLog("downloadurl", params.toString());

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.Url_SaveFeed, params);
                Log.i("Response", "backgroundTask " + res);
                return res;
            }
        }

        AddClient ae = new AddClient();
        ae.execute();

    }

    public String getImageUrl() {
        //upload image
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference().child("Posts");
        mStorageRef.child(Util.generateString()).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                downloadUri = taskSnapshot.getDownloadUrl().toString();


                Util.writeLog("downloadurl", downloadUri);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Util.writeLog(FeedUploadActivity.class.getSimpleName(), "Error in uploading image!Try later or post without image");
                // App.showError(getActivity(), );
            }
        });
        return downloadUri;
    }

    public void nextAc() {
        startActivity(new Intent(this, AdminActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (ContextCompat.checkSelfPermission(FeedUploadActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

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
            Util.compressInputImage(data, this, imageButton_post);
            uri = data.getData();
        } else {
            // gracefully handle failure
            Log.w("debug", "Warning: activity result not ok");
        }
        return;
    }


}