package rig.up.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rig.up.Interface.ItemClickListener;
import rig.up.R;

public class ComponentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView comp_name;
    public ImageView comp_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ComponentViewHolder(View itemView) {
        super(itemView);

        comp_name = itemView.findViewById(R.id.info_part_name);
        comp_image = itemView.findViewById(R.id.info_part_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}

