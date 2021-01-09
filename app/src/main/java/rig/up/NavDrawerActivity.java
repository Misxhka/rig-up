package rig.up;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

import io.paperdb.Paper;
import rig.up.Common.Common;
import rig.up.Model.User;
import rig.up.SubMenu.AccountActivity;
import rig.up.SubMenu.HistoryActivity;
import rig.up.SubMenu.SignOutActivity;

public class NavDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        TextView txtUserEmail;

        CardView cardInfo;
        CardView cardLow;
        CardView cardMid;
        CardView cardHi;
        CardView cardPsu;

        ImageButton infoLow;
        ImageButton infoMid;
        ImageButton infoHigh;
        ImageButton infoPsu;
        ImageButton infoQuick;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        cardInfo = findViewById(R.id.cardInfo);
        cardLow = findViewById(R.id.cardLow);
        cardMid = findViewById(R.id.cardMid);
        cardHi = findViewById(R.id.cardHi);
        cardPsu = findViewById(R.id.cardPsu);

        infoLow = findViewById(R.id.optionLow);
        infoMid = findViewById(R.id.optionMid);
        infoHigh = findViewById(R.id.optionHigh);
        infoPsu = findViewById(R.id.optionPsu);
        infoQuick = findViewById(R.id.optionQinfo);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //User email display
        View headerView = navigationView.getHeaderView(0);
        txtUserEmail = (TextView)headerView.findViewById(R.id.txtUserEmail);
        txtUserEmail.setText(Common.currentUser.getEmail());


        cardLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lowEnd = new Intent(getApplicationContext(), LowEndActivity.class);
                startActivity(lowEnd);
            }
        });

        cardMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent midEnd = new Intent(getApplicationContext(), MidEndActivity.class);
                startActivity(midEnd);
            }
        });

        cardHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hiEnd = new Intent(getApplicationContext(), HighEndActivity.class);
                startActivity(hiEnd);
            }
        });

        cardPsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent psuCalc = new Intent(getApplicationContext(), PsuCalculatorActivity.class);
                startActivity(psuCalc);
            }
        });

        cardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quickInfo = new Intent(getApplicationContext(), QuickInfoActivity.class);
                startActivity(quickInfo);
            }
        });

        infoLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowAlertDialog();
            }
        });

        infoMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                midAlertDialog();
            }
        });

        infoHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highAlertDialog();
            }
        });

        infoPsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psuAlertDialog();
            }
        });

        infoQuick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickAlertDialog();
            }
        });
    }

    private void lowAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Low-end Specification");
        alert.setMessage(getString(R.string.option_info_low));

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void midAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Mid-end Specification");
        alert.setMessage(getString(R.string.option_info_mid));

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void highAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("High-end Specification");
        alert.setMessage(getString(R.string.option_info_hi));

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void psuAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Psu watt calculator");
        alert.setMessage(getString(R.string.option_info_psu));

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void quickAlertDialog(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Quick info");
        alert.setMessage(getString(R.string.option_info_quick));

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showAlertDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
        //noinspection SimplifiableIfStatement
        case R.id.action_settings:
            Intent appInfo = new Intent(getApplicationContext(), AppInfoActivity.class);
            startActivity(appInfo);
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){

            //case R.id.nav_account:
            //    Intent account = new Intent(getApplicationContext(), AccountActivity.class);
            //    startActivity(account);
            //    break;
            case R.id.nav_history:
                Intent history = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivity(history);
                break;
            case R.id.nav_signout:

                //delete user credential
                Paper.book().destroy();

                Intent signout = new Intent(getApplicationContext(), LoginActivity.class);
                signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signout);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}


