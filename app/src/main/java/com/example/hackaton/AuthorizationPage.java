package com.example.hackaton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hackaton.R;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationPage extends AppCompatActivity {
    static final String AUTHORIZATION_NUM_GROUP = "AUTHORIZATION_NUM_GROUP";
    static final String AUTHORIZATION_LOGIN = "AUTHORIZATION_LOGIN";
    static final String AUTHORIZATION_PASSWORD = "AUTHORIZATION_PASSWORD";


    EditText authoNumGroup;
    EditText authoLogin;
    EditText authoPassword;
    Button btnAuthorization;
    Button btnRegistration;

//    String[] masNumGroup = {"B721", "B722", "B723"};
//    String[] mas_login = {"asd1", "asd2", "asd3"};
//    String[] mas_password = {"pas1", "pas2", "pas3"};
//    String[] login_array = {"Admin"};
//    int[] password_array = {1234};
//    String[] group_number_array = {"Admin"};
    List<String> login_list = new ArrayList<String>();
    List<Integer> password_list = new ArrayList<Integer>();
    List<String> group_number_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authorization_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        authoNumGroup = findViewById(R.id.authoNumGroup);
        authoLogin = findViewById(R.id.authoLogin);
        authoPassword = findViewById(R.id.authoPassword);
        btnAuthorization = findViewById(R.id.btnAuthorization);
        btnRegistration = findViewById(R.id.btnRegistration);

        btnRegistration.setOnClickListener(v->{
            Intent int_new_act = new Intent(this, MainActivity.class);
            startActivity(int_new_act);
        });

        btnAuthorization.setOnClickListener((view)->onClickBtn());
    }

    //проверка номера группы, логина и пароля пользователя
    public void onClickBtn(){
        String strAuthoNumGroup = authoNumGroup.getText().toString();
        String strAuthoLogin = authoLogin.getText().toString();
        String strAuthoPassword = authoPassword.getText().toString();
        if(strAuthoNumGroup.isEmpty() || strAuthoLogin.isEmpty() || strAuthoPassword.isEmpty()){
            Toast.makeText(this, "Заполните пустые поля!", Toast.LENGTH_SHORT).show();
        }else {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AuthorizationPage.this);
            myDB.getData(login_list,password_list,group_number_list);
            //Logs
            Log.d("LISTT", login_list.toString());
            Log.d("LISTT", password_list.toString());
            Log.d("LISTT", group_number_list.toString());
            Log.d("LISTT", group_number_list.get(0));

            for (int i = 0; i < login_list.toArray().length; i++) {
                if (strAuthoNumGroup.equals(group_number_list.get(i)) && strAuthoLogin.equals(login_list.get(i)) && strAuthoPassword.equals(password_list.get(i).toString())) {
                    Intent intent = new Intent(this, SecondPage.class);
                    startActivity(intent);
                    break;
                } else{
                    if(i == (login_list.toArray().length-1))
                        Toast.makeText(this, "Логин или пароль неверный!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}