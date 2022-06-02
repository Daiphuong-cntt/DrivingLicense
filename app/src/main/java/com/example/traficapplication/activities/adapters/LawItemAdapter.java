package com.example.traficapplication.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.traficapplication.R;
import com.example.traficapplication.activities.activities.LawDetailListActivity;
import com.example.traficapplication.activities.models.ItemLaw;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LawItemAdapter extends RecyclerView.Adapter<LawItemAdapter.ItemLawViewHolder> {
    private Context itemLawContext;
    private List<ItemLaw> itemLaw;

    public LawItemAdapter(Context itemLawContext, List<ItemLaw> itemLaw) {
        this.itemLawContext = itemLawContext;
        this.itemLaw = itemLaw;
    }

    @NonNull
    @Override
    public LawItemAdapter.ItemLawViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_law, parent, false);
        return new LawItemAdapter.ItemLawViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LawItemAdapter.ItemLawViewHolder holder, int position) {
        ItemLaw itemL = itemLaw.get(position);
        int a = position;
        if (itemL == null) {
            return;
        }
        holder.imgItemLaw.setImageResource(itemL.getImgItemLaw());
        holder.tittleItemLaw.setText(itemL.getTittleItemLaw());
        holder.contentItemLaw.setText(itemL.getContentItemLaw());
        holder.detailItemLaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int img = itemL.getImgItemLaw();
                String tittle = itemL.getTittleItemLaw();
                String content = itemL.getContentItemLaw();
                String detail = itemL.getDetailItemLaw();
                Intent intent = new Intent(itemLawContext, LawDetailListActivity.class);
                intent.putExtra("img",img);
                intent.putExtra("tittle",tittle);
                intent.putExtra("content",content);
                intent.putExtra("detail",detail);
                itemLawContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (itemLaw != null) {
            return itemLaw.size();
        }
        return 0;
    }


    public class ItemLawViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imgItemLaw;
        private TextView tittleItemLaw,contentItemLaw,detailItemLaw;
        private ConstraintLayout constraintLayout;
        public ItemLawViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemLaw = itemView.findViewById(R.id.img_item_list_law);
            tittleItemLaw = itemView.findViewById(R.id.tv_law_name);
            contentItemLaw = itemView.findViewById(R.id.tv_law_content);
            detailItemLaw = itemView.findViewById(R.id.tv_law_detail);
            constraintLayout = itemView.findViewById(R.id.item_list_law);
        }
    }

}
