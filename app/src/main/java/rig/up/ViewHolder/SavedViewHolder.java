package rig.up.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.w3c.dom.Text;

import rig.up.Interface.ItemClickListener;
import rig.up.R;

public class SavedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_saved_name, txt_component_price;
    public CardView click_saved;

    private ItemClickListener itemClickListener;

    public void setTxt_saved_name(TextView txt_saved_name) {
        this.txt_saved_name = txt_saved_name;
    }

    public SavedViewHolder(View itemView){

        super(itemView);
        txt_saved_name = (TextView) itemView.findViewById(R.id.saved_name);
        txt_component_price = (TextView) itemView.findViewById(R.id.component_Tprice);
        click_saved = (CardView) itemView.findViewById(R.id.card_click);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View view){ itemClickListener.onClick(view, getAdapterPosition(),false);

    }
}
