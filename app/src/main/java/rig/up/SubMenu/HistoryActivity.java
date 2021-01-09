package rig.up.SubMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rig.up.Adapter.SavedBuildAdapter;
import rig.up.Common.Common;
import rig.up.Interface.FirebaseLoadDone;
import rig.up.Interface.ItemClickListener;
import rig.up.LoginActivity;
import rig.up.LowEndActivity;
import rig.up.Model.Component;
import rig.up.Model.SavedBuild;
import rig.up.Model.SavedbuildII;
import rig.up.Model.User;
import rig.up.QuickInfoActivity;
import rig.up.R;
import rig.up.ViewHolder.SavedViewHolder;


public class HistoryActivity extends AppCompatActivity{
       // implements NavigationView.OnNavigationItemSelectedListener {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    private List<SavedbuildII> savedbuildList;
    private SavedBuildAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference component;
    DatabaseReference savedBuild;
    DatabaseReference user;

    SavedbuildII SB;

    TextView savedName;
    TextView tPrice;
    ImageButton btnDel;

    CardView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        savedName = findViewById(R.id.saved_name);

        recyclerView = findViewById(R.id.listComponents);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SB = new SavedbuildII();
        savedbuildList = new ArrayList<>();
        adapter = new SavedBuildAdapter(this, savedbuildList);

        recyclerView.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();
        savedBuild = database.getReference("Savedbuild");

        String currentUser = Common.currentUser.getName();
        savedBuild.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<SavedbuildII> categories = new ArrayList<>();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    SB  = snapshot.getValue(SavedbuildII.class);

                    String name = snapshot.getKey();
                    SB.setSavedName(name);
                    savedbuildList.add(SB);

                }
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






}}
