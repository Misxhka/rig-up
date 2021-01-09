package rig.up;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rig.up.Interface.ItemClickListener;
import rig.up.Model.Component;
import rig.up.ViewHolder.ComponentViewHolder;

public class ComponentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference complist;

    String categoryId="";


    FirebaseRecyclerAdapter<Component, ComponentViewHolder> adapter;

    //Search
    FirebaseRecyclerAdapter<Component, ComponentViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_list);

        database = FirebaseDatabase.getInstance();
        complist = database.getReference("Component");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_component);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(getIntent()!= null);
            categoryId = getIntent().getStringExtra("CategoryId");
            if (!categoryId.isEmpty() && categoryId != null)
            {
                loadListComp(categoryId);
            }

            //search
        materialSearchBar = findViewById(R.id.search_bar);
            materialSearchBar.setHint("Search here");

            loadSuggest();
            materialSearchBar.setLastSuggestions(suggestList);
            materialSearchBar.setCardViewElevation(10);
            materialSearchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //Change suggest list based on search
                    List<String> suggest = new ArrayList<String>();
                    for(String search:suggestList){
                        if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                            suggest.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {
                    //restore list when search bar closed
                    if(!enabled)
                        recyclerView.setAdapter(adapter);
                }

                @Override
                public void onSearchConfirmed(CharSequence text) {
                    //search result
                    startSearch(text);
                }

                @Override
                public void onButtonClicked(int buttonCode) {

                }
            });

    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Component, ComponentViewHolder>(
                Component.class,
                R.layout.component_item,
                ComponentViewHolder.class,
                complist.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(ComponentViewHolder viewHolder, Component model, int position) {
                viewHolder.comp_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.comp_image);

                final Component local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent compDetail = new Intent(getApplicationContext(), ComponentDetail.class);
                        compDetail.putExtra("CategoryId", searchAdapter.getRef(position).getKey());
                        startActivity(compDetail);
                    }
                });
            }
        };
                recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        complist.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                            Component item = postSnapshot.getValue(Component.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void loadListComp(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Component, ComponentViewHolder>(Component.class,
                R.layout.component_item,
                ComponentViewHolder.class,
                complist.orderByChild("CategoryId").equalTo(categoryId) //SELECT * FROM Category WHERE CategoryId = categoryId
                ) {
            @Override
            protected void populateViewHolder(ComponentViewHolder viewHolder, final Component model, int position) {
                viewHolder.comp_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.comp_image);

                final Component local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent compDetail = new Intent(getApplicationContext(),ComponentDetail.class);
                        compDetail.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(compDetail);
                    }
                });
            }
        };
        //Set Adapter
        recyclerView.setAdapter(adapter);
    }
}
