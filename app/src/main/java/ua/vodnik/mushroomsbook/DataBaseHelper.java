package ua.vodnik.mushroomsbook;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;


public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DB_NAME = "mushrooms.db";
    private SQLiteDatabase myDataBase;
    private  static final int DATABASE_VERSION = 11;
    private Context myContext;
    private static String DB_PATH = "/data/data/ua.vodnik.mushroomsbook/databases/";
    public static final String TABLE_NAME = "mushrooms_table";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("База даних існує");
            try {
                open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("База данних не існує!");
            createdatabase();
        }
    }

    public void createdatabase() {
        boolean dbexist = checkdatabase();
        if(dbexist) {
            //System.out.println("База данних існує");
        } else {
            this.getReadableDatabase();
            copydatabase();
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Бази даних не існує!");
        }
        return checkdb;
    }

    public void copydatabase()
    {
        Log.i("Database",
                "Нова база даних копіюється на девайс!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;

        InputStream myInput = null;
        try
        {
            myInput = myContext.getAssets().open(DB_NAME);

            myOutput = new FileOutputStream(DB_PATH + DB_NAME);
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "Нова база даних скопійована на девайс");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // opening connection
    public void open() throws SQLException {
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    // close connection
    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    public String getInfromation(int id) {
        myDataBase = this.getReadableDatabase();

        String selection = "_id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id+1)};
        Cursor cursor = myDataBase.query(TABLE_NAME , null , selection , selectionArgs , null , null , null);
        String str = null;
        if(cursor.moveToFirst() && cursor.getCount() >= 1) {
            do {
                str = cursor.getString(cursor.getColumnIndexOrThrow("mushroom"));
            } while (cursor.moveToNext());
        }
        myDataBase.close();
        return str;


    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}