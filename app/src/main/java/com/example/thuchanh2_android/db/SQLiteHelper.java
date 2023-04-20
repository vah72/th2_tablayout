package com.example.thuchanh2_android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.core.database.sqlite.SQLiteDatabaseKt;

import com.example.thuchanh2_android.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ChiTieu.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table items(" +
                " id integer primary key autoincrement, " +
                "title text, category text, price text, date text)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        super.onOpen(sqLiteDatabase);
    }

    public List<Item> getAllItems(){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        //ngay giam dan
        String order = "date DESC";
        Cursor rs = st.query("items", null, null, null, null, null, order);
        while (rs!=null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0),rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

    public long addItem(Item item){
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("items", null, values);
    }

    public List<Item> getByDate(String date){
        List<Item> list = new ArrayList<>();
        String where = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where, whereArgs, null, null, null);
        while(rs!=null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0), rs.getString(1),
                    rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

    public int update(Item item){
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase st = getWritableDatabase();
        String where = "id=?";
        String[] args = {String.valueOf(item.getId())};
        return st.update("items", values, where, args);
    }

    public int delete(int id){
        String where = "id=?";
        String[] args = {String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("items", where, args);
    }

    public List<Item> searchByCategory(String category){
        List<Item> list = new ArrayList<>();
        String where = "category like ?";
        String[] args = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where, args, null, null, null);
        while(rs!=null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

    public List<Item> searchByTitle(String title){
        List<Item> list = new ArrayList<>();
        String where = "title like ?";
        String[] args = {"%"+title.trim()+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where, args, null, null, null);
        while(rs!=null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

    public List<Item> searchByDateFromTo(String from, String to){
        List<Item> list = new ArrayList<>();
        String where = "date between ? and ?";
        String order = "date desc";
        String[] whereArgs = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, where, whereArgs, null, null, order);
        while(rs!=null && rs.moveToNext()){
            list.add(new Item(rs.getInt(0), rs.getString(1),
                    rs.getString(2), rs.getString(3), rs.getString(4)));
        }
        return list;
    }

}
