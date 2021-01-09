package rig.up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import rig.up.Common.Common;
import rig.up.Model.User;

public class RegLogActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_log_activity);

        Button btnSin;
        Button btnSup;

        btnSin = (Button) findViewById(R.id.btnSignin);
        btnSup = (Button) findViewById(R.id.btnSignup);

        Paper.init(this);

        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegLogActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegLogActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //Check remember
        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null)
        {
            if(!user.isEmpty()&& !pwd.isEmpty())
                login(user,pwd);
        }

    }

    private void login(final String usname, final String pwd) {
        //just copy login code from signIn.class

        //Init Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        if (Common.isConnectedToInternet(getBaseContext())) {



            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                   // String uname = editUname.getText().toString().trim();
                   // String pass = editPass.getText().toString().trim();
                    //progressBar.setVisibility(View.VISIBLE);

                    if (dataSnapshot.child(usname).exists()) {

                        //progressBar.setVisibility(View.VISIBLE);


                        User user = dataSnapshot.child(usname).getValue(User.class);
                        user.setName(usname); //set NAME
                        if (user.getPassword().equals(pwd)) {
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Sign in success", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(getApplicationContext(), NavDrawerActivity.class);
                            //mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Common.currentUser = user;
                            startActivity(mainIntent);
                            finish();

                        } else {
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_SHORT).show();
                       // progressBar.setVisibility(View.GONE);
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
}





