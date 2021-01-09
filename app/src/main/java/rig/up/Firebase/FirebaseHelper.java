package rig.up.Firebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import rig.up.Model.Component;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //READ OPERATION
    public ArrayList<String> retrieve()
    {
        final ArrayList<String> component=new ArrayList<>();

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot, component);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot, component);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return component;
    }

    private void fetchData(DataSnapshot dataSnapshot, ArrayList<String> component) {
        component.clear();
        for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
        {
            String name = dataSnapshot1.getValue(Component.class).getName();
            component.add(name);
            String price = dataSnapshot1.getValue(Component.class).getPrice();
            component.add(price);
            String socketid = dataSnapshot1.getValue(Component.class).getSocketId();
            component.add(socketid);
            String wattage = dataSnapshot1.getValue(Component.class).getWattage();
            component.add(wattage);

        }
    }
}
