package com.shaswat.kumar.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shaswat.kumar.mynotepad.Model.Data;

public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FloatingActionButton fabBtn;


    //Firebase

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        toolbar = findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NoteActivity");

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Note").child(uid);
        mDatabase.keepSynced(true);


        mRecyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        fabBtn = findViewById(R.id.float_btn);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),AddNoteActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Data,MyViewHolder>recyclerAdapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>
                (
                Data.class,
                R.layout.item_data,
                NoteActivity.MyViewHolder.class,
                mDatabase
                )
        {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, final Data model, int i) {

           //check for error here in place of i , position should come
                final String post_key = getRef(i).getKey();


                myViewHolder.setTitle(model.getTitle());
                myViewHolder.setDescription(model.getDescription());
                myViewHolder.setDate(model.getDate());


               myViewHolder.myview.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent =new Intent(getApplicationContext(),DetailsActivity.class);
                       intent.putExtra("key",post_key);
                       intent.putExtra("date",model.getDate());
                       intent.putExtra("title",model.getTitle());
                       intent.putExtra("description",model.getDescription());


                       startActivity(intent);
                       overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                   }
               });


            }
        };

        mRecyclerView.setAdapter(recyclerAdapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public MyViewHolder(View itemView){
            super(itemView);
            myview = itemView;
        }

        public void setTitle(String title){

            TextView mTitle = myview.findViewById(R.id.title_xml);
            mTitle.setText(title);
        }


        public void setDescription(String description){

            TextView mDescription = myview.findViewById(R.id.description_item);
            mDescription.setText(description);
        }

        public void setDate(String date){

            TextView mDate = myview.findViewById(R.id.date_xml);
            mDate.setText(date);
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
