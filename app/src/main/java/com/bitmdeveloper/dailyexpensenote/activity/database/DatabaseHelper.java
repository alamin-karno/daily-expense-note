package com.bitmdeveloper.dailyexpensenote.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Expense.db";
    private static final String TABLE_NAME = "expense";
    public static String COL_ID = "Id";
    public static String COL_TYPE = "type";
    public static String COL_AMOUNT = "expense_amount";
    public static String COL_DATE = "date";
    public static String COL_TIME = "time";
    public static String COL_DOC = "doc";
    private static final int VERSION = 1;

    private final String createTable = "create table "+TABLE_NAME+" ("+COL_ID+" Integer primary key autoincrement, "+COL_TYPE+" TEXT, "+COL_AMOUNT+" TEXT, "
            +COL_DATE+" TEXT, "+COL_TIME+" TEXT, "+COL_DOC+" TEXT)";
    private static final String DROP_TABLE = " DROP TABLE IF EXISTS "+TABLE_NAME;

    private final Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        try{
            sqLiteDatabase.execSQL(createTable);
        }
        catch (Exception e){
            Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }
        catch (Exception e){
            Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public long insertdata(String type,String amount,String date,String time,String doc){
        ContentValues values = new ContentValues();

        values.put(COL_TYPE,type);
        values.put(COL_AMOUNT,amount);
        values.put(COL_DATE,date);
        values.put(COL_TIME,time);
        values.put(COL_DOC,doc);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.insert(TABLE_NAME,null,values);
    }
    public long updatedata(String id,String type,String amount,String date,String time,String doc){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_TYPE,type);
        values.put(COL_AMOUNT,amount);
        values.put(COL_DATE,date);
        values.put(COL_TIME,time);
        values.put(COL_DOC,doc);

        return sqLiteDatabase.update(TABLE_NAME,values,"id = ?",new String[]{id});
    }
    public Cursor showData(){
        String getTable = "Select * From "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery(getTable,null);
    }

    public int deleteDataFromDatabase(int rowId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, COL_ID + "=" + rowId, null);
    }


    public Integer deleteData(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,COL_ID + "=" +id,null);
    }

    public Cursor getItemTypeData(String item_type) {

        String QUERY = "select * From "+TABLE_NAME+" where "+COL_TYPE+" = '"+item_type+"'";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery(QUERY,null);
    }

    public Cursor getSpecificData(String sql) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }
}
