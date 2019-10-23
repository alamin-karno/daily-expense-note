package com.bitmdeveloper.dailyexpensenote.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "Expense.db";
    private static String TABLE_NAME = "expense";
    public static String COL_ID = "Id";
    public static String COL_TYPE = "type";
    public static String COL_AMOUNT = "expense_amount";
    public static String COL_DATE = "date";
    public static String COL_TIME = "time";
    public static String COL_DOC = "doc";
    private static int VERSION = 1;

    private String createTable = "create table "+TABLE_NAME+" ("+COL_ID+" Integer primary key autoincrement, "+COL_TYPE+" TEXT, "+COL_AMOUNT+" TEXT, "
            +COL_DATE+" TEXT, "+COL_TIME+" TEXT, "+COL_DOC+" TEXT)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertdata(String type,String amount,String date,String time,String doc){
        ContentValues values = new ContentValues();
        values.put(COL_TYPE,type);
        values.put(COL_AMOUNT,amount);
        values.put(COL_DATE,date);
        values.put(COL_TIME,time);
        values.put(COL_DOC,doc);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Long id = sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();
        return id;
    }
    public Cursor showData(){
        String getTable = "Select * From "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(getTable,null);
        return cursor;
    }
    public Integer deleteData(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteID = db.delete(TABLE_NAME,COL_ID + "=" +id,null);
        return deleteID;
    }
    public Cursor getSpecificData(String sql) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }
}
