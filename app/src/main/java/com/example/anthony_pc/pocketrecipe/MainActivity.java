package com.example.anthony_pc.pocketrecipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.crashlytics.android.answers.Answers;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Answers());
        setContentView(R.layout.activity_main);
    }
}
