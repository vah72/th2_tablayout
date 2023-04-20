package com.example.thuchanh2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.thuchanh2_android.db.SQLiteHelper;
import com.example.thuchanh2_android.model.Item;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
   public Spinner category_spinner;
   private EditText title, price, date;
   private Button btnUpdate, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = title.getText().toString();
                String p = price.getText().toString();
                String c = category_spinner.getSelectedItem().toString();
                String d = date.getText().toString();
                if ( !t.isEmpty() && p.matches("\\d+")){
                    Item item = new Item(t, c, p, d);
                    SQLiteHelper db = new SQLiteHelper(AddActivity.this);
                    db.addItem(item);
                    finish();
                }
            }
        });

    }


    private void initView(){
        category_spinner = findViewById(R.id.add_spinner);
        title = findViewById(R.id.enter_title_txt);
        price = findViewById(R.id.enter_price_txt);
        date =  findViewById(R.id.enter_date_txt);
        btnUpdate = findViewById(R.id.update_btn);
        btnCancel = findViewById(R.id.cancel_btn);

    }
}