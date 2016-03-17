package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sergio on 16/03/2016.
 */
public class HighScore extends SQLiteOpenHelper {
    private static final String DATA_NAME = "HighScoreDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "TableScore";
    private static final String KEY_ID = "_id";
    private static final String KEY_SCORE = "_score";

    public HighScore(Context context) {
        super(context, DATA_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE "
                + TABLE_NAME + "( " + KEY_ID + " INTEGER  PRIMARY KEY, " + KEY_SCORE + " INTEGER)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String delete = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(delete);
        onCreate(db);
    }

    public void addScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, score);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Integer> getAllScore() {
        ArrayList<Integer> arrScore = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int score = cursor.getInt(1);
                arrScore.add(score);
            } while (cursor.moveToNext());
        }
        Collections.sort(arrScore);
        return arrScore;

    }


}
