package com.example.unknown.firebasechatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChatRoom extends AppCompatActivity {


    Button chat;
    EditText message;
    RecyclerView listMessages;

    String user_name,room_name;

    DatabaseReference child;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        user_name = getIntent().getExtras().getString("user_name");
        room_name = getIntent().getExtras().getString("room_name");

        reference = FirebaseDatabase.getInstance().getReference().child("sem_1").child("ec");

        message = (EditText)findViewById(R.id.editText2);
        chat = (Button)findViewById(R.id.button2);
        listMessages = (RecyclerView) findViewById(R.id.textView);

        listMessages.setHasFixedSize(true);
        listMessages.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        setTitle(room_name);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(message.getText().toString() == null){
                    return;
                }

               // Chats chats = new Chats();
               // String key = reference.push().getKey();

                //chats.setName(user_name);
                //chats.setMessage(message.getText().toString());
                //reference.push().setValue(chats);

                Map<String,Object> map = new HashMap<String, Object>();
                map.put(message.getText().toString(),"");
                reference.updateChildren(map);

                View view = ChatRoom.this.getCurrentFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(ChatRoom.this.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                message.setText("");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Chats,ViewHold> adapter = new FirebaseRecyclerAdapter<Chats, ViewHold>(
                Chats.class,
                android.R.layout.two_line_list_item,
                ViewHold.class,
                reference
        ) {
            @Override
            protected void populateViewHolder(ViewHold viewHolder, Chats model, int position) {
                viewHolder.text.setText(model.getName() + ": " + model.getMessage());
                Toast.makeText(ChatRoom.this,"Inside Populate",Toast.LENGTH_SHORT).show();
            }
        };

       // listMessages.setAdapter(adapter);

    }

    public static class ViewHold extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHold(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }

    }

}
