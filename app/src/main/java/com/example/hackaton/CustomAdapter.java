package com.example.hackaton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

        private Context context;
        private Activity activity;
        private ArrayList event_id, event_name, event_interest, event_category, event_date;


        CustomAdapter(Activity activity, Context context, ArrayList event_id, ArrayList event_name,ArrayList event_interest,ArrayList event_category,
                      ArrayList event_date){
            this.activity = activity;
            this.context = context;
            this.event_id = event_id;
            this.event_name = event_name;
            this.event_interest = event_interest;
            this.event_category = event_category;
            this.event_date = event_date;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_row, parent, false);
            return new MyViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            holder.event_id_txt.setText(String.valueOf(event_id.get(position)));
            holder.event_name_txt.setText(String.valueOf(event_name.get(position)));
            holder.event_interest_txt.setText(String.valueOf(event_interest.get(position)));
            holder.event_category_txt.setText(String.valueOf(event_category.get(position)));
            holder.event_date_txt.setText(String.valueOf(event_date.get(position)));

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CustomAdapter.class);
                    intent.putExtra("id", String.valueOf(event_id.get(position)));
                    intent.putExtra("eventName", String.valueOf(event_name.get(position)));
                    intent.putExtra("interest", String.valueOf(event_interest.get(position)));
                    intent.putExtra("category", String.valueOf(event_category.get(position)));
                    activity.startActivityForResult(intent, 1);
                }
            });


        }

        @Override
        public int getItemCount() {
            return event_id.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView event_id_txt, event_name_txt, event_interest_txt,event_category_txt,event_date_txt;
            LinearLayout mainLayout;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                event_id_txt = itemView.findViewById(R.id.event_id_txt);
                event_name_txt = itemView.findViewById(R.id.event_name_txt);
                event_interest_txt = itemView.findViewById(R.id.event_interest_txt);
                event_category_txt = itemView.findViewById(R.id.event_category_txt);
                event_date_txt = itemView.findViewById(R.id.event_date_txt);
                mainLayout = itemView.findViewById(R.id.mainLayout);
                //Animate Recyclerview
                Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
                mainLayout.setAnimation(translate_anim);
            }

        }
}
