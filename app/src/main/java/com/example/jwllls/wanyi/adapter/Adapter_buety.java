package com.example.jwllls.wanyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jwllls.wanyi.R;
import com.example.jwllls.wanyi.bean.photo;


import java.util.List;

/**
 * Created by jwllls on 2016/12/7.
 */

public class Adapter_buety extends RecyclerView.Adapter<Adapter_buety.buty_holder> {
    private LayoutInflater inflater;
    private List<photo.buty> mdatas;
    private Context context;
    View view;

    public Adapter_buety(Context context, List<photo.buty> mdatas) {
        this.context = context;
        this.mdatas = mdatas;
    }

    @Override
    public buty_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.from(context).inflate(R.layout.buety_cv_item, parent, false);
        return new buty_holder(view);
    }

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    @Override
    public void onBindViewHolder(buty_holder holder, final int position) {
        String str = "http://tnfs.tngou.net/img" + mdatas.get(position).getImg();
        Glide.with(context).load(str).diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.img);
        holder.text.setText(mdatas.get(position).getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent it = new Intent(context, BuetyList.class);
                it.putExtra("id",mdatas.get(position).getId());
                context.startActivity(it);*/
            }
        });

    }


    class buty_holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text;

        public buty_holder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.item_img);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

}
