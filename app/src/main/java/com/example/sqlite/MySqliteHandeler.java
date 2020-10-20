package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MySqliteHandeler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "computer.db";
    private static final String TABLE_COMPUTER = "Computers";

    private static final String COLOUMN_ID = "id";
    private static final String COLOUMN_COMPUTER_NAME = "ComputerName";
    private static final String COLOUMN_COMPUTER_TYPE = "ComputerType";

    String CREATE_COMPUTER_TABLE = " CREATE_TABLE " + TABLE_COMPUTER + "(" + COLOUMN_ID +
            "INTEGER vPRIMARY KEY" + COLOUMN_COMPUTER_NAME + " TEXT, " + COLOUMN_COMPUTER_TYPE + " TEXT" + ")";
    public MySqliteHandeler (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COMPUTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPUTER );
        onCreate(sqLiteDatabase);
    }

    public void addComputer(Computer computer){
        SQLiteDatabase database = MySqliteHandeler.this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLOUMN_COMPUTER_NAME,computer.getComputername());
        values.put(COLOUMN_COMPUTER_TYPE,computer.getComputertype());

        database.insert(TABLE_COMPUTER,null,values);
        database.close();

    }
    public Computer getComputer(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_COMPUTER,new String[]{COLOUMN_ID,COLOUMN_COMPUTER_NAME,COLOUMN_COMPUTER_TYPE},
                COLOUMN_ID + "=?", new String[] {String.valueOf(id)},null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Computer computer = new Computer((Integer.parseInt(cursor.getString(0))),cursor.getString(1),cursor.getString(2));
        return computer;
    }
    public List<Computer> getAllComputer(){
        List<Computer> Computerlist = new ArrayList<>();

        String selectAllQuery = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectAllQuery,null);
        if (cursor.moveToFirst()){
            do {
                Computer computer = new Computer();
                computer.setId(Integer.parseInt(cursor.getString(0)));
                computer.setComputername(cursor.getString(1));
                computer.setComputertype(cursor.getString(2));

                Computerlist.add(computer);

            }while (cursor.moveToFirst());
        }
       return Computerlist;
    }
    public int updatecomputer(Computer computer){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLOUMN_COMPUTER_NAME,computer.getComputername());
        values.put(COLOUMN_COMPUTER_TYPE,computer.getComputertype());

        return database.update(TABLE_COMPUTER,values,COLOUMN_ID + " = ? ",new String[]
                {String.valueOf(computer.getId())});
    }
    public void deleteComputer(Computer computer){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_COMPUTER,COLOUMN_ID + " = ? ",new String[]{String.valueOf(computer.getId())});
        database.close();
    }
    public int getComputerscount(){
        String computerscount = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(computerscount,null);
        cursor.close();
        return cursor.getCount();
    }
}
