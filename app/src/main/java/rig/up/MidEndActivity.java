package rig.up;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rig.up.Common.Common;
import rig.up.Interface.FirebaseLoadDone;
import rig.up.Model.Component;
import rig.up.Model.User;
import rig.up.SubMenu.HistoryActivity;
import rig.up.ViewHolder.MEndViewHolder;

public class MidEndActivity extends AppCompatActivity implements FirebaseLoadDone {

    SearchableSpinner searchableSpinnerMobo;
    SearchableSpinner searchableSpinnerCpu;
    SearchableSpinner searchableSpinnerRam;
    SearchableSpinner searchableSpinnerGpu;
    SearchableSpinner searchableSpinnerStor;
    SearchableSpinner searchableSpinnerCase;
    SearchableSpinner searchableSpinnerPsu;
    DatabaseReference component;
    DatabaseReference savedBuild;
    DatabaseReference user;
    FirebaseLoadDone firebaseLoadDone;

    TextView psuEstimation;
    TextView priceCalculation;

    Button btnCalculateWatt;
    Button btnSave;

    List<Component> components;
    List<Component> componentsMobo;
    List<Component> componentsCpu;
    List<Component> componentsMemory;
    List<Component> componentsGpu;
    List<Component> componentsStorage;
    List<Component> componentsCasing;
    List<Component> componentsPsu;




    private String BuildId = " ";

    private ArrayList<String> cMotherboard = new ArrayList<>();

    int TotalWatt = 0;
    float TotalPrice;
    String SocketType = "SocketId";

    String socketMobo;
    String socketCpu;
    String socketRam;

    int sumMobo = 0;
    int sumCpu = 0;
    int sumRam = 0;
    int sumGpu = 0;
    int sumStorage = 0;

    float priceMobo;
    float priceCpu;
    float priceRam;
    float priceGpu;
    float priceStorage;
    float priceCasing;
    float pricePsu;

    int wattPSU;
    int socket;



    //private ArrayList<String> cKey = new ArrayList<>(); //

