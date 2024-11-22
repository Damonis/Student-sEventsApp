package com.example.hackaton;


import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SecondPage extends AppCompatActivity {

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    TextView nameUser;
    TextView numGroup;

    RecyclerView recyclerView;
    Button btn; //тестовая кнопка для отправки уведомлений
    MyDatabaseHelper myDB;
    ArrayList<String> event_id, event_name, event_interest, event_category,event_date;
    CustomAdapter customAdapter;
    FloatingActionButton add_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameUser = findViewById(R.id.nameUser); //имя студента
        numGroup = findViewById(R.id.numGroup); //номер группы

        recyclerView = findViewById(R.id.recycler);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondPage.this, AddActivity.class);
                startActivity(intent);
            }
        });


        myDB = new MyDatabaseHelper(SecondPage.this);
        event_id = new ArrayList<>();
        event_name = new ArrayList<>();
        event_interest = new ArrayList<>();
        event_category = new ArrayList<>();
        event_date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(SecondPage.this, this, event_id, event_name, event_interest, event_category,
                event_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SecondPage.this));
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 1){
                recreate();
            }
        }


    void storeDataInArrays(){
        Cursor cursor = myDB.readAllDataFromEvents();
            while (cursor.moveToNext()){
                event_id.add(cursor.getString(0));
                event_name.add(cursor.getString(1));
                event_interest.add(cursor.getString(2));
                event_category.add(cursor.getString(3));
                event_date.add(cursor.getString(4));
            }
        }
}


//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);//
//
//
////        name = findViewById(R.id.name);
////        login = findViewById(R.id.login);
////        password = findViewById(R.id.password);
////        group = findViewById(R.id.group);
////
////        btn = findViewById(R.id.button);
////
////
////        Intent intentName = getIntent();
////        getName = intentName.getStringExtra(MainActivity.REGISTRATION_NAME);
////
////        Intent intentLogin = getIntent();
////        getLogin = intentLogin.getStringExtra(MainActivity.REGISTRATION_LOGIN);
////
////        Intent intentPassword = getIntent();
////        getPassword = intentPassword.getStringExtra(MainActivity.REGISTRATION_PASSWORD);
////
////        Intent intentGroup = getIntent();
////        getGroup = intentGroup.getStringExtra(MainActivity.REGISTRATION_NUM_GROUP);
////
////        name.setText(getName);
////        login.setText(getLogin);
////        password.setText(getPassword);
////        group.setText(getGroup);
//
//
//        btn.setOnClickListener((view)->setNotification());
//
//    }
//
//    // кнопка для запуска уведомления (тест)
//    public void setNotification(){
//        Intent intent = new Intent(getApplicationContext(), SecondPage.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                        .setAutoCancel(false)
//                        .setSmallIcon(R.drawable.ic_launcher_foreground) //иконка на панели уведомлений
//                        .setContentIntent(pendingIntent) // текущее время в мс
//                        .setContentTitle("БУ!!! Испугался??") // заголовок уведомления
//                        .setContentText("Не бойся, я друг, я тебя не обижу!") // текст уведомления
//                        .setPriority(PRIORITY_HIGH); // приоритет уведомления
//        createChannelNeeded(notificationManager);
//        notificationManager.notify(NOTIFY_ID, notificationBuilder.build());
//    }
//
//    public static void createChannelNeeded(NotificationManager manager){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_DEFAULT);
//            manager.createNotificationChannel(notificationChannel);
//        }
//    }
