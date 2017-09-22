package com.ndp.triplay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Handler;


public class SignupActivity extends AppCompatActivity
{
    EditText editTextEmail, editTextPassword,editTextUserName;
    Button buttonSignup, buttonSignin,buttonSignInGoogle,buttonReset;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mauth;
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference myRef;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private static final String TAG1 = "Google Signin";
    private  static final int PC_SIGN_IN = 9001;
    private  static  int UserID;
    private static   AtomicInteger count = new AtomicInteger();
    Intent intent1;
    Intent intent3;
    private  String userID;

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        mauth = FirebaseAuth.getInstance();


        firebaseAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop()
    {
        super.onStop();

        if(mAuthListener !=null){firebaseAuth.removeAuthStateListener(mAuthListener);}



    }

    FirebaseUser user;
    private void checkIfEmailVerified()
    {
        user =  firebaseAuth.getCurrentUser();

        if (user.isEmailVerified())
        {
            Toast.makeText(SignupActivity.this, "Başarılı Bir Şekilde Giriş Yapılmıştır", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignupActivity.this, HomePage.class);
            startActivity(intent);
            finish();
        }
        else
        {

            user = null;
            FirebaseAuth.getInstance().signOut();
            Intent intent1 = getIntent();
            startActivity(intent1);
            finish();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        intent3 = getIntent();

        progressDialog = new ProgressDialog(this);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        myRef = mFirebaseInstance.getReference();

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        editTextUserName = (EditText) findViewById(R.id.input_name);


        if(TextUtils.isEmpty(editTextEmail.toString())){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(editTextPassword.toString())){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        buttonSignup = (Button) findViewById(R.id.btn_signup);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    intent1 = new Intent(SignupActivity.this,HomePage.class);
                    startActivity(intent1);
                    updateUI(user);

                   // Log.d(TAG1,"onAuthStateChanged:signed_in" + user.getUid());
                }
                else
                {
                   // Log.d(TAG1,"onAuthStateChanged:signed_out");
                    updateUI(null);
                }

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  userName = editTextUserName.getText().toString().trim();
                final String  email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();
                final String ID = myRef.child("users").push().getKey();
                final int userID = count.incrementAndGet();


                firebaseAuth.createUserWithEmailAndPassword(email, password)

                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {

                                    sendEmailVerification();
                                    final String userName = editTextUserName.getText().toString().trim();
                                    final String email = editTextEmail.getText().toString().trim();
                                    final String password = editTextPassword.getText().toString().trim();
                                    final String ID = myRef.child("users").push().getKey();
                                    UserID = count.incrementAndGet();



                                    if (!userName.equals("") && !email.equals("") && !password.equals("")) {

                                        User kullanici = new User(userName, email, password, UserID);


                                        myRef.child("users").child(ID).child("kullanıcıIsmi").setValue(kullanici.getUserName());
                                        myRef.child("users").child(ID).child("Email").setValue(kullanici.getEmail());
                                        myRef.child("users").child(ID).child("Parola").setValue(kullanici.getPassword());
                                        myRef.child("users").child(ID).child("ID").setValue(kullanici.getId());
                                    }


                                }

                                if (!task.isSuccessful()) {

                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(SignupActivity.this, "Bu mail adresine sahip başka bir kullanıcı vardır.", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(SignupActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                                }


                            }
                        });

            }
        });
    }



    private void sendEmailVerification()
    {
        final String  userName = editTextUserName.getText().toString().trim();
        final String  email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        User us = new User();
        User user1;
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button

                        if (task.isSuccessful()) {

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    checkIfEmailVerified();
                                }
                            }, 10000);

                            Toast.makeText(SignupActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            firebaseAuth.signOut();
                            Intent intent1 = getIntent();
                            startActivity(intent1);

                            // FirebaseUser user = firebaseAuth.getCurrentUser();
                            finish();

                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(SignupActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

        } else {


        }
    }
}


