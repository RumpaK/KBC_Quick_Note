package kbc.cognizant.com.kbc1.db;

/**
 * Created by cts_mobility5 on 3/15/16.
 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import kbc.cognizant.com.kbc1.model.NoteItem;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "KBC.db";
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_LAST_MOD_DATE = "last_mod_date";
    public static final String TAG = "DBHelper";



    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                   "create table  " +TABLE_NAME +
                   "("+COLUMN_ID+" integer primary key, "+COLUMN_TITLE+" text," +
                   COLUMN_DESC+" text," +
                   COLUMN_LAST_MOD_DATE +" text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    /**
     * insert data from note table
     * @param
     * @return
     */
    public boolean insertNote  (String title, String desc, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESC, desc);
        contentValues.put(COLUMN_LAST_MOD_DATE, date);
        long rowId = db.insert(TABLE_NAME, null, contentValues);
        Log.e(TAG, "" + rowId);

        db.close();
        return true;
    }

    /**
     * update data in note table
     * @param id
     * @return
     */

    public boolean updateNote (Integer id, String title, String desc , String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESC, desc);
        contentValues.put(COLUMN_LAST_MOD_DATE, date);
        db.update(TABLE_NAME, contentValues, COLUMN_ID+" = ? ", new String[] { Integer.toString(id) } );
        db.close();
        return true;
    }

    /**
     * delete data from note table
     * @param id
     * @return
     */
    public Integer deleteNote (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRowId =  db.delete(TABLE_NAME,
                                COLUMN_ID+" = ? ",
                                new String[] { Integer.toString(id) });

        Log.e(TAG, "delete " + deletedRowId);

        db.close();
        return deletedRowId;
    }

    /**
     * read data from note table
     * @return
     */
    public ArrayList<NoteItem> getAllNotes()
    {
        ArrayList<NoteItem> array_list = new ArrayList<NoteItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){

            int id = res.getInt(res.getColumnIndex(COLUMN_ID));
            String title = res.getString(res.getColumnIndex(COLUMN_TITLE));
            String desc  = res.getString(res.getColumnIndex(COLUMN_DESC));
            String mod_date  = res.getString(res.getColumnIndex(COLUMN_LAST_MOD_DATE));
            NoteItem item = new NoteItem(id,title,desc,mod_date);

            array_list.add(item);
            Log.e(TAG ,"read "+ title);
            res.moveToNext();
        }

        db.close();
        return array_list;
    }

    /**
     * fetch note corresponding to id
     * @param id
     * @return
     */
    public NoteItem getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,COLUMN_TITLE, COLUMN_DESC,
                                                            COLUMN_LAST_MOD_DATE },
                                        COLUMN_ID + "=?",
                                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        NoteItem note = new NoteItem(Integer.parseInt(cursor.getString(0)),
                                               cursor.getString(1),
                                               cursor.getString(2),
                                               cursor.getString(3));

        return note;
    }

}