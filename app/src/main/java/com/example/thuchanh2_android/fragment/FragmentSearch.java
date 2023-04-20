package com.example.thuchanh2_android.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.SearchView;
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

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment {

    private EditText to_txt, from_txt;
    private SearchView searchView;
    private TextView total_txt;
    private Spinner category_spinner;
    private Button search_btn;
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        recyclerView = view.findViewById(R.id.search_recycler_view);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list = db.getAllItems();
        adapter.setList(list);
        total_txt.setText("Tong tien :" + tong(list));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Item> list = db.searchByTitle(newText);
                total_txt.setText("Tong tien: " + tong(list));
                adapter.setList(list);
                return true;
            }
        });

        from_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String str_date = "";
                        if(m>8 && d>8)
                            str_date += d+ "/" + (m+1)+"/"+y;
                        else if (m>8 && d<8) {
                            str_date += "0" + d+ "/" + (m+1)+"/"+y;
                        }
                        else if (m<8 && d>8) {
                            str_date += d+ "/0" + (m+1)+"/"+y;
                        }else str_date += "0" + d+"/0" + (m+1)+"/"+y;
                        from_txt.setText(str_date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        to_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String str_date = "";
                        if(m>8 && d>8)
                            str_date += d+ "/" + (m+1)+"/"+y;
                        else if (m>8 && d<8) {
                            str_date += "0" + d+ "/" + (m+1)+"/"+y;
                        }
                        else if (m<8 && d>8) {
                            str_date += d+ "/0" + (m+1)+"/"+y;
                        }else str_date += "0" + d+"/0" + (m+1)+"/"+y;
                        to_txt.setText(str_date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cate = category_spinner.getItemAtPosition(i).toString();
                List<Item> list;
                if( !cate.equalsIgnoreCase("All")){
                    list = db.searchByCategory(cate);
                } else list = db.getAllItems();
                adapter.setList(list);
                total_txt.setText("Tong tien: " + tong(list));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = from_txt.getText().toString();
                String to = to_txt.getText().toString();
                if(!from.isEmpty() && !to.isEmpty()){
                    List<Item> list = db.searchByDateFromTo(from, to);
                    System.out.println(from + to);
                    adapter.setList(list);
                    total_txt.setText("Tong tien: " + tong(list));
                }
            }
        });

    }

    private long tong(List<Item> list){
        long t = 0;
        for (Item i : list){
            t += Integer.parseInt(i.getPrice());
        }
        return t;
    }


    private void initView(View view){
        to_txt = view.findViewById(R.id.to_txt);
        from_txt = view.findViewById(R.id.from_txt);
        total_txt = view.findViewById(R.id.total_txt);
        searchView = view.findViewById(R.id.search_view);
        category_spinner = view.findViewById(R.id.category_spinner);
        search_btn = view.findViewById(R.id.search_btn);
        String[] cate = getResources().getStringArray(R.array.category);
        String[] cate1 = new String[cate.length+1];
        cate1[0] = "All";
        for (int i = 0; i<cate.length; i++)
            cate1[i+1] = cate[i];
        category_spinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, cate1));
    }

}
