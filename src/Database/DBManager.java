package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/01/2016.
 */
public class DBManager {
    private static final String DB_PATH =
            Environment.getDataDirectory().getPath() + "/data/com.example.AilatrieuphuVer2/databases";

    private static final String DB_NAME = "Question";
    private static final String TAG = "DBManager";
    private Context mContext;

    private SQLiteDatabase sqlDB;

    private List<QuestionItem> arrQuestions = new ArrayList<QuestionItem>();

    public DBManager(Context context) {
        mContext = context;
        copyDB();
    }

    private void copyDB() {

        try {
            //1. Tao folder databases trong duong dan DB_PATH
            new File(DB_PATH).mkdir();

            File file = new File(DB_PATH + "/" + DB_NAME);
            if (file.exists())
                return;

            file.createNewFile();
            InputStream input = mContext.getAssets().open(DB_NAME);
            FileOutputStream output = new FileOutputStream(file);

            int len;
            byte buff[] = new byte[1024];
            //Do du lieu vao mang buff
            len = input.read(buff);
            while (len > 0) {
                output.write(buff, 0, len);
                len = input.read(buff);
            }
            input.close();
            output.close();
            Log.i(TAG, "DB is copied to internal path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDB() {
        if (sqlDB == null || !sqlDB.isOpen())
            sqlDB = SQLiteDatabase.openDatabase(DB_PATH + "/" + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDB() {
        if (sqlDB != null && sqlDB.isOpen()) {
            sqlDB.close();
            sqlDB = null;
        }
    }

    public ArrayList<QuestionItem> getQuestions() {
        arrQuestions.clear();
        //Mo DB
        openDB();
        Cursor cursor = sqlDB.rawQuery(SQLConst.SQL_GET_QUESTIONS, null);
        if (cursor == null)
            return null;

        QuestionItem itemQuestion;

        int indexID, indexQuestion, indexCaseA, indexCaseB, indexCaseC, indexCaseD, indexTrueCase;
        indexID = cursor.getColumnIndex("_id");
        indexQuestion = cursor.getColumnIndex("question");
        indexCaseA = cursor.getColumnIndex("casea");
        indexCaseB = cursor.getColumnIndex("caseb");
        indexCaseC = cursor.getColumnIndex("casec");
        indexCaseD = cursor.getColumnIndex("cased");
        indexTrueCase = cursor.getColumnIndex("truecase");

        String question, caseA, caseB, caseC, caseD;
        int id, trueCase;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            id = cursor.getInt(indexID);
            question = cursor.getString(indexQuestion);
            caseA = cursor.getString(indexCaseA);
            caseB = cursor.getString(indexCaseB);
            caseC = cursor.getString(indexCaseC);
            caseD = cursor.getString(indexCaseD);
            trueCase = cursor.getInt(indexTrueCase);
            itemQuestion = new QuestionItem(id, question, caseA, caseB, caseC, caseD, trueCase);

//            Log.i(TAG, itemQuestion.toString());
            arrQuestions.add(itemQuestion);
            cursor.moveToNext();
        }
        return (ArrayList) arrQuestions;
    }

}
