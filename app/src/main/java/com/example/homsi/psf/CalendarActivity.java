package com.example.homsi.psf;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.widget.DatePicker;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class CalendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
    }
    public void toSecondTimer(View view){
        final DatePicker datePicker1 = findViewById(R.id.datePicker1);
        int year = datePicker1.getYear();
        int month = datePicker1.getMonth() + 1;
        int day = datePicker1.getDayOfMonth();
        String date = month + "/" + day + "/" + year;

        Intent cal2 = new Intent(CalendarActivity.this, EndDate.class);

        String startTime = "";
        String endTime = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
        }

        if(isValidStartDate(date)){
        cal2.putExtra("startTime",startTime);
        cal2.putExtra("endTime",endTime);
        cal2.putExtra("beginDate",date );
        startActivity(cal2);
        }

    }
    public boolean isValidStartDate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(input.trim());

        } catch (ParseException pe) {
            return false;
        }
        return true;
    }


}