package com.example.agendamento.ui;

import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VH>{
    private final List<String> items;
    public SimpleAdapter(List<String> items){this.items=items;}
    public static class VH extends RecyclerView.ViewHolder{public VH(TextView v){super(v);}}
    @Override public VH onCreateViewHolder(ViewGroup p,int t){
        TextView v=new TextView(p.getContext());v.setPadding(16,20,16,20);v.setTextSize(16);return new VH(v);
    }
    @Override public void onBindViewHolder(VH h,int i){((TextView)h.itemView).setText(items.get(i));}
    @Override public int getItemCount(){return items.size();}
}
