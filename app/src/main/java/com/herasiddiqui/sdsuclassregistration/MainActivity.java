package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText redId;
    EditText email;
    EditText password;
    TextView alreadyRegistered;
    Button registerUser;

    public static String addUserURL = "https://bismarck.sdsu.edu/registration/addstudent";
    public static final String PREFS_DEFAULT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String retrievedRedID = sharedPref.getString("RedID",PREFS_DEFAULT);
        String retrievedPassword = sharedPref.getString("password",PREFS_DEFAULT);

        if(!retrievedRedID.equals("") && !retrievedPassword.equals("")) {
            Intent go = new Intent(MainActivity.this,ClassesAndWaitlist.class);
            startActivity(go);
            finish();
        }

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        redId = findViewById(R.id.reIdEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        registerUser = findViewById(R.id.registerUserButton);
        alreadyRegistered = findViewById(R.id.signInTextView);

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(MainActivity.this,SignIn.class);
                startActivity(go);
            }
        });
    }

    public void registerUser(View button) {
        if (firstName.getText().toString().equals("")) {
            Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
        } else if (lastName.getText().toString().equals("")) {
            Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
        } else if (redId.getText().toString().equals("")) {
            Toast.makeText(this, "Enter your SDSU RedID", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().equals("")) {
            Toast.makeText(this, "Enter your Email address", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("") || password.getText().length() < 8) {
            Toast.makeText(this, "Enter your Password", Toast.LENGTH_SHORT).show();
        } else {
            addStudent();
        }
    }

    public void addStudent() {
        JSONObject data = new JSONObject();
        try {
            data.put("firstname", firstName.getText().toString());
            data.put("lastname",lastName.getText().toString());
            data.put("redid",redId.getText().toString());
            data.put("password",password.getText().toString());
            data.put("email",email.getText().toString());
        }
        catch (JSONException error) {
            Log.e("ERROR1", "JSON error occurred ", error);
            return;
        }
        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                getResponse(response);
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("hs_error", "post fail " + new String(error.networkResponse.data));
            }
        };

        JsonObjectRequest postRequest = new JsonObjectRequest(addUserURL,data,success,failure);
        VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }

    public void getResponse(JSONObject response){
        try {
            Log.i("TAG", response.toString()+ "this is the response ");
            //Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
            String objectString = response.toString();
            JSONObject jsonReturned = new JSONObject(objectString);
            if(jsonReturned.has("ok")){
                Toast.makeText(this, jsonReturned.get("ok").toString(), Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("FirstName", firstName.getText().toString());
                editor.putString("LastName", lastName.getText().toString());
                editor.putString("RedID", redId.getText().toString());
                editor.putString("password", password.getText().toString());
                editor.putString("Email", email.getText().toString());
                editor.commit();
                Toast.makeText(MainActivity.this, " USER Information Saved!", Toast.LENGTH_SHORT).show();

                // write code for next intent
                Intent go = new Intent(MainActivity.this,ClassesAndWaitlist.class);
                startActivity(go);
                finish();
            }
            else if(jsonReturned.has("error")) {
                Toast.makeText(this, jsonReturned.get("error").toString(), Toast.LENGTH_SHORT).show();

            }
        }
        catch (JSONException error) {
            Log.e("TAG", "JSON error occurred", error);
            return;
        }
    }
}