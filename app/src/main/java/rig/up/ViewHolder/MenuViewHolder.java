package rig.up.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rig.up.Interface.ItemClickListener;
import rig.up.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView txtMenuName;
   public ImageView imageView;

   private ItemClickListener itemClickListener;



    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = itemView.findViewById(R.id.info_part_name);
        imageView = itemView.findViewById(R.id.info_part_image);

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
