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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class register extends AppCompatActivity {

    Button register;
    EditText username,email, phone,password;
    TextView registertxt;
    String username1="",email1="",password1="",phoneno="";
    ProgressDialog progressDialog;
//    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registertxt=findViewById(R.id.account);
        username=findViewById(R.id.editTextName);
        email=findViewById(R.id.editTextEmail);
        password=findViewById(R.id.editTextPassword);
        phone =findViewById(R.id.editTextMobile);
        register=findViewById(R.id.register);

//        firebaseAuth= FirebaseAuth.getInstance();
//        firebaseUser=firebaseAuth.getCurrentUser();


        progressDialog=new ProgressDialog(this);


        registertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this,login.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser();
            }
        });
    }

    private void checkUser() {
        username1=username.getText().toString();
        email1=email.getText().toString();
        password1=password.getText().toString();
        phoneno= phone.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            // check email foramt is invalid, dont procee further
            email.setError("Email is Invalid");
        }


        else if (TextUtils.isEmpty(username1)){
            //no password is entered
            username.setError("Enter Username");
        }
        else if (TextUtils.isEmpty(password1)){
            //no password is entered
            password.setError("Enter Password");
        }
        else if (TextUtils.isEmpty(phoneno)){
            //no password is entered
            phone.setError("Enter Phone Number");
        }

        else{
            //Data is valid now continue to firebase login
            progressDialog.setMessage("Registration...");
            progressDialog.setTitle("Please Wait");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users")
                                        .child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
                        reference.child("name").setValue(username1);
                        reference.child("phone").setValue(phoneno);
                        progressDialog.dismiss();
                        sendUsertoNextActivity();
                        Toast.makeText(register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(register.this,"Task Failed"+task.getException(),Toast.LENGTH_SHORT).show();

                    }
                }
            });


        }

    }
private void sendUsertoNextActivity() {
    Intent intent=new Intent(register.this,mainpage.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
}


}