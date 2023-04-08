package com.example.homsi.psf;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class EndDate extends AppCompatActivity {
    final Context MyContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_date);
    }

    public void toSecondTimer(View view){
        final DatePicker datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
        int year = datePicker1.getYear();
        int month = datePicker1.getMonth() + 1;
        int day = datePicker1.getDayOfMonth();
        Button next = findViewById(R.id.toTime2Button);
        String date = month + "/" + day + "/" + year;

        Intent cal2 = new Intent(this, createListing_Activity.class);
        String startTime = "";
        String endTime = "";
        String startDate = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            startTime = bundle.getString("startTime");
            endTime = bundle.getString("endTime");
            startDate = bundle.getString("beginDate");

            if (validEnd(startDate, date) && isValidEndDate(date)){
                cal2.putExtra("startTime", startTime);
                cal2.putExtra("endTime", endTime);
                cal2.putExtra("beginDate", startDate);
                cal2.putExtra("endDate", date);
                startActivity(cal2);
            }
            else {
                CharSequence message = "Please pick a valid date";
                Toast.makeText(MyContext, message, Toast.LENGTH_SHORT).show();
            }

        }
        //startActivity(cal2);

    }

    public boolean isValidEndDate(String input){
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(input.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public boolean validEnd(String date1, String date2){
        String[] bDate = date1.split("/");
        String mon1 = bDate[0];
        String day1 = bDate[1];
        String year1 = bDate[2];
        String[] eDate = date2.split("/");
        String mon2 = eDate[0];
        String day2 = eDate[1];
        String year2 = eDate[2];


        if(Integer.parseInt(mon1) == Integer.parseInt(mon2) && Integer.parseInt(year1) <= Integer.parseInt(year2)&& Integer.parseInt(day1) < (Integer.parseInt(day2))) {
            return true;
        }
        else if(Integer.parseInt(mon1) < Integer.parseInt(mon2) && Integer.parseInt(year1) <= Integer.parseInt(year2)) {
            return true;
        }
        else if(Integer.parseInt(year2) > Integer.parseInt(year1))
        {
            return true;
        }

        return false;

    }


}