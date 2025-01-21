package com.wireless.charging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class  extends AppCompatActivity {

    TextView registernow;
    EditText email,password;login
//    FirebaseAuth firebaseAuth;
    String email2="",password2="";
    ProgressDialog progressDialog;

    //    FirebaseUser firebaseUser;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        registernow=findViewById(R.id.registernow);
        login=findViewById(R.id.login);

        email=findViewById(R.id.editTextEmail1);
        password=findViewById(R.id.editTextPassword1);

//        firebaseAuth= FirebaseAuth.getInstance();
//        firebaseUser=firebaseAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this);


        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            sendUsertoNextActivity();
            return;
        }



        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,register.class));
                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,mainpage.class));
                finish();

                performlogin();



            }
        });
    }

    private void performlogin() {

        email2 = email.getText().toString();
        password2 = password.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            // check email foramt is invalid, dont procee further
            email.setError("Email is Invalid");
        } else if (TextUtils.isEmpty(password2)) {
            //no password is entered
            password.setError("Enter Password");
        } else {
            //Data is valid now continue to firebase login
            progressDialog.setMessage("Logging in...");
            progressDialog.setTitle("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email2,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        sendUsertoNextActivity();
                        Toast.makeText(login.this,"Login Successful",Toast.LENGTH_SHORT);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(login.this,"Task Failed"+task.getException(),Toast.LENGTH_SHORT);

                    }
                }
            });

        }
    }

    private void sendUsertoNextActivity() {
        Intent intent=new Intent(login.this,mainpage.class);
        finish();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}