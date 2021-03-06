package com.example.yuwei.killexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.yuwei.killexam.tools.Task;

import java.util.ArrayList;

/**
 * Created by yuwei on 15/2/18.
 */
//TODO: 将业务逻辑和数据库剥离
public class MyDatabaseHelper extends SQLiteOpenHelper{


    private static String DATABASE_NAME;

    private static final String TABLE_NAME = "task";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String FINISH_TIME = "finish_time";
    private static final String SPEND_HOURS = "spend_time_hours";
    private static final String SPEND_MINUTES = "spend_time_minutes";
    private static final String CONTEXT = "context";
    private static final String REMIND_METHOD = "remind_method";
    private static final String ATTRIBUTE = "attribute";
    private static final String HAS_BELONG = "has_belong";
    private static final String BELONG_NAME = "belong";
    private static final String HAS_FINISHED = "has_finished";


    private static final String CREATE_TASK_TABLE = "create table task (" +
            ID + " integer primary key autoincrement, " +
            NAME + " text, " +
            FINISH_TIME + " integer, " +
            SPEND_HOURS + " integer, " +
            SPEND_MINUTES + " integer, " +
            CONTEXT + " text, " +
            REMIND_METHOD + " text, " +
            ATTRIBUTE + " text, " +
            HAS_BELONG + " integer, " +
            BELONG_NAME + " text, " +
            HAS_FINISHED + " integer);";


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        DATABASE_NAME = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static void writeNewTask(Context context, Task task){
        SQLiteOpenHelper databaseHelper = new MyDatabaseHelper(context,"task.db", null, 1);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        insertNewTask(database, task);
    }

    private static void insertNewTask(SQLiteDatabase database, Task task){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, task.getTaskName());
        contentValues.put(FINISH_TIME, task.getFinishedTime().toString());
        contentValues.put(SPEND_HOURS, task.getSpendHours());
        contentValues.put(SPEND_MINUTES, task.getSpendMinutes());
        contentValues.put(CONTEXT, task.getTaskContext());
        contentValues.put(REMIND_METHOD, task.getRemindMethod().getSelectedName());
        contentValues.put(ATTRIBUTE, task.getTaskAttribute().getSelectedName());

        if (task.isHasBelong()){
            contentValues.put(HAS_BELONG, 1);
            contentValues.put(BELONG_NAME, task.getBelongName());
        }
        else{
            contentValues.put(HAS_BELONG, 0);
            contentValues.put(BELONG_NAME, "NULL");
        }
        contentValues.put(HAS_FINISHED, 0);

        database.insert(TABLE_NAME, null, contentValues);


    }


    public static boolean checkNameHasExist(Context context, String name){
        SQLiteOpenHelper databaseHelper = new MyDatabaseHelper(context, "task.db", null, 1);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        return checkName(database, name);
    }

    private static boolean checkName(SQLiteDatabase database, String name){
        String where = NAME + "='" + name + "'";

        Cursor cursor = database.query(TABLE_NAME, null, where, null, null, null, null);
        if (cursor.getCount()>0){
            return true;
        }
        return false;
    }

    public static ArrayList<String> getBelongTasksNames(Context context, String belongTasksAttribute){

        SQLiteOpenHelper databaseHelper = new MyDatabaseHelper(context, "task.db", null, 1);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        return getBelongTasksNamesFromDatabase(database, belongTasksAttribute);
    }

    private static ArrayList<String> getBelongTasksNamesFromDatabase(SQLiteDatabase database, String belongTasksAttribute){
        ArrayList<String> belongTasksNames = new ArrayList<String>();

        String where = ATTRIBUTE + "='" + belongTasksAttribute + "'";

        Cursor cursor = database.query(TABLE_NAME, new String[]{NAME}, where, null, null, null, null);

        while (cursor.moveToNext()){

            String belongTaskName = cursor.getString(cursor.getColumnIndex(NAME));

            belongTasksNames.add(belongTaskName);
        }

        return belongTasksNames;
    }


}
