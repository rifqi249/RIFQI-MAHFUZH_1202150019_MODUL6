package com.example.rifqimahfuzh.rifqimahfuzh_1202150019_modul6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.rifqimahfuzh.rifqimahfuzh_1202150019_modul6.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout mEmail,mPassword;
    private Button regis,masuk;
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmail = (TextInputLayout) findViewById(R.id.email);
        mPassword = (TextInputLayout) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void login(View view) {
        signIn(mEmail.getEditText().getText().toString(),mPassword.getEditText().getText().toString());

    }

    private void signIn(String email,String password) {
        Log.d(TAG,"signIn :" + email);

        if (!validateForm()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:" +task.isSuccessful());
                        if (task.isSuccessful()) {


//                            FirebaseUser user = mAuth.getCurrentUser();
//
//                            Intent a = new Intent(MainActivity.this,Home.class);
//                            startActivity(a);

                            onAuthSuccess(task.getResult().getUser());

//                            updateUI(user);
                        } else {

                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            Toast.makeText(MainActivity.this, "Authentication failed.",

                                    Toast.LENGTH_SHORT).show();

//                            updateUI(null);
                        }
                    }
                });

    }



    public void daftar(View view) {
        createAccount(mEmail.getEditText().getText().toString(),mPassword.getEditText().getText().toString());
    }

    private void createAccount(String email,String password) {
        Log.d(TAG,"createAccount" + email);

        if (!validateForm()){
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG,"createUserWithEmail:" + task.isSuccessful());
                        if (task.isSuccessful()){
//                            Log.d(TAG,"createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(MainActivity.this,"Authentication Success.",Toast.LENGTH_SHORT).show();
                            onAuthSuccess(task.getResult().getUser());

//                            updateUI(user);
                        } else{
                            Log.w(TAG,"createUserWithEmail:failure",task.getException());
                            Toast.makeText(MainActivity.this,"Sign Up Failed.",Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        writeNewUser(user.getUid(),username,user.getEmail());

        startActivity(new Intent(MainActivity.this,Home.class));
        finish();
    }

    private void writeNewUser(String uid, String username, String email) {
        User user = new User(username,email);

        mDatabase.child("users").child(uid).setValue(user);
    }

    private String usernameFromEmail(String email) {

        if (email.contains("@")){
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void signOut() {
        mAuth.signOut();
//        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user!=null){

        }
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getEditText().getText().toString();
        if (TextUtils.isEmpty(email)){
            mEmail.setError("Required");
            valid = false;
        }
        else {
            mEmail.setError(null);
        }

        String password = mPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)){
            mPassword.setError("Required");
            valid = false;
        } else{
            mPassword.setError(null);
        }
        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {

            signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
