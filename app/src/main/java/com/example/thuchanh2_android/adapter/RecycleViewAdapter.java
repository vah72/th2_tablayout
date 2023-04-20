package com.example.thuchanh2_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchanh2_android.R;
import com.example.thuchanh2_android.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.TodayViewHolder> {

    private ItemListener itemListener;
    private List<Item> list;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public List<Item> getList() {
        notifyDataSetChanged();
        return list;
    }


    public void setList(List<Item> list) {
        notifyDataSetChanged();
        this.list = list;
    }

    public Item getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TodayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayViewHolder holder, int position) {
        Item item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.category.setText(item.getCategory());
        holder.price.setText(item.getPrice());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TodayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, category, price, date;
        public TodayViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_item_txt);
            category = itemView.findViewById(R.id.category_item_txt);
            price = itemView.findViewById(R.id.price_item_txt);
            date = itemView.findViewById(R.id.date_item_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int pos);
    }
}
