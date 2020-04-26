package hitesh.asimplegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizDBOpenHelper_Medium extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsone_medium";
    // tasks table name
    private static final String TABLE_QUEST = "quest";
    // tasks Table Columns names
    private static final String KEY_ID = "qid";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer"; // correct option
    private static final String KEY_OPTA = "opta"; // option a
    private static final String KEY_OPTB = "optb"; // option b
    private static final String KEY_OPTC = "optc"; // option c
    private static final String KEY_OPTD = "optd";
    private static final String KEY_OPTE = "opte";

    private SQLiteDatabase database;

    public QuizDBOpenHelper_Medium(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT, "
                + KEY_OPTD + " TEXT, " + KEY_OPTE + " TEXT)";
        db.execSQL(sql);
        addQuestion();
        // db.close();
    }

    private void addQuestion() {
        Log.d("Add : ", "Adding ..");
        Question q1 = new Question("5+2 = ?", "7", "8", "6", "9", "10", "7");
        addQuestion(q1);
        Question q2 = new Question("2+18 = ?", "18", "19", "20", "21", "22", "20");
        addQuestion(q2);
        Question q3 = new Question("10-3 = ?", "6", "7", "8", "9", "10", "7");
        addQuestion(q3);
        Question q4 = new Question("5+7 = ?", "12", "13", "14", "15", "16", "12");
        addQuestion(q4);
        Question q5 = new Question("3-1 = ?", "1", "3", "2", "5", "7", "2");
        addQuestion(q5);
        Question q6 = new Question("0+1 = ?", "1", "0", "10", "2", "11", "1");
        addQuestion(q6);
        Question q7 = new Question("9-9 = ?", "0", "9", "1", "3", "2", "0");
        addQuestion(q7);
        Question q8 = new Question("3+6 = ?", "8", "7", "9", "10", "11", "9");
        addQuestion(q8);
        Question q9 = new Question("1+5 = ?", "6", "7", "5", "8", "9", "6");
        addQuestion(q9);
        Question q10 = new Question("7-5 = ?", "3", "2", "6", "5", "7", "2");
        addQuestion(q10);
        Question q11 = new Question("7-2 = ?", "7", "6", "5", "4", "3", "5");
        addQuestion(q11);
        Question q12 = new Question("3+5 = ?", "8", "7", "5", "9", "10", "8");
        addQuestion(q12);
        Question q13 = new Question("0+6 = ?", "7", "6", "5", "4", "8", "6");
        addQuestion(q13);
        Question q14 = new Question("12-10 = ?", "1", "2", "3", "4", "5", "2");
        addQuestion(q14);
        Question q15 = new Question("12+2 = ?", "14", "15", "16", "17", "18", "14");
        addQuestion(q15);
        Question q16 = new Question("2-1 = ?", "2", "1", "0", "3", "4",  "1");
        addQuestion(q16);
        Question q17 = new Question("6-6 = ?", "6", "12", "0", "1", "2", "0");
        addQuestion(q17);
        Question q18 = new Question("5-1 = ?", "4", "3", "2", "5", "6", "4");
        addQuestion(q18);
        Question q19 = new Question("4+2 = ?", "6", "7", "5", "4", "8", "6");
        addQuestion(q19);
        Question q20 = new Question("5+1 = ?", "6", "7", "5", "4", "1", "6");
        addQuestion(q20);
        Question q21 = new Question("5-4 = ?", "5", "4", "1", "2", "0", "1");
        addQuestion(q21);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        // Create tables again
        onCreate(db);
    }

    // Adding new question
    public void addQuestion(Question quest) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());
        values.put(KEY_OPTD, quest.getOPTD());
        values.put(KEY_OPTE, quest.getOPTE());

        // Inserting Row
        database.insert(TABLE_QUEST, null, values);
    }

    public List<Question> getAllQuestions() {
        List<Question> quesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        Log.d("Reading : ", "Reading contacts...");
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quest.setOPTD(cursor.getString(6));
                quest.setOPTE(cursor.getString(7));

                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }
}
