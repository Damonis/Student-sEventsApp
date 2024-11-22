package com.example.hackaton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    static final String REGISTRATION_NAME = "REGISTRATION_NAME";
    static final String REGISTRATION_LOGIN = "REGISTRATION_LOGIN";
    static final String REGISTRATION_PASSWORD = "REGISTRATION_PASSWORD";
    static final String REGISTRATION_NUM_GROUP = "REGISTRATION_NUM_GROUP";

    private String[] interests = {"Science","Art","Music","Volunteering","IT"};

    EditText name;
    EditText login;
    EditText password;
    EditText numGroup;
    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayAdapter<String> interestsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, interests);

        interestsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spInterests = (Spinner) findViewById(R.id.activitiesSpinner);
        spInterests.setAdapter(interestsAdapter);

        name = findViewById(R.id.editTextName);
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        numGroup = findViewById(R.id.editTextNumGroup);
        nextPage = findViewById(R.id.nextPage);

        nextPage.setOnClickListener(v->{
            String strName = name.getText().toString();
            String strLogin = login.getText().toString();
            String strPassword = password.getText().toString();
            String strNumGroup = numGroup.getText().toString();
            String strInterests = spInterests.getSelectedItem().toString();

            if(strName.isEmpty() || strLogin.isEmpty() || strPassword.isEmpty() || strNumGroup.isEmpty()){
                Toast.makeText(this, "Заполните пустые поля!", Toast.LENGTH_SHORT).show();
            }else{
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.addUser(strName.trim(),
                        strLogin.trim(),
                        Integer.valueOf(strPassword.trim()),
                        strNumGroup.trim(),
                        strInterests.trim());

                Intent intent = new Intent(this, SecondPage.class);
                intent.putExtra(REGISTRATION_NAME, strName);
                intent.putExtra(REGISTRATION_LOGIN, strLogin);
                intent.putExtra(REGISTRATION_PASSWORD, strPassword);
                intent.putExtra(REGISTRATION_NUM_GROUP, strNumGroup);
                startActivity(intent);
            }

        });

    }
}