package rig.up;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
import java.util.List;
import java.util.Map;

import rig.up.Interface.FirebaseLoadDonePsu;
import rig.up.Model.Component;
import rig.up.ViewHolder.PsuViewHolder;

public class PsuCalculatorActivity extends AppCompatActivity implements FirebaseLoadDonePsu {

    SearchableSpinner searchableSpinnerMobo;
    SearchableSpinner searchableSpinnerCpu;
    SearchableSpinner searchableSpinnerRam;
    SearchableSpinner searchableSpinnerGpu;
    SearchableSpinner searchableSpinnerStor;
    SearchableSpinner searchableSpinnerPsu;
    DatabaseReference component;
    DatabaseReference SavedBuild;
    DatabaseReference User;
    FirebaseLoadDonePsu firebaseLoadDone;

    TextView psuEstimation;

    Button btnCalculateWatt;

    List<Component> components;
    List<Component> componentsMobo;
    List<Component> componentsCpu;
    List<Component> componentsMemory;
    List<Component> componentsGpu;
    List<Component> componentsStorage;
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


    int wattPSU;
    int socket;



    //private ArrayList<String> cKey = new ArrayList<>(); //

    FirebaseRecyclerAdapter<Component, PsuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psu_calculator);

        searchableSpinnerMobo = findViewById(R.id.search_mobo_spinner_psu);
        searchableSpinnerCpu = findViewById(R.id.search_cpu_spinner_psu);
        searchableSpinnerRam = findViewById(R.id.search_ram_spinner_psu);
        searchableSpinnerGpu = findViewById(R.id.search_gpu_spinner_psu);
        searchableSpinnerStor = findViewById(R.id.search_storage_spinner_psu);
        searchableSpinnerPsu = findViewById(R.id.search_psu_spinner_psu);

        psuEstimation = findViewById(R.id.txtPsuEstimation);
        btnCalculateWatt = findViewById(R.id.btn_calc_psu);

        //INIT DATABASE
        component = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Component");
        //INIT INTERFACE
        firebaseLoadDone = this;

        setSearchableSpinnerMobo();
        setSearchableSpinnerCpu();
        setSearchableSpinnerRam();
        setSearchableSpinnerGpu();
        setSearchableSpinnerStor();
        setSearchableSpinnerPsu();


        searchableSpinnerMobo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsMobo.get(position);
                String total = component.getWattage();

                sumMobo = Integer.parseInt(total);

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




                sumCpu = Integer.parseInt(total);


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
                String socket = component.getSocketId();

                socketRam = String.valueOf(socket);

                if(!socketCpu.equals(socketRam)){
                    Toast.makeText(getApplicationContext(), "Socket mismatch! please use the same socket. e.g: LGA1150=DDR3", Toast.LENGTH_LONG).show();
                }

                sumRam = Integer.parseInt(total);



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

                sumGpu = Integer.parseInt(total);


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

                sumStorage = Integer.parseInt(total);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchableSpinnerPsu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Component component = componentsPsu.get(position);

                String intPSUwatt = component.getWattage();

                wattPSU = Integer.parseInt(intPSUwatt);

                if(wattPSU < TotalWatt){
                    Toast.makeText(getApplicationContext(), "Incompatible power supply", Toast.LENGTH_SHORT).show();
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

                        
                        if(searchableSpinnerMobo == null && searchableSpinnerCpu == null && searchableSpinnerRam == null && searchableSpinnerGpu == null && searchableSpinnerStor == null ){
                            Toast.makeText(getApplicationContext(), "Please fill up the component", Toast.LENGTH_SHORT).show();

                        }else
                        {
                            TotalWatt = sumMobo + sumCpu + sumRam + sumGpu + sumStorage + 20;
                            String totalWatt = String.valueOf(TotalWatt);
                            psuEstimation.setText(totalWatt + " W" + "  ");
                        }


                        if(wattPSU < TotalWatt){
                            Toast.makeText(getApplicationContext(), "Incompatible power supply", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }


    public void setSearchableSpinnerMobo(){
        component.orderByChild("CategoryId").equalTo("01").addListenerForSingleValueEvent(new ValueEventListener() {
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
            name_list.add(component.getName());

        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerMobo.setAdapter(adapter);


    }


    public void setSearchableSpinnerCpu(){
        component.orderByChild("CategoryId").equalTo("02").addListenerForSingleValueEvent(new ValueEventListener() {
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
            name_list.add(component.getName());

        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerCpu.setAdapter(adapter);


    }

    public void setSearchableSpinnerRam(){

        component.orderByChild("CategoryId").equalTo("03").addListenerForSingleValueEvent(new ValueEventListener() {
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
            name_list.add(component.getName());

        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerRam.setAdapter(adapter);

    }

    public void setSearchableSpinnerGpu(){

        component.orderByChild("CategoryId").equalTo("04").addListenerForSingleValueEvent(new ValueEventListener() {
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
            name_list.add(component.getName());


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
            name_list.add(component.getName());


        //ADAPTER SETTING FOR SPINNER
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, name_list);
        searchableSpinnerStor.setAdapter(adapter);

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
