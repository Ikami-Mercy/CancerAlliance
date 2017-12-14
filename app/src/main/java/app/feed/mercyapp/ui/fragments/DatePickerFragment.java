package app.feed.mercyapp.ui.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import app.feed.mercyapp.R;

import static java.util.Calendar.YEAR;


public class  DatePickerFragment extends DialogFragment {
    static Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public static DatePickerFragment newInstance(Context xs) {
        DatePickerFragment x = new DatePickerFragment();
        context = xs;
        return x;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.datepicker, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

              //  RegisterActivity callingActivity = (RegisterActivity) getActivity();
                String dateString = Integer.toString(dayOfMonth)  + "-" + Integer.toString(month) + "-" + Integer.toString(year) ;

                try {
                    Date date = format.parse(dateString);
                    Calendar c = Calendar.getInstance();
                    Date currentDate = c.getTime();

                    Calendar a = getCalendar(date);
                    Calendar b = getCalendar(currentDate);
                    int diff = b.get(YEAR) -  a.get(YEAR);

                    Intent i = new Intent()
                            .putExtra("date",date.toString());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);

                   // dismiss();

                    Log.e("DATES", "Today: " + currentDate.toString() + "\nSelected: " + date.toString() + "\nDiff: " + Integer.toString(diff));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, year, month, day);

        dialog.getDatePicker().setMaxDate(new Date().getTime());

        // Create a new instance of DatePickerDialog and return it
        return dialog;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    interface SetDate{
        void choosenDate();
    }
}
