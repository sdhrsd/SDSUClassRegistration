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

public class ClassesAndWaitlist extends AppCompatActivity {

    private Button goToMyWaitlist;
    private Button resetAllClasses;
    private Button addClass;
    public static final String PREFS_DEFAULT = "";
    private RecyclerView classesRecyclerView;
    private ArrayList<ClassDetails> addedClassDetails = new ArrayList<>();
    SharedPreferences sharedPref;
    String retrievedRedID;
    String retrievedPassword;
    ClassAndWaitRecyclerViewAdapter firstAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_and_waitlist);
        goToMyWaitlist = findViewById(R.id.goToMyWaitlist);
        goToMyWaitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ClassesAndWaitlist.this,Waitlist.class);
                startActivity(go);
            }
        });
        addClass = findViewById(R.id.addClassButtonC);
        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesAndWaitlist.this,Filters.class);
                startActivity(intent);
            }
        });
        resetAllClasses = findViewById(R.id.resetButtonC);
        resetAllClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStudent();
            }
        });
        addClass = findViewById(R.id.addClassButtonC);
        classesRecyclerView = findViewById(R.id.recyclerClasses);
        RecyclerView.LayoutManager classesLayoutManager = new LinearLayoutManager(this);
        classesRecyclerView.setLayoutManager(classesLayoutManager);
        firstAdapter = new ClassAndWaitRecyclerViewAdapter(ClassesAndWaitlist.this,addedClassDetails);
        classesRecyclerView.setAdapter(firstAdapter);

        sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        retrievedRedID = sharedPref.getString("RedID",PREFS_DEFAULT);
        retrievedPassword = sharedPref.getString("password",PREFS_DEFAULT);
        //getEnrolledClasses(retrievedRedID,retrievedPassword);
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

    public void getEnrolledClasses(String redId,String password) {
        addedClassDetails.clear();
        firstAdapter.notifyDataSetChanged();
        String enrolledURL = "https://bismarck.sdsu.edu/registration/studentclasses?redid=";
        enrolledURL = enrolledURL+redId+"&password="+password;
        JsonObjectRequest getRequest = new JsonObjectRequest(enrolledURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Toast.makeText(Waitlist.this,response.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("hs", response.toString());
                        try {
                            final JSONArray enrolledClasses = response.optJSONArray("classes");
                            if(enrolledClasses.length() != 0) {
                                for(int i=0;i<enrolledClasses.length();i++) {
                                    String courseidURL = "https://bismarck.sdsu.edu/registration/classdetails?classid=" + enrolledClasses.getInt(i);
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
                                                    Toast.makeText(ClassesAndWaitlist.this,error.toString(),Toast.LENGTH_SHORT).show();
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
            putInAddedClasses.setStudentEnrolled(true);
            addedClassDetails.add(putInAddedClasses);
            firstAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addedClassDetails.clear();
        firstAdapter.notifyDataSetChanged();
        getEnrolledClasses(retrievedRedID,retrievedPassword);
    }
    public void resetStudent() {
        Toast.makeText(ClassesAndWaitlist.this, "Reset Clicked", Toast.LENGTH_SHORT).show();
        String resetURL = "https://bismarck.sdsu.edu/registration/resetstudent?redid="+retrievedRedID+"&password="+retrievedPassword;
        JsonObjectRequest resetRequest = new JsonObjectRequest(resetURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("ok")) {
                                Toast.makeText(ClassesAndWaitlist.this, response.get("ok").toString(), Toast.LENGTH_SHORT).show();
                                addedClassDetails.clear();
                                firstAdapter.notifyDataSetChanged();
                                getEnrolledClasses(retrievedRedID,retrievedPassword);
                            }
                            else if (response.has("error")) {
                                Toast.makeText(ClassesAndWaitlist.this, response.get("error").toString(), Toast.LENGTH_SHORT).show();
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
}