package rig.up.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


import rig.up.Common.Common;
import rig.up.Model.SavedbuildII;
import rig.up.Model.User;
import rig.up.NavDrawerActivity;
import rig.up.R;
import rig.up.SavedListActivity;
import rig.up.SubMenu.HistoryActivity;

public class SavedBuildAdapter extends RecyclerView.Adapter<SavedBuildAdapter.SBViewHolder> {

    private Context mCtx;
    private List<SavedbuildII> savedList;


    public SavedBuildAdapter(Context mCtx, List<SavedbuildII> savedList){
        this.mCtx = mCtx;
        this.savedList = savedList;
    }

    @NonNull
    @Override
    public SBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new SBViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.content_history, parent, false));



    }

    @Override
    public void onBindViewHolder(@NonNull final SBViewHolder holder, int position) {
        final SavedbuildII savedBuild = savedList.get(position);

        holder.text_Name.setText(savedBuild.getSavedName());
        holder.text_tprice.setText(savedBuild.getTotal_Price());

        holder.btnDel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                savedList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), savedList.size());

               String currentUser = Common.currentUser.getName();

               FirebaseDatabase database;
               DatabaseReference savedbuild;
               database = FirebaseDatabase.getInstance();
               savedbuild = database.getReference("Savedbuild");
               savedbuild.child(currentUser).child(savedBuild.getSavedName()).removeValue();

               Intent i = new Intent(mCtx, HistoryActivity.class);
               i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               mCtx.startActivity(i);




            }
        });

    }

    @Override
    public int getItemCount(){
        return savedList.size();
    }



    class SBViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView text_Name, text_tprice;
        ImageButton btnDel;

        public SBViewHolder(View itemView){
            super(itemView);

            text_Name = itemView.findViewById(R.id.saved_name);
            text_tprice = itemView.findViewById(R.id.component_Tprice);
            btnDel = itemView.findViewById(R.id.btnDel);

            cardView = itemView.findViewById(R.id.card_click);

            itemView.setOnClickListener(this);



        }
        @Override
        public void onClick(View v){
            SavedbuildII savedbuildII = savedList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, SavedListActivity.class);
            intent.putExtra("Savedbuild", savedbuildII);
            mCtx.startActivity(intent);

        }



    }
}
