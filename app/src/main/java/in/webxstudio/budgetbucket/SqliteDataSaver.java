package in.webxstudio.budgetbucket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

//todo here you have to change all the entries such as column name and table name according to your need

public class SqliteDataSaver extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String TAG = "SqliteDataSaver";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "budget.db";

    private int expenseIndex,typeIndex,dateIndex,noteIndex;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseColumns.TABLE_NAME+"("+DatabaseColumns.COLUMN_NAME_EXPENSE+" INT, " +
                    ""+DatabaseColumns.COLUMN_NAME_TYPE+" TEXT, "+DatabaseColumns.COLUMN_DATE+" TEXT, "+DatabaseColumns.COLUMN_NOTE+" TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseColumns.TABLE_NAME;



    SqliteDataSaver(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }



    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }




    //TODO send date here in YYYY-MM-DD format like 2016-10-14 or 2018-08-15
    boolean addData(Context context,int expense,String date,String note,String type){
        SqliteDataSaver saver = new SqliteDataSaver(context);

        db = saver.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseColumns.COLUMN_NAME_EXPENSE, expense);
        values.put(DatabaseColumns.COLUMN_DATE, date);
        values.put(DatabaseColumns.COLUMN_NAME_TYPE,type);
        values.put(DatabaseColumns.COLUMN_NOTE,note);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseColumns.TABLE_NAME, null, values);

        Log.d(TAG, "addData: data saved");
        //this returns -1 if there occurred any error
        //if there was no error it returns entry's table entry number
        return newRowId == -1;
    }



    ArrayList<BudgetModel> getData(Context context){
        ArrayList<BudgetModel> data = new ArrayList<>();

        SqliteDataSaver saver = new SqliteDataSaver(context);

        db=saver.getReadableDatabase();

        Cursor cursor = db.query(DatabaseColumns.TABLE_NAME,null,null,null,null,null,null,null);

        getValues(cursor);

        while (cursor.moveToNext()){
            int expense = cursor.getInt(expenseIndex);
            String type = cursor.getString(typeIndex);
            String date = cursor.getString(dateIndex);
            String note = cursor.getString(noteIndex);

            BudgetModel model = new BudgetModel(expense,type,date,note);
            data.add(model);
        }
        cursor.close();

        db.close();
        //this returns all the values that are currently present in the table
        return data;
    }


    //you have to pass year as well as month of which you want to get data
    //month should be from 01 - 12
    //year should be like 2009 or 2018 or 2006
    int getMonthlyTotal(Context context , int month, int year){
        int data=0;

        SqliteDataSaver saver = new SqliteDataSaver(context);

        db=saver.getReadableDatabase();

        String query = "SELECT SUM("+DatabaseColumns.COLUMN_NAME_EXPENSE+") as total FROM "+DatabaseColumns.TABLE_NAME+"" +
                        " WHERE  strftime('%m',"+DatabaseColumns.COLUMN_DATE+")=\'"+month+"\' AND strftime('%Y',"+DatabaseColumns.COLUMN_DATE+")=\'"+year+"\';";

        Log.d(TAG, "getMonthlyTotal: query is "+query);
        Cursor cursor = db.rawQuery(query,null);

        int totalIndex = cursor.getColumnIndex("total");

        while (cursor.moveToNext()){
            data = cursor.getInt(totalIndex);
        }
        cursor.close();

        db.close();

        return data;
    }

    private void getValues(Cursor cursor){
        expenseIndex = cursor.getColumnIndexOrThrow(DatabaseColumns.COLUMN_NAME_EXPENSE);
        typeIndex = cursor.getColumnIndexOrThrow(DatabaseColumns.COLUMN_NAME_TYPE);
        dateIndex = cursor.getColumnIndexOrThrow(DatabaseColumns.COLUMN_DATE);
        noteIndex = cursor.getColumnIndexOrThrow(DatabaseColumns.COLUMN_NOTE);
    }

}
