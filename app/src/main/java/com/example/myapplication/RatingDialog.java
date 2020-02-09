package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RatingDialog extends AppCompatActivity {

    Button submitr;
    RatingBar ratingBar;
    TextView comment,ratingdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_dialog);

        ratingBar = (RatingBar) findViewById(R.id.ratingbar);
        submitr = (Button) findViewById(R.id.submitr);
        comment = (TextView) findViewById(R.id.comment);
        ratingdesc = (TextView) findViewById(R.id.ratingdesc);

        submitr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RatingDialog.this,ClientHomeService.class));
            }
        });


    }
}
