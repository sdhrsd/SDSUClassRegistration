package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Drop extends AppCompatActivity {

    TextView courseNo;
    TextView scheduleNo;
    TextView units;
    TextView location;
    TextView title;
    TextView department;
    TextView instructor;
    TextView days;
    Button cancelDrop;
    Button finalDrop;
    boolean isEnrolled;
    public static final String PREFS_DEFAULT = "";
    SharedPreferences sharedPref;
    String retrievedRedID;
    String retrievedPassword;
    int courseid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);

        sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        retrievedRedID = sharedPref.getString("RedID",PREFS_DEFAULT);
        retrievedPassword = sharedPref.getString("password",PREFS_DEFAULT);

        courseNo = findViewById(R.id.textViewCourseNo);
        scheduleNo = findViewById(R.id.textViewScheduleNo);
        units = findViewById(R.id.textViewUnits);
        location = findViewById(R.id.textViewLocation);
        title = findViewById(R.id.textViewTitle);
        department = findViewById(R.id.textViewDepartment);
        instructor = findViewById(R.id.textViewInstructor);
        days = findViewById(R.id.textViewDays);
        cancelDrop = findViewById(R.id.buttonCancelD);
        cancelDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finalDrop = findViewById(R.id.buttonFinalDrop);
        finalDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropOrRemove(retrievedRedID,retrievedPassword,courseid);
            }
        });

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
            isEnrolled = extras.getBoolean("enrolled");
            if(isEnrolled) {
                finalDrop.setText(R.string.drop);
            }
            else {
                finalDrop.setText(R.string.remove);
            }
        }

    }

    public void dropOrRemove(String redId,String password,int courseid) {
        String dropOrRemoveURL;
        if(isEnrolled) {
            dropOrRemoveURL = "https://bismarck.sdsu.edu/registration/unregisterclass?redid=";
            dropOrRemoveURL = dropOrRemoveURL+redId+"&password="+password+"&courseid="+courseid;
        }
        else {
            dropOrRemoveURL = "https://bismarck.sdsu.edu/registration/unwaitlistclass?redid=";
            dropOrRemoveURL = dropOrRemoveURL+redId+"&password="+password+"&courseid="+courseid;
        }
        JsonObjectRequest dropOrRemoveRequest = new JsonObjectRequest(dropOrRemoveURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("ok")) {
                                Toast.makeText(Drop.this, response.get("ok").toString(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else if (response.has("error")) {
                                Toast.makeText(Drop.this, response.get("error").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Drop.this,error.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("hs", error.toString());
                    }
                }
        );
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(dropOrRemoveRequest);
    }
}
