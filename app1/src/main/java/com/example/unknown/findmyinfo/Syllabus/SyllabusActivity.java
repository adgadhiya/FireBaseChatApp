package com.example.unknown.findmyinfo.Syllabus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.unknown.findmyinfo.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SyllabusActivity extends AppCompatActivity {

    TextView first_sessional,second_sessional,third_sessional,reference_book;

    String sub_name,selected_field,selected_sem;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("paper");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SyllabusActivity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        sub_name = bundle.getString("SUB_NAME",null);
        selected_field = bundle.getString("SELECTED_FIELD",null);
        selected_sem = bundle.getString("SELECTED_SEM",null);

    }


    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference sub_name_ref = reference.child(selected_field).child(selected_field).child(sub_name);

        FirebaseListAdapter<Sub_Syllabus_Phases> adapter = new FirebaseListAdapter<Sub_Syllabus_Phases>(
                this,
                Sub_Syllabus_Phases.class,
                android.R.layout.simple_list_item_1,
                sub_name_ref
        ) {
            @Override
            protected void populateView(View v, Sub_Syllabus_Phases model, int position) {
                first_sessional.setText(model.getPhase_1());
                second_sessional.setText(model.getPhase_2());
                third_sessional.setText(model.getPhase_3());
                reference_book.setText(model.getReference());
            }
        };

    }
}
