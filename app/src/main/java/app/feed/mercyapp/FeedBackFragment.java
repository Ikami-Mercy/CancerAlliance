package app.feed.mercyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import app.feed.mercyapp.ui.activities.FeedUploadActivity;
import app.feed.mercyapp.ui.fragments.HomeFragment;


public class FeedBackFragment extends Fragment implements View.OnClickListener {

    EditText userName = null, email = null, subject = null, message = null;
    Button submit;
    private ProgressDialog loading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View curView = inflater.inflate(R.layout.fragment_feedback, container, false);

        userName = curView.findViewById(R.id.editName);
        email = curView.findViewById(R.id.editEmail);
        subject = curView.findViewById(R.id.editSubject);
        message = curView.findViewById(R.id.editMessage);
        submit = curView.findViewById(R.id.signin);

        submit.setOnClickListener(this);

        return curView;


    }

    public void submitFeedBack () {

        final String Name = userName.getText().toString().trim();
        final String feedEmail = email.getText().toString().trim();
        final String feedSubject = subject.getText().toString().trim();
        final String feedMessage = message.getText().toString().trim();
        class AddClient extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Submitting...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Log.i("Response", "response " + s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getBoolean("error")) {
                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_LONG).show();
                        userName.getText().clear();
                        email.getText().clear();
                        subject.getText().clear();
                        message.getText().clear();
                        //nextAc();
                    } else {
                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Config.KEY_Name, Name);
                params.put(Config.KEY_Email, feedEmail);
                params.put(Config.KEY_Subject, feedSubject);
                params.put(Config.KEY_Message, feedMessage);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.Url_saveFeedback, params);
                Log.i("Response", "backgroundTask " + res);
                return res;
            }
        }

        AddClient ae = new AddClient();
        ae.execute();
    }
    public void nextAc() {
        Intent intent = new Intent(getContext(), FeedBackFragment.class);
        startActivity(intent);

  }


/*
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Uploading feedback...");
                progressDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 5000);*/


    @Override
    public void onClick(View curView) {
        switch (curView.getId()) {
            case R.id.signin:

                if (userName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter name of your name", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (email.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter  your email adress", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (subject.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter subject", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter message", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    submitFeedBack();
                }



/*
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("Thanks for your Feedback!")
                        .show();*/

        }

    }

}



