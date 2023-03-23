package com.example.myvoca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    final String TAG = "DBHelper";

    public Context context;

    public static final String TABLE_NAME = "VOCAB";
    public static final String ID = "_id";
    public static final String DATE = "date";
    public static final String WORD = "word";
    public static final String DEFINITION = "definition";
    public static final String URLFROM = "urlfrom";

    static final String DB_NAME = "vocab.db";
    static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE " + TABLE_NAME + "(");
        stringBuffer.append(ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuffer.append(DATE + " TEXT, ");
        stringBuffer.append(WORD + " TEXT, ");
        stringBuffer.append(DEFINITION + " TEXT, ");
        stringBuffer.append(URLFROM + " TEXT);");

        Log.d(TAG, stringBuffer.toString());
        db.execSQL(stringBuffer.toString());
        //Message.message(context, "DB 생성 완료");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            //Message.message(context, "onUpgrade");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertData(String word, String definition, String urlfrom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // 금일 날짜 가져오기
        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        values.put(DATE, format.format(dateNow));

        values.put(WORD, word);
        values.put(DEFINITION, definition);
        values.put(URLFROM, urlfrom);

        long result = db.insert(TABLE_NAME, null, values);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " order by _id desc", null);
        return cursor;
    }

    public boolean updateData(String id, String word, String definition, String urlfrom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, "20220618");
        values.put(WORD, word);
        values.put(DEFINITION, definition);
        values.put(URLFROM, urlfrom);
        db.update(TABLE_NAME, values, "ID = ?", new String[] { id });
        return true;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "name = ?", new String[] {id});
    }

}
