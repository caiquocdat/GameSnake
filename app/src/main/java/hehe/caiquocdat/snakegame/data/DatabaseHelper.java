package hehe.caiquocdat.snakegame.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hehe.caiquocdat.snakegame.model.HistoryModel;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "score_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SCORE = "scores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_SCORE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SCORE + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    public void insertScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TABLE_SCORE + " (" + COLUMN_SCORE + ") VALUES (" + score + ")";
        db.execSQL(insertQuery);
        db.close();
    }
    public ArrayList<HistoryModel> getAllScores() {
        ArrayList<HistoryModel> scoreList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORE));

                HistoryModel scoreModel = new HistoryModel(id, score);
                scoreList.add(scoreModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return scoreList;
    }

}