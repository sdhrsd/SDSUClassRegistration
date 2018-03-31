package com.herasiddiqui.sdsuclassregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText firstName;
    EditText lastName;
    EditText redId;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        redId = findViewById(R.id.reIdEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
    }
}
