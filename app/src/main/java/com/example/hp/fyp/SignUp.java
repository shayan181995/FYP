package com.example.hp.fyp;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by HP on 5/11/2017.
 */

public class SignUp extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
   // private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText name;
    private EditText country;
    private EditText city;
    private EditText phone;
    private EditText UserType;
    private EditText gender;
    private EditText PaymentMode;

    private String semail;
    private String spassword;
    private String sname;
    private String scountry;
    private String scity;
    private int sphone;
    private String sUserType;
    private String sgender;
    private String sPaymentMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //Reference to widgets
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        country = (EditText) findViewById(R.id.country);
        city = (EditText) findViewById(R.id.city);


// ...
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
      //  FirebaseUser currentUser = mAuth.getCurrentUser();
        //thing to do if user already sign in redirect to main page
        // updateUI(currentUser);
    }

    public void SignUpUser(View view){

        semail = email.getText().toString();
        spassword = password.getText().toString();
        sname = name.getText().toString();
        scountry = country.getText().toString();
        scity = city.getText().toString();



        ///Firebase Authentication SignUp////
        mAuth.createUserWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            //Adding Userid in profile table///
                            User muser = new User(user.getUid(),semail,spassword,sname,scountry,scity,0,"Captain","","");
                            DatabaseReference usersRef = ref.child("users");
                            DatabaseReference newUserRef = usersRef.push();
                            ////////////////////////////

                            // Get the unique ID generated by a push()
                            String ProfileId = newUserRef.getKey();
                            ///////////////////////////////
                            //Add in Database///
                            newUserRef.setValue(muser);

                            //////////////////
                            //Redirecting To main Page with Userid//
                            Intent i = new Intent(SignUp.this,Main.class);
                            i.putExtra("UserID",ProfileId);
                            startActivity(i);
                            //////////////
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "SignUp failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }

}
