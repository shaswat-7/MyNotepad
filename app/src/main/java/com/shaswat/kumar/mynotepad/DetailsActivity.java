package com.shaswat.kumar.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {


    private Toolbar toolbar;

    private TextView date;
    private TextView title;
    private TextView description;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    private String post_key;

    private String mdate;
    private String mtitle;
    private String mdescription;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = findViewById(R.id.details_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");


        date = findViewById(R.id.date_details);

        title = findViewById(R.id.title_details);

        description = findViewById(R.id.description_details);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Note").child(uid);


        Intent intent = getIntent();

         mdate = intent.getStringExtra("date");
         mtitle = intent.getStringExtra("title");
         mdescription = intent.getStringExtra("description");

        post_key = intent.getStringExtra("key");


        date.setText(mdate);
        title.setText(mtitle);
        description.setText(mdescription);

//        editNote();



    }

     private void editNote(){

        Intent intent = new Intent(getApplicationContext(),EditActivity.class);
        intent.putExtra("title",mtitle);
        intent.putExtra("description",mdescription);
        intent.putExtra("key",post_key);


        startActivity(intent);


     }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),NoteActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.delete://after  pressing delete button
                mDatabase.child(post_key).removeValue();
                Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),NoteActivity.class));

                break;

            case R.id.note_edit:

                editNote();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