    FirebaseRecyclerAdapter<Component, MEndViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mid_end);

        searchableSpinnerMobo = findViewById(R.id.search_mobo_spinner_mid);
        searchableSpinnerCpu = findViewById(R.id.search_cpu_spinner_mid);
        searchableSpinnerRam = findViewById(R.id.search_ram_spinner_mid);
        searchableSpinnerGpu = findViewById(R.id.search_gpu_spinner_mid);
        searchableSpinnerStor = findViewById(R.id.search_storage_spinner_mid);
        searchableSpinnerCase = findViewById(R.id.search_casing_spinner_mid);
        searchableSpinnerPsu = findViewById(R.id.search_psu_spinner_mid);

        psuEstimation = findViewById(R.id.txtPsuEstimation);
        priceCalculation = findViewById(R.id.txtTotalPrice);


        btnCalculateWatt = findViewById(R.id.btn_calc);
        btnSave = findViewById(R.id.btn_save);

        //INIT DATABASE
        component = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Component");
        savedBuild = FirebaseDatabase.getInstance().getReference("Savedbuild");
        user = FirebaseDatabase.getInstance().getReference("User");
        //INIT INTERFACE
        firebaseLoadDone = this;

        setSearchableSpinnerMobo();
        setSearchableSpinnerCpu();
        setSearchableSpinnerRam();
        setSearchableSpinnerGpu();
        setSearchableSpinnerStor();
        setSearchableSpinnerCase();
        setSearchableSpinnerPsu();


        searchableSpinnerMobo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsMobo.get(position);
                String total = component.getWattage();
                String price = component.getPrice();
                String socket = component.getSocketId();

                sumMobo = Integer.parseInt(total);
                priceMobo = Float.parseFloat(price);
                socketMobo = String.valueOf(socket);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerCpu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsCpu.get(position);
                String total = component.getWattage();
                String price = component.getPrice();
                String socket = component.getSocketId();

                socketCpu = String.valueOf(socket);

                sumCpu = Integer.parseInt(total);
                priceCpu = Float.parseFloat(price);

                if(!socketMobo.equals(socketCpu)){
                    Toast.makeText(getApplicationContext(),"Socket mismatch! please use the same socket. e.g: LGA1150=DDR3", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerRam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsMemory.get(position);
                String total = component.getWattage();
                String price = component.getPrice();
                String socket = component.getSocketId();

                socketRam = String.valueOf(socket);
                sumRam = Integer.parseInt(total);
                priceRam= Float.parseFloat(price);

                if(!socketCpu.equals(socketRam)){
                    Toast.makeText(getApplicationContext(),"Socket mismatch! please use the same socket. e.g: LGA1150=DDR3", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerGpu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsGpu.get(position);
                String total = component.getWattage();
                String price = component.getPrice();

                sumGpu = Integer.parseInt(total);
                priceGpu = Float.parseFloat(price);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerStor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsStorage.get(position);
                String total = component.getWattage();
                String price = component.getPrice();

                sumStorage = Integer.parseInt(total);
                priceStorage = Float.parseFloat(price);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerCase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsCasing.get(position);
                String price = component.getPrice();

                priceCasing = Float.parseFloat(price);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerPsu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsPsu.get(position);
                String price = component.getPrice();

                String intPSUwatt = component.getWattage();

                wattPSU = Integer.parseInt(intPSUwatt);
                pricePsu = Float.parseFloat(price);

                if(wattPSU < TotalWatt){
                    Toast.makeText(getApplicationContext(),"Incompatible power supply", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnCalculateWatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                component.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        TotalWatt = sumMobo + sumCpu + sumRam + sumGpu + sumStorage + 20;
                        String totalWatt = String.valueOf(TotalWatt);
                        psuEstimation.setText(totalWatt + " W" + "  ");

                        if(wattPSU < TotalWatt){
                            Toast.makeText(getApplicationContext(),"Incompatible power supply", Toast.LENGTH_SHORT).show();
                        }

                        TotalPrice = priceMobo + priceCpu + priceRam + priceGpu + priceStorage + priceCasing + pricePsu;
                        String totalPrice = String.valueOf(TotalPrice) + "0";
                        priceCalculation.setText(totalPrice + "MYR");



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }


        });
    }

    private void showAlertDialog(){

        final Map<List<Component>, rig.up.Model.User> users = new HashMap<>();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Save as");

        //SET EDITTEXT TO GET USER INPUT
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                if(input==null){
                    input.setError("This field is required.");
                    input.requestFocus();
                }else {

                String currentUser = Common.currentUser.getName();
                String input1 = input.getText().toString();
                String tPrice = priceCalculation.getText().toString();
                String tWatt = psuEstimation.getText().toString();

                final String setMobo = searchableSpinnerMobo.getSelectedItem().toString().trim();
                final String setCpu = searchableSpinnerCpu.getSelectedItem().toString().trim();
                final String setRam = searchableSpinnerRam.getSelectedItem().toString().trim();
                final String setGpu = searchableSpinnerGpu.getSelectedItem().toString().trim();
                final String setCase = searchableSpinnerCase.getSelectedItem().toString().trim();
                final String setStor = searchableSpinnerStor.getSelectedItem().toString().trim();
                final String setPsu = searchableSpinnerPsu.getSelectedItem().toString().trim();



                    savedBuild.child(currentUser).child(input1).child("UserId").setValue(currentUser);
                    savedBuild.child(currentUser).child(input1).child("Motherboard").setValue(setMobo);
                    savedBuild.child(currentUser).child(input1).child("Processor").setValue(setCpu);
                    savedBuild.child(currentUser).child(input1).child("Memory").setValue(setRam);
                    savedBuild.child(currentUser).child(input1).child("Graphic_Card").setValue(setGpu);
                    savedBuild.child(currentUser).child(input1).child("Casing").setValue(setCase);
                    savedBuild.child(currentUser).child(input1).child("Storage").setValue(setStor);
                    savedBuild.child(currentUser).child(input1).child("Power_Supply").setValue(setPsu);
                    savedBuild.child(currentUser).child(input1).child("Total_Price").setValue(tPrice);
                    savedBuild.child(currentUser).child(input1).child("Total_Wattage").setValue(tWatt);

                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                    addNotification();
                    dialog.dismiss();
                    finish();
                }


            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();


    }

    private void addNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle("Mid-End")
                .setContentText("Successfully saved! Click here to view");

        // Creates the intent needed to show the notification
        Intent notificationIntent = new Intent(this, HistoryActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    public void setSearchableSpinnerMobo(){
        component.orderByChild("Build_CategoryId").equalTo("09").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));
                }
                firebaseLoadDone.onFirebaseLoadMobo(categories);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onFirebaseLoadMobo(List<Component> componentList) {
        componentsMobo = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();
        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");

        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerMobo.setAdapter(adapter);


    }


    public void setSearchableSpinnerCpu(){
        component.orderByChild("Build_CategoryId").equalTo("010").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));

                }
                firebaseLoadDone.onFirebaseLoadCpu(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFirebaseLoadCpu(List<Component> componentList) {
        componentsCpu = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();
        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");

        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerCpu.setAdapter(adapter);


    }

    public void setSearchableSpinnerRam(){

        component.orderByChild("Build_CategoryId").equalTo("011").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));

                }
                firebaseLoadDone.onFirebaseLoadRam(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFirebaseLoadRam(List<Component> componentList) {
        componentsMemory = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();
        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");

        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerRam.setAdapter(adapter);

    }

    public void setSearchableSpinnerGpu(){

        component.orderByChild("Build_CategoryId").equalTo("012").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));

                }
                firebaseLoadDone.onFirebaseLoadGpu(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFirebaseLoadGpu(List<Component> componentList) {
        componentsGpu = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();
        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");


        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerGpu.setAdapter(adapter);

    }

    public void setSearchableSpinnerStor(){

        component.orderByChild("CategoryId").equalTo("05").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));

                }
                firebaseLoadDone.onFirebaseLoadStorage(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFirebaseLoadStorage(List<Component> componentList) {
        componentsStorage = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();
        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");


        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerStor.setAdapter(adapter);

    }

    public void setSearchableSpinnerCase(){

        component.orderByChild("CategoryId").equalTo("06").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));

                }
                firebaseLoadDone.onFirebaseLoadCasing(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFirebaseLoadCasing(List<Component> componentList) {
        componentsCasing = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();

        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");





        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerCase.setAdapter(adapter);

    }

    public void setSearchableSpinnerPsu(){
        component.orderByChild("CategoryId").equalTo("07").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Component> categories = new ArrayList<>();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    categories.add(snapshot.getValue(Component.class));

                }
                firebaseLoadDone.onFirebaseLoadPsu(categories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onFirebaseLoadPsu(List<Component> componentList) {
        componentsPsu = componentList;
        //GET NAME
        List<String> name_list = new ArrayList<>();
        for(Component component:componentList)
            name_list.add(component.getName() + "     " + component.getPrice() + "MYR");


        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerPsu.setAdapter(adapter);

    }
    @Override
    public void onFirebaseLoadFailed(String message) {

    }
}
