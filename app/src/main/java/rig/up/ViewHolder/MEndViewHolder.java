package rig.up.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rig.up.Interface.ItemClickListener;
import rig.up.R;

public class MEndViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMoboName, txtCpuName, txtRamName, txtGpuName, txtStorageName, txtCasingName, txtPsuName;

    private ItemClickListener itemClickListener;



    public MEndViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMoboName = itemView.findViewById(R.id.search_mobo_spinner_mid);
        txtCpuName = itemView.findViewById(R.id.search_cpu_spinner_mid);
        txtRamName = itemView.findViewById(R.id.search_ram_spinner_mid);
        txtGpuName = itemView.findViewById(R.id.search_gpu_spinner_mid);
        txtStorageName = itemView.findViewById(R.id.search_storage_spinner_mid);
        txtCasingName = itemView.findViewById(R.id.search_casing_spinner_mid);
        txtPsuName = itemView.findViewById(R.id.search_psu_spinner_mid);


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