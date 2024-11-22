package com.example.hackaton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText event_name_input, event_interest_input, event_category_input,event_date_input;
    Button add_button,back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        event_name_input = findViewById(R.id.event_name_input);
        event_interest_input = findViewById(R.id.event_interest_input);
        event_category_input = findViewById(R.id.event_category_input);
        event_date_input = findViewById(R.id.event_date_input);
        add_button = findViewById(R.id.add_button);
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondPage.class);
            startActivity(intent);
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addEvent(event_name_input.getText().toString().trim(),
                        event_interest_input.getText().toString().trim(),
                        event_category_input.getText().toString().trim(),
                        event_date_input.getText().toString().trim() );
            }
        });
    }
}
