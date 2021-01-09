package rig.up;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;
import rig.up.Common.Common;
import rig.up.Model.User;

public class LoginActivity extends AppCompatActivity {

    EditText editUname, editPass;
    ProgressBar progressBar;
    Button btnSignIn;
    CheckBox cRemember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        editUname = findViewById(R.id.txtUname);
        editPass = findViewById(R.id.txtPass);
        progressBar = findViewById(R.id.progressBar2);
        cRemember = findViewById(R.id.ckbRemember);

        btnSignIn = findViewById(R.id.btnLogin);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConnectedToInternet(getBaseContext())) {

                    //save user logon
                    if(cRemember.isChecked())
                    {
                        Paper.book().write(Common.USER_KEY, editUname.getText().toString());
                        Paper.book().write(Common.PWD_KEY, editPass.getText().toString());
                    }

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String uname = editUname.getText().toString().trim();
                            String pass = editPass.getText().toString().trim();
                            progressBar.setVisibility(View.VISIBLE);

                            if (uname.isEmpty()) {
                                editUname.setError("Username is required");
                                editUname.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            if (pass.isEmpty()) {
                                editPass.setError("Password is required");
                                editPass.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            if (pass.length() < 8) {
                                editPass.setError("Must contain at least 8 characters");
                                editPass.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            if (dataSnapshot.child(editUname.getText().toString()).exists()) {

                                progressBar.setVisibility(View.VISIBLE);


                                User user = dataSnapshot.child(editUname.getText().toString()).getValue(User.class);
                                user.setName(editUname.getText().toString()); //set NAME
                                if (user.getPassword().equals(editPass.getText().toString())) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Sign in success", Toast.LENGTH_SHORT).show();
                                    Intent mainIntent = new Intent(getApplicationContext(), NavDrawerActivity.class);
                                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    Common.currentUser = user;
                                    startActivity(mainIntent);
                                    finish();

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "No internet access", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

}

