package com.herasiddiqui.sdsuclassregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CompleteClassDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_class_details);
        Bundle extras = getIntent().getExtras();
        int value;
        boolean isEnrolled;
        if (extras != null) {
            value = extras.getInt("courseId");
            isEnrolled = extras.getBoolean("enrolled");
            System.out.println("EXTRA IS " + value);
            Toast.makeText(this,"Value is " + isEnrolled,Toast.LENGTH_LONG).show();
        }
    }
}
