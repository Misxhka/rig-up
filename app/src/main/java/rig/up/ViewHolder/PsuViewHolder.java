package rig.up.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rig.up.Interface.ItemClickListener;
import rig.up.R;

public class PsuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMoboName, txtCpuName, txtRamName, txtGpuName, txtStorageName, txtPsuName;

    private ItemClickListener itemClickListener;



    public PsuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMoboName = itemView.findViewById(R.id.search_mobo_spinner_psu);
        txtCpuName = itemView.findViewById(R.id.search_cpu_spinner_psu);
        txtRamName = itemView.findViewById(R.id.search_ram_spinner_psu);
        txtGpuName = itemView.findViewById(R.id.search_gpu_spinner_psu);
        txtStorageName = itemView.findViewById(R.id.search_storage_spinner_psu);
        txtPsuName = itemView.findViewById(R.id.search_psu_spinner_psu);


        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}