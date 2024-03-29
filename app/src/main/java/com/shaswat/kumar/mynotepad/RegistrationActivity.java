package com.shaswat.kumar.mynotepad;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailReg;
    private EditText passReg;
    private Button btnReg;
    private TextView signHere;

    //Firebase

    private FirebaseAuth mAuth;


    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);

    RegistrationFunction();
    }

    private void RegistrationFunction(){

        emailReg = findViewById(R.id.email_reg);
        passReg = findViewById(R.id.password_reg);
        btnReg = findViewById(R.id.btn_reg);
        signHere = findViewById(R.id.singin_reg);

        signHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailReg.getText().toString().trim();
                String pass = passReg.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailReg.setError("Required Field..");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    passReg.setError("Required Field..");
                    return;
                }

                mDialog.setMessage("Processing..");
                mDialog.show();

               mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           mDialog.dismiss();
                           startActivity(new Intent(getApplicationContext(),NoteActivity.class));
                           Toast.makeText(getApplicationContext(),"Registration Complete",Toast.LENGTH_SHORT).show();
                       }else{
                           mDialog.dismiss();
                           Toast.makeText(getApplicationContext(), "Registration Failed" , Toast.LENGTH_SHORT).show();
                       }

                   }
               });

            }
        });

    }
}
