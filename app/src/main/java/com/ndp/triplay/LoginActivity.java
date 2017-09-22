package com.ndp.triplay;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    EditText editTextEmail, editTextPassword,editTextUserName;
    Button  buttonSignin,buttonSignInGoogle;
    TextView buttonSignup,buttonReset;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mauth;
    FirebaseDatabase mFirebaseInstance;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";
    private static final String TAG1 = "Google Signin";
    private  static final int PC_SIGN_IN = 9001;
    Intent intent1;
    Intent intent3;
    Intent intent2;
    User user1 = new User();

    private GoogleApiClient mGoogleApiClient;

      private boolean mGoogleIntentInProgress;


    private boolean mGoogleLoginClicked;


    private ConnectionResult mGoogleConnectionResult;
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        mauth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();



        firebaseAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(mAuthListener !=null){firebaseAuth.removeAuthStateListener(mAuthListener);}

    }

    @Override
    public void  onActivityResult(int requestCode,int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == PC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }
        }
        else
        {
            updateUI(null);
        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct)
    {
        //Log.d("firebaseAuthWithGoogle:"+acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG1,"signWithCredential onComplete:"+task.isSuccessful());
                        intent1 = new Intent(LoginActivity.this,HomePage.class);
                        startActivity(intent1);

                        if(!task.isSuccessful())
                        {
                            Log.w(TAG1,"signWithCredential",task.getException());
                            Toast.makeText(LoginActivity.this,"AuthenticationFaied",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

       /* firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        intent1 = new Intent(LoginActivity.this,HomePage.class);
                        startActivity(intent1);
                        Log.d(TAG1,"signWithCredential onComplete:"+task.isSuccessful());
                        if(!task.isSuccessful())
                        {
                            Log.w(TAG1,"signWithCredential",task.getException());
                            Toast.makeText(LoginActivity.this,"AuthenticationFaied",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent3 = getIntent();


        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
       // editTextUserName = (EditText) findViewById(R.id.editTextUsername);

        buttonSignInGoogle = (Button) findViewById(R.id.btn_loginGoogle);
        buttonSignup = (TextView) findViewById(R.id.link_signup);
        buttonSignin = (Button) findViewById(R.id.btn_login);
        buttonReset = (TextView) findViewById(R.id.btn_forget);

        progressDialog = new ProgressDialog(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    intent1 = new Intent(LoginActivity.this,HomePage.class);
                    startActivity(intent1);
                    updateUI(user);

                    Log.d(TAG1,"onAuthStateChanged:signed_in" + user.getUid());
                }
                else
                {
                    Log.d(TAG1,"onAuthStateChanged:signed_out");
                    updateUI(null);
                }

            }
        };


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2 = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent2);

            }
        });



        buttonSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinDeneme();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    public void resetPassword()
    {
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplication(), "Mailinizi giriniz", Toast.LENGTH_SHORT).show();
            return;
        }

        // progressDialog.setVisibility(View.VISIBLE);

        firebaseAuth.sendPasswordResetEmail(email)

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Şifre sıfırlama mesajı emailinize gönderildi!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Gönderilemedi!", Toast.LENGTH_SHORT).show();
                        }

                        //    progressBar.setVisibility(View.GONE);
                    }
                });
    }
    public void signinDeneme()
    {

        String  email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        Log.d(TAG, "signIn:" + email);
        final FirebaseUser prevUser = firebaseAuth.getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(email,password);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "girmedi", Toast.LENGTH_LONG).show();

                        } else {
                            checkIfEmailVerified();



                        }
                    }
                });

    }

    private void sendEmailVerification()
    {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        // Re-enable button

                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this,
                                    "Doğrulama Mesajı Gönderilmiştir: " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            firebaseAuth.signOut();
                            Intent intent1 = getIntent();
                            startActivity(intent1);

                            // FirebaseUser user = firebaseAuth.getCurrentUser();
                            finish();

                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Gönderilemedi",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    FirebaseUser user;
    private void checkIfEmailVerified()
    {
        user =  firebaseAuth.getCurrentUser();

        if (user.isEmailVerified())
        {

            Toast.makeText(LoginActivity.this, "Başarılı bir şekilde giriş yapılmıştır", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomePage.class);
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

    private void updateUI(FirebaseUser user) {

        if (user != null) {

        } else {


            findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_loginGoogle).setVisibility(View.VISIBLE);


        }
    }
    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,PC_SIGN_IN);

    }

    private void signOut()
    {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(null);
            }
        });
    }


    private  void  revokeAccess()
    {
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(null);
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
