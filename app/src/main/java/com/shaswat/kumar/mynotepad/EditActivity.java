package com.shaswat.kumar.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shaswat.kumar.mynotepad.Model.Data;

import java.text.DateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mdescription;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String title;
    private String desc;
    private String post_key;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar = findViewById(R.id.edt_note);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");

        mTitle = findViewById(R.id.edt_title);
        mdescription = findViewById(R.id.edt_description);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        String uid = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Note").child(uid);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("description");
        post_key = intent.getStringExtra("key");

        mTitle.setText(title);
        mTitle.setSelection(title.length());

        mdescription.setText(desc);
        mdescription.setSelection(desc.length());


    }

    private void saveEditNote(){

        title = mTitle.getText().toString().trim();
        desc = mdescription.getText().toString().trim();

        String date = DateFormat.getDateInstance().format(new Date());

        Data data = new Data(title,desc,date,post_key);

        mDatabase.child(post_key).setValue(data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), NoteActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.done_menu:
                saveEditNote();
                startActivity(new Intent(getApplicationContext(), NoteActivity.class));
                Toast.makeText(getApplicationContext(),"Note Edited",Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
