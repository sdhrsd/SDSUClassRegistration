package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class Filters extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner subjectSpinner;
    Spinner levelSpinner;
    Spinner startTimeSpinner;
    Spinner endTimeSpinner;

    int subjectSelectedId;
    String levelSelected = "";
    String startTimeSelected = "";
    String endTimeSelected = "";

    String[] subjectList;
    String[] levelList;
    String[] startTimeList;
    String[] endTimeList;

    Button search;

    ArrayList<Subject> allSubjectArrayList = new ArrayList<>();  // ArrayList for storing all Subject Details
    String subjectListURL = "https://bismarck.sdsu.edu/registration/subjectlist";

    RecyclerView searchRecyclerView;
    private ArrayList<ClassDetails> addedClassDetails = new ArrayList<>();
    SearchRecycleViewAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        search = findViewById(R.id.buttonSearch);
        searchRecyclerView = findViewById(R.id.searchRecyclerView);
        RecyclerView.LayoutManager searchLayoutManager = new LinearLayoutManager(this);
        searchRecyclerView.setLayoutManager(searchLayoutManager);

        subjectSpinner = findViewById(R.id.spinnerForSubject);
        subjectSpinner.setOnItemSelectedListener(this);

        levelSpinner = findViewById(R.id.spinnerForLevel);
        levelSpinner.setOnItemSelectedListener(this);

        startTimeSpinner = findViewById(R.id.spinnerForStartTime);
        startTimeSpinner.setOnItemSelectedListener(this);

        endTimeSpinner = findViewById(R.id.spinnerForEndTime);
        endTimeSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this, R.array.level_list, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(levelAdapter);

        ArrayAdapter<CharSequence> startTimeAdapter = ArrayAdapter.createFromResource(this, R.array.start_time_list, android.R.layout.simple_spinner_item);
        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTimeSpinner.setAdapter(startTimeAdapter);

        ArrayAdapter<CharSequence> endTimeAdapter = ArrayAdapter.createFromResource(this, R.array.end_time_list, android.R.layout.simple_spinner_item);
        endTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTimeSpinner.setAdapter(endTimeAdapter);

        levelList = getResources().getStringArray(R.array.level_list);
        startTimeList = getResources().getStringArray(R.array.start_time_list);
        endTimeList = getResources().getStringArray(R.array.end_time_list);

        getListOfSubjects();
        searchAdapter = new SearchRecycleViewAdapter(Filters.this,addedClassDetails);
        searchRecyclerView.setAdapter(searchAdapter);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchResults();
            }
        });

    }

    protected void onResume(){
        super.onResume();

    }

    public void getListOfSubjects(){
        JsonArrayRequest getListOfSubjectsRequest = new JsonArrayRequest(subjectListURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(response.length() != 0){
                                subjectList = new String[response.length()];
                                for(int i = 0;i<response.length();i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Subject subject = new Subject();
                                    subject.setTitle(jsonObject.getString("title"));
                                    subject.setId(jsonObject.getInt("id"));
                                    subject.setCollege(jsonObject.getString("college"));
                                    subject.setClasses(jsonObject.getInt("classes"));
                                    subject.setSelected(false);
                                    allSubjectArrayList.add(subject);
                                    subjectList[i] = jsonObject.getString("title");
                                }
                                ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(Filters.this,android.R.layout.simple_spinner_item,subjectList);
                                subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                subjectSpinner.setAdapter(subjectAdapter);
                            }

                        }catch(JSONException e) {
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
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(getListOfSubjectsRequest);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.spinnerForSubject:
                    levelSelected = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(Filters.this, levelSelected, Toast.LENGTH_SHORT).show();
                    for(Subject subject: allSubjectArrayList) {
                        if(subject.getTitle().equals(levelSelected)) {
                            subjectSelectedId = subject.getId();
                            break;
                        }
                    }

                break;
            case R.id.spinnerForLevel:
                if(position != 0) {
                    levelSelected = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(Filters.this, levelSelected, Toast.LENGTH_SHORT).show();
                }
                else {
                    levelSelected = "";
                }
                break;
            case R.id.spinnerForStartTime:
                if(position != 0) {
                    startTimeSelected = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(Filters.this, startTimeSelected, Toast.LENGTH_SHORT).show();
                }
                else {
                    startTimeSelected = "";
                }
                break;
            case R.id.spinnerForEndTime:
                if(position != 0) {
                    endTimeSelected = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(Filters.this, endTimeSelected, Toast.LENGTH_SHORT).show();
                }
                else {
                    endTimeSelected = "";
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getSearchResults(){
        addedClassDetails.clear();
        searchAdapter.notifyDataSetChanged();
        String searchURL = "https://bismarck.sdsu.edu/registration/classidslist?subjectid=";
        if(subjectSelectedId != 0) {
            searchURL = searchURL + subjectSelectedId + "&level="+levelSelected +"&starttime="+startTimeSelected+"&endtime="+endTimeSelected;
        }

        final JsonArrayRequest getcourseIdsRequest = new JsonArrayRequest(searchURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(response.length() != 0) {
                                for(int i=0;i<response.length();i++) {
                                    String courseDetailURL = "https://bismarck.sdsu.edu/registration/classdetails?classid=";
                                    courseDetailURL = courseDetailURL + response.getInt(i);
                                    JsonObjectRequest getCourseDetailRequest = new JsonObjectRequest(courseDetailURL,null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        ClassDetails putInAddedClasses = new ClassDetails();
                                                        if(response.has("description")) {
                                                            putInAddedClasses.setDescription(response.getString("description"));
                                                        } else {
                                                            putInAddedClasses.setDescription("");
                                                        }
                                                        putInAddedClasses.setDepartment(response.getString("department"));
                                                        putInAddedClasses.setSuffix(response.getString("suffix"));
                                                        putInAddedClasses.setBuilding(response.getString("building"));
                                                        putInAddedClasses.setStartTime(response.getString("startTime"));
                                                        putInAddedClasses.setMeetingType(response.getString("meetingType"));
                                                        putInAddedClasses.setSection(response.getString("section"));
                                                        putInAddedClasses.setEndTime(response.getString("endTime"));
                                                        putInAddedClasses.setEnrolled(response.getInt("enrolled"));
                                                        putInAddedClasses.setDays(response.getString("days"));
                                                        if(response.has("prerequisite")) {
                                                            putInAddedClasses.setPrerequisite(response.getString("prerequisite"));
                                                        } else {
                                                            putInAddedClasses.setPrerequisite("");
                                                        }
                                                        putInAddedClasses.setTitle(response.getString("title"));
                                                        putInAddedClasses.setId(response.getInt("id"));
                                                        putInAddedClasses.setInstructor(response.getString("instructor"));
                                                        putInAddedClasses.setScheduleNo(response.getString("schedule#"));
                                                        putInAddedClasses.setUnits(response.getString("units"));
                                                        putInAddedClasses.setRoom(response.getString("room"));
                                                        putInAddedClasses.setWaitlist(response.getInt("waitlist"));
                                                        putInAddedClasses.setSeats(response.getInt("seats"));
                                                        if(response.has("fullTitle")) {
                                                            putInAddedClasses.setFullTitle(response.getString("fullTitle"));
                                                        } else {
                                                            putInAddedClasses.setFullTitle("");
                                                        }
                                                        putInAddedClasses.setSubject(response.getString("subject"));
                                                        putInAddedClasses.setCourseNo(response.getString("course#"));
                                                        putInAddedClasses.setWaitlist(response.getInt("waitlist"));
                                                        //putInAddedClasses.setStudentEnrolled(true);
                                                        addedClassDetails.add(putInAddedClasses);
                                                        searchAdapter.notifyDataSetChanged();
                                                        //Toast.makeText(Filters.this,"Course detail " + response.get("description"),Toast.LENGTH_SHORT).show();
                                                    }catch(JSONException e) {
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
                                    VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(getCourseDetailRequest);
                                }
                            }
                            }catch(JSONException e) {
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
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(getcourseIdsRequest);
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
