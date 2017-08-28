package com.example.unknown.findmyinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unknown.findmyinfo.Papers.PapersActivity;
import com.example.unknown.findmyinfo.Syllabus.SyllabusActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv_subjects;

    String btn_clicked,selected_field,selected_sem;

    boolean IsNotPapers = true;

    SharedPreferences preferences;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("paper");
    DatabaseReference semester_to_field_to_sub_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Bundle bundle = getIntent().getExtras();

        btn_clicked = bundle.getString("BTN_CLICKED",null);
        selected_field = preferences.getString("field_pref",null);
        selected_sem = preferences.getString("semester_pref",null);

        lv_subjects = (ListView) findViewById(R.id.lv_subjects);

        lv_subjects.setOnItemClickListener(this);

        semester_to_field_to_sub_name = root.child(selected_sem).child(selected_field).child("sub_name");

        getSupportActionBar().setTitle(selected_field.toUpperCase());
    }



    @Override
    protected void onStart() {
        super.onStart();

        Load();

        FirebaseListAdapter<String> adapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                semester_to_field_to_sub_name
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

        lv_subjects.setAdapter(adapter);

        if(btn_clicked.contains("papers")){
            IsNotPapers = false;
        } else if(btn_clicked.contains("syllabus")) {
            IsNotPapers = true;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void Load() {
        // TODO Auto-generated method stub

        final DialohHandler handler = new DialohHandler(SubjectActivity.this);

        new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String sub_name = lv_subjects.getItemAtPosition(position).toString();

        Bundle bundle = new Bundle();
        bundle.putString("SUB_NAME",sub_name.toLowerCase());
        bundle.putString("SELECTED_FIELD",selected_field);
        bundle.putString("SELECTED_SEM",selected_sem);

        Intent intent;

        if(IsNotPapers){
            intent = new Intent(SubjectActivity.this,SyllabusActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            intent = new Intent(SubjectActivity.this,PapersActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }

    }
}
