package com.ndp.triplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {

    Intent intent;
    FirebaseAuth mauth;
    Button buttonSignOut;
    private GoogleApiClient mGoogleApiClient;
    FirebaseUser fuser;
    FirebaseAuth firebaseAuth;
    DatabaseReference mMessagesDatabaseReference;
    private String userID;
    FirebaseDatabase mFirebaseDatabase;
    private static final String TAG = "HomePage";

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;

    private String mUsername;
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.action_conversatione:
                    // mTextMessage.setText(R.string.text_conversation);
                    transaction.replace(R.id.content, new ChatFragment()).commit();
                    return true;
                case R.id.action_maps:
                    // mTextMessage.setText(R.string.text_maps);
                    transaction.replace(R.id.content, new MapsFragment()).commit();
                    return true;
                case R.id.action_games:
                    //mTextMessage.setText(R.string.text_game);
                    transaction.replace(R.id.content, new GameFragment()).commit();
                    return true;
                case R.id.action_book:
                    //mTextMessage.setText(R.string.text_book);
                    transaction.replace(R.id.content, new BookFragment()).commit();
                    return true;
                case R.id.action_settings:
                    //mTextMessage.setText(R.string.text_settings);
                    transaction.replace(R.id.content, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        intent = getIntent();
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);
        mauth = FirebaseAuth.getInstance();
        fuser = mauth.getCurrentUser();
        //  mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        buttonSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mauth.signOut();


                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Toast.makeText(getApplicationContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                        fuser = null;
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

}
