package rig.up;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import rig.up.Model.Component;

public class ComponentDetail extends AppCompatActivity {

    TextView comp_name, comp_price, comp_desc;
    ImageView comp_image;
    CollapsingToolbarLayout collapsingToolbarLayout;



    String categoryId="";

    FirebaseDatabase database;
    DatabaseReference component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_detail);

        database = FirebaseDatabase.getInstance();
        component = database.getReference("Component");


        //comp_name = findViewById(R.id.component_name);
        comp_desc = findViewById(R.id.comp_description);
        comp_price = findViewById(R.id.component_price);
        comp_image = findViewById(R.id.img_comp);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        if(getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty()){
            getDetailComp(categoryId);
        }


    }

    private void getDetailComp(String categoryId) {
        component.child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Component components = dataSnapshot.getValue(Component.class);

                Picasso.with(getBaseContext()).load(components.getImage()).into(comp_image);

                collapsingToolbarLayout.setTitle(components.getName());

                comp_price.setText(components.getPrice()+ " MYR");

                //comp_name.setText(components.getName());

                comp_desc.setText(components.getDescription());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
