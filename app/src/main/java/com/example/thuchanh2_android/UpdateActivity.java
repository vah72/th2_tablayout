package com.example.thuchanh2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thuchanh2_android.db.SQLiteHelper;
import com.example.thuchanh2_android.model.Item;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    public Spinner category_spinner;
    private EditText title, price, date;
    private Button btnUpdate, btnRemove, btnBack;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initView();

        Intent intent = getIntent();
        item = (Item)intent.getSerializableExtra("item");
        title.setText(item.getTitle());
        price.setText(item.getPrice());
        date.setText(item.getDate());
        int pos = 0;
        for(int i = 0; i<category_spinner.getCount(); i++){
            if(category_spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                pos=i;
                break;
            }
        }
        category_spinner.setSelection(pos);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        date.setText(str_date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteHelper db = new SQLiteHelper(UpdateActivity.this);
                String t = title.getText().toString();
                String p = price.getText().toString();
                String c = category_spinner.getSelectedItem().toString();
                String d = date.getText().toString();
                if ( !t.isEmpty() && p.matches("\\d+")){
                    int id = item.getId();
                    Item item = new Item(id, t, c, p, d);
                    System.out.println(t + p + c + d);
                    db.update(item);
                    finish();
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = item.getId();
                SQLiteHelper db = new SQLiteHelper(UpdateActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muon xoa " + id + " khong ?");
                builder.setIcon(R.drawable.icon_remove);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(id);
                        finish();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // noop
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    private void initView(){
        category_spinner = findViewById(R.id.add_spinner);
        title = findViewById(R.id.enter_title_txt);
        price = findViewById(R.id.enter_price_txt);
        date =  findViewById(R.id.enter_date_txt);
        btnUpdate = findViewById(R.id.update_btn);
        btnRemove = findViewById(R.id.remove_btn);
        btnBack = findViewById(R.id.back_btn);

    }
}