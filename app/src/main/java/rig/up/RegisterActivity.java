package rig.up;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rig.up.Common.Common;
import rig.up.Model.User;


public class RegisterActivity extends AppCompatActivity  {

    private EditText EditUname, EditEmail, EditPass;
    private ProgressBar progressBar;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        EditUname = findViewById(R.id.txtUnameR);
        EditEmail = findViewById(R.id.txtEmailR);
        EditPass = findViewById(R.id.txtPassR);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnSignUp = findViewById(R.id.btnReg);

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (Common.isConnectedToInternet(getBaseContext())) {
                    progressBar.setVisibility(View.VISIBLE);

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            final String uname = EditUname.getText().toString().trim();
                            final String email = EditEmail.getText().toString().trim();
                            final String pass = EditPass.getText().toString().trim();
                            progressBar.setVisibility(View.VISIBLE);
                            if (email.isEmpty()) {
                                EditEmail.setError("Email is required");
                                EditEmail.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }
                            if (email.equals(EditEmail.getText().toString())) {
                                EditEmail.setError("Email already exist");
                                EditEmail.requestFocus();
                                progressBar.setVisibility(View.GONE);
                            }
                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                EditEmail.setError("Invalid email address");
                                EditEmail.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            if (uname.isEmpty()) {
                                EditUname.setError("Username is required");
                                EditUname.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            if (pass.isEmpty()) {
                                EditPass.setError("Password is required");
                                EditPass.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }

                            if (pass.length() < 8) {
                                EditPass.setError("Must contain at least 8 characters");
                                EditPass.requestFocus();
                                progressBar.setVisibility(View.GONE);
                                return;
                            }
                            if (dataSnapshot.child(EditUname.getText().toString()).exists()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                                EditUname.requestFocus();

                            } else {
                                progressBar.setVisibility(View.GONE);
                                User user = new User(EditEmail.getText().toString(), EditPass.getText().toString());
                                table_user.child(EditUname.getText().toString()).setValue(user);
                                Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                                finish();

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



