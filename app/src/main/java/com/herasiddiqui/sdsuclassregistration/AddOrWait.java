package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AddOrWait extends AppCompatActivity {

    TextView courseNo;
    TextView scheduleNo;
    TextView units;
    TextView location;
    TextView title;
    TextView department;
    TextView instructor;
    TextView seats;
    TextView waitlist;
    TextView days;
    Button finalAorW;
    Button cancelAorW;
    public static final String PREFS_DEFAULT = "";
    SharedPreferences sharedPref;
    String retrievedRedID;
    String retrievedPassword;
    int courseid;
    int openSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_wait);

        sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        retrievedRedID = sharedPref.getString("RedID",PREFS_DEFAULT);
        retrievedPassword = sharedPref.getString("password",PREFS_DEFAULT);

        courseNo = findViewById(R.id.cnoAorW);
        scheduleNo = findViewById(R.id.snoAorW);
        units = findViewById(R.id.unAorW);
        location = findViewById(R.id.locAorW);
        title = findViewById(R.id.fullTitle);
        department = findViewById(R.id.deptAorW);
        instructor = findViewById(R.id.instAorW);
        days = findViewById(R.id.daysAorW);
        seats = findViewById(R.id.seatsAorW);
        waitlist = findViewById(R.id.waitlistAorW);
        finalAorW = findViewById(R.id.finalAorW);
        cancelAorW = findViewById(R.id.cancelAorW);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String departmentCourseNo = extras.getString("subject") + "-" + extras.getString("courseNo");
            courseNo.setText(departmentCourseNo);
            scheduleNo.setText(extras.getString("scheduleNo"));
            units.setText(extras.getString("units"));
            String buildingRoom = extras.getString("building") + "-" + extras.getString("room");
            location.setText(buildingRoom);
            title.setText(extras.getString("fullTitle"));
            department.setText(extras.getString("department"));
            instructor.setText(extras.getString("instructor"));
            days.setText(extras.getString("days"));
            courseid = extras.getInt("courseId");
            openSeats = extras.getInt("seats") - extras.getInt("enrolled");
            String seatsString = openSeats + "/" + extras.getInt("seats");
            seats.setText(seatsString);
            waitlist.setText(Integer.toString(extras.getInt("waitlist")));
            if(openSeats > 0){
                finalAorW.setText(R.string.addS);
            }
            else {
                finalAorW.setText(R.string.waitS);
            }
        }
        cancelAorW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finalAorW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrWaitlistStudent();
            }
        });
    }

    public void addOrWaitlistStudent(){
        String urlAorW = "";
        if(openSeats > 0) {
            urlAorW = "https://bismarck.sdsu.edu/registration/registerclass?redid="+ retrievedRedID + "&password="+retrievedPassword+"&courseid="+courseid;
        } else {
            urlAorW = "https://bismarck.sdsu.edu/registration/waitlistclass?redid="+ retrievedRedID + "&password="+retrievedPassword+"&courseid="+courseid;
        }
        JsonObjectRequest registerOrAddRequest = new JsonObjectRequest(Request.Method.GET, urlAorW, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("ok")) {
                                // Show classes
                                goBack();
                            }
                            else {
                                Toast.makeText(AddOrWait.this,response.getString("error"),Toast.LENGTH_LONG).show();
                            }

                        } catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(registerOrAddRequest);
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
        SharedPreferences preferences =getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        Intent go = new Intent(this,MainActivity.class);
        startActivity(go);
        finish();
    }

    public void goBack() {
        Intent goToclasses = new Intent(this,ClassesAndWaitlist.class);
        navigateUpTo(goToclasses);
    }

}
