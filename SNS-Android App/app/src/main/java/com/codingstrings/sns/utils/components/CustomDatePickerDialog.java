package com.codingstrings.sns.utils.components;

import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CustomDatePickerDialog implements android.app.DatePickerDialog.OnDateSetListener , View.OnClickListener{

    private Context context;
    private EditText view;
    private Calendar calendar;


    public CustomDatePickerDialog(Context context , View view){
        this.context = context;
        this.view = (EditText) view;
        this.calendar = Calendar.getInstance();
        view.setOnClickListener(this);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);
        view.setText(format.format(calendar.getTime()));

    }
    @Override
    public void onClick(View view) {
        calendar.set(Calendar.YEAR, 1990);
        calendar.set(Calendar.MONTH, 0  );
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        new android.app.DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
