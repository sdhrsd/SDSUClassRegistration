package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CompleteClassDetails extends AppCompatActivity {

    TextView fullTitleDetail;
    TextView courseDetail;
    TextView titleDetail;
    TextView sectionDetail;
    TextView scheduleDetail;
    TextView unitsDetail;
    TextView seatsDetail;
    TextView meetingsDetail;
    TextView locationDetail;
    TextView instructorDetail;
    TextView descriptionDetail;
    TextView prerequisiteDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_class_details);
        fullTitleDetail = findViewById(R.id.fullTitleInDetail);
        courseDetail = findViewById(R.id.courseInDetail);
        titleDetail = findViewById(R.id.titleInDetail);
        sectionDetail = findViewById(R.id.sectionInDetail);
        scheduleDetail = findViewById(R.id.scheduleInDetail);
        unitsDetail = findViewById(R.id.unitsInDetail);
        seatsDetail = findViewById(R.id.seatsInDetail);
        meetingsDetail = findViewById(R.id.meetingsInDetail);
        locationDetail = findViewById(R.id.locationInDetail);
        instructorDetail = findViewById(R.id.instructorInDetail);
        descriptionDetail = findViewById(R.id.descriptionInDetail);
        prerequisiteDetail = findViewById(R.id.prerequisiteInDetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fullTitleDetail.setText(extras.getString("fullTitle"));
            String courseDetailString = extras.getString("subject") +"-"+ extras.getString("courseNo");
            courseDetail.setText(courseDetailString);
            titleDetail.setText(extras.getString("title"));
            sectionDetail.setText(extras.getString("section"));
            scheduleDetail.setText(extras.getString("scheduleNo"));
            unitsDetail.setText(extras.getString("units"));
            int openSeats = extras.getInt("seats") - extras.getInt("enrolled");
            String seatsDetailString = openSeats +"/"+ extras.getInt("seats");
            seatsDetail.setText(seatsDetailString);
            String meetingsInDetailString = extras.getString("meetingType") + "  " + extras.getString("start")+"-"+extras.getString("end");
            meetingsDetail.setText(meetingsInDetailString);
            String locationInDetailString = extras.getString("building")+ "-" + extras.getString("room");
            locationDetail.setText(locationInDetailString);
            instructorDetail.setText(extras.getString("instructor"));
            descriptionDetail.setText(extras.getString("description"));
            prerequisiteDetail.setText(extras.getString("prerequisite"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logoutButtonClicked();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logoutButtonClicked() {
        SharedPreferences preferences =getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent go = new Intent(this,MainActivity.class);
        startActivity(go);
        finish();
    }
}
