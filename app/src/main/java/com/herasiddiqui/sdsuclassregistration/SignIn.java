package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class SignIn extends AppCompatActivity {

    EditText signInRedId;
    EditText signInPassword;
    Button signInButton;
    TextView noAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInRedId = findViewById(R.id.signInRedId);
        signInPassword = findViewById(R.id.signInPassword);
        signInButton = findViewById(R.id.signInButton);
        noAccount = findViewById(R.id.noAccountTextView);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void signInClicked(View button) {
        hideKeyboard();
        if(signInRedId.getText().toString().equals("")) {
            Toast.makeText(this,"Enter your SDSU RedId",Toast.LENGTH_SHORT).show();
        }
        else if(signInPassword.getText().toString().equals("")) {
            Toast.makeText(this,"Enter your Password",Toast.LENGTH_SHORT).show();
        }
        else {
            String signInUserURL = "https://bismarck.sdsu.edu/registration/studentclasses?redid=";
            signInUserURL = signInUserURL+signInRedId.getText().toString()+"&password="+signInPassword.getText().toString();

            Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    if (!response.has("error")){
                        //Toast.makeText(SignIn.this, response.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("hs", response.toString());
                    SharedPreferences sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("RedID", signInRedId.getText().toString());
                    editor.putString("password", signInPassword.getText().toString());
                    editor.commit();
                    //go to next intent
                    Intent go = new Intent(SignIn.this, ClassesAndWaitlist.class);
                    go.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(go);
                }
             }
            };
            Response.ErrorListener failure = new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignIn.this,error.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("hs", error.toString());
                }
            };
            JsonObjectRequest getRequest = new JsonObjectRequest(signInUserURL,null, success, failure);
            VolleyQueue.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
