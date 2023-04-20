package com.example.thuchanh2_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thuchanh2_android.R;
import com.example.thuchanh2_android.UpdateActivity;
import com.example.thuchanh2_android.adapter.RecycleViewAdapter;
import com.example.thuchanh2_android.db.SQLiteHelper;
import com.example.thuchanh2_android.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentToday extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter = new RecycleViewAdapter();;
    private SQLiteHelper db;
    private TextView total_txt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.today_recycler_view);
        total_txt = view.findViewById(R.id.total_txt);
        db = new SQLiteHelper(getContext());
        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(date.format(d));
        if (list.size()==0){
            total_txt.setText("Hom nay khong co chi tieu");
        }
        else {
            adapter.setList(list);
            total_txt.setText("Tong tien: " + tong(list));
        }
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    private long tong(List<Item> list){
        long t = 0;
        for (Item i : list){
            t += Integer.parseInt(i.getPrice());
        }
        return t;
    }

    @Override
    public void onItemClick(View view, int pos) {
        Item item = adapter.getItem(pos);
        Intent intent = new Intent(getActivity(), UpdateActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(date.format(d));
        if (list.size()==0){
            adapter.setList(list);
            total_txt.setText("Hom nay khong co chi tieu");
        }
        else {
            adapter.setList(list);
            total_txt.setText("Tong tien: " + tong(list));
        }
    }
}
