package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Waitlist extends AppCompatActivity {

    private Button goToMyClasses;
    private Button resetAllClasses;
    private Button addClass;
    private RecyclerView waitlistRecyclerView;
    private ArrayList<ClassDetails> waitlistClassDetails = new ArrayList<>();
    public static final String PREFS_DEFAULT = "";
    SharedPreferences sharedPref;
    String retrievedRedID;
    String retrievedPassword;
    ClassAndWaitRecyclerViewAdapter secondAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitlist);
        goToMyClasses = findViewById(R.id.goToMyClasses);
        resetAllClasses = findViewById(R.id.resetButtonW);
        resetAllClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStudent();
            }
        });
        goToMyClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addClass = findViewById(R.id.addClassButtonW);
        waitlistRecyclerView = findViewById(R.id.recyclerWaitlist);
        RecyclerView.LayoutManager waitlistLayoutManager = new LinearLayoutManager(this);
        waitlistRecyclerView.setLayoutManager(waitlistLayoutManager);
        secondAdapter = new ClassAndWaitRecyclerViewAdapter(Waitlist.this,waitlistClassDetails);
        waitlistRecyclerView.setAdapter(secondAdapter);

        sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        retrievedRedID = sharedPref.getString("RedID",PREFS_DEFAULT);
        retrievedPassword = sharedPref.getString("password",PREFS_DEFAULT);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Waitlist.this,Filters.class);
                startActivity(intent);
            }
        });
        //getWaitlistedClasses(retrievedRedID,retrievedPassword);
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

    public void getWaitlistedClasses(String redId,String password) {
        waitlistClassDetails.clear();
        secondAdapter.notifyDataSetChanged();
        String waitlistURL = "https://bismarck.sdsu.edu/registration/studentclasses?redid=";
        waitlistURL = waitlistURL+redId+"&password="+password;
        JsonObjectRequest getRequest = new JsonObjectRequest(waitlistURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(Waitlist.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("hs", response.toString());
                        try {
                            final JSONArray waitListClasses = response.optJSONArray("waitlist");
                            if(waitListClasses.length() != 0) {
                                for(int i=0;i<waitListClasses.length();i++) {
                                    String courseidURL = "https://bismarck.sdsu.edu/registration/classdetails?classid=" + waitListClasses.getInt(i);
                                    JsonObjectRequest detailRequest = new JsonObjectRequest(courseidURL, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    responseHelper(response);
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(Waitlist.this,error.toString(),Toast.LENGTH_SHORT).show();
                                                    Log.d("hs", error.toString());

                                                }
                                            }
                                    );
                                    VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(detailRequest);
                                }
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Waitlist.this,error.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("hs", error.toString());

                }
        });
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    public void responseHelper(JSONObject response) {
        try {
            ClassDetails putInAddedClasses = new ClassDetails();
            putInAddedClasses.setDescription(response.getString("description"));
            putInAddedClasses.setDepartment(response.getString("department"));
            putInAddedClasses.setSuffix(response.getString("suffix"));
            putInAddedClasses.setBuilding(response.getString("building"));
            putInAddedClasses.setStartTime(response.getString("startTime"));
            putInAddedClasses.setMeetingType(response.getString("meetingType"));
            putInAddedClasses.setSection(response.getString("section"));
            putInAddedClasses.setEndTime(response.getString("endTime"));
            putInAddedClasses.setEnrolled(response.getInt("enrolled"));
            putInAddedClasses.setDays(response.getString("days"));
            putInAddedClasses.setPrerequisite(response.getString("prerequisite"));
            putInAddedClasses.setTitle(response.getString("title"));
            putInAddedClasses.setId(response.getInt("id"));
            putInAddedClasses.setInstructor(response.getString("instructor"));
            putInAddedClasses.setScheduleNo(response.getString("schedule#"));
            putInAddedClasses.setUnits(response.getString("units"));
            putInAddedClasses.setRoom(response.getString("room"));
            putInAddedClasses.setWaitlist(response.getInt("waitlist"));
            putInAddedClasses.setSeats(response.getInt("seats"));
            putInAddedClasses.setFullTitle(response.getString("fullTitle"));
            putInAddedClasses.setSubject(response.getString("subject"));
            putInAddedClasses.setCourseNo(response.getString("course#"));
            waitlistClassDetails.add(putInAddedClasses);
            secondAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void resetStudent() {
        Toast.makeText(Waitlist.this, "Reset Clicked", Toast.LENGTH_SHORT).show();
        String resetURL = "https://bismarck.sdsu.edu/registration/resetstudent?redid="+retrievedRedID+"&password="+retrievedPassword;
        JsonObjectRequest resetRequest = new JsonObjectRequest(resetURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("ok")) {
                                Toast.makeText(Waitlist.this, response.get("ok").toString(), Toast.LENGTH_SHORT).show();
                                waitlistClassDetails.clear();
                                secondAdapter.notifyDataSetChanged();
                                getWaitlistedClasses(retrievedRedID,retrievedPassword);
                            }
                            else if (response.has("error")) {
                                Toast.makeText(Waitlist.this, response.get("error").toString(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(resetRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        waitlistClassDetails.clear();
        secondAdapter.notifyDataSetChanged();
        getWaitlistedClasses(retrievedRedID,retrievedPassword);
    }
}
