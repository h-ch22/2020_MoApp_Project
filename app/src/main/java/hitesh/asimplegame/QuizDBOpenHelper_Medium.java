package hitesh.asimplegame;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBOpenHelper_Medium extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mathsone";
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
        Question q1 = new Question("18+42 = ?", "55", "58", "59", "60", "70", "60");
        addQuestion(q1);
        Question q2 = new Question("75+53 = ?", "125", "126", "127", "128", "118", "128");
        addQuestion(q2);
        Question q3 = new Question("32+97 = ?", "99", "109", "119", "129", "139", "129");
        addQuestion(q3);
        Question q4 = new Question("63+85 = ?", "138", "148", "158", "168", "178", "148");
        addQuestion(q4);
        Question q5 = new Question("76+48 = ?", "124", "134", "144", "154", "164", "124");
        addQuestion(q5);
        Question q6 = new Question("75-42 = ?", "23", "33", "43", "53", "63", "33");
        addQuestion(q6);
        Question q7 = new Question("90-31 = ?", "19", "29", "39", "49", "59", "59");
        addQuestion(q7);
        Question q8 = new Question("72-17 = ?", "55", "45", "35", "25", "15", "55");
        addQuestion(q8);
        Question q9 = new Question("52-25 = ?", "25", "26", "27", "28", "29", "27");
        addQuestion(q9);
        Question q10 = new Question("89-73 = ?", "13", "14", "15", "16", "17", "16");
        addQuestion(q10);
        Question q11 = new Question("72*78 = ?", "5616", "5626", "5636", "5646", "5656", "5616");
        addQuestion(q11);
        Question q12 = new Question("38*32 = ?", "1196", "1206", "1216", "1226", "1236", "1216");
        addQuestion(q12);
        Question q13 = new Question("19*11 = ?", "199", "209", "219", "229", "239", "209");
        addQuestion(q13);
        Question q14 = new Question("27*23 = ?", "581", "591", "601", "611", "621", "621");
        addQuestion(q14);
        Question q15 = new Question("62*42 = ?", "2584", "2594", "2604", "2614", "2624", "2604");
        addQuestion(q15);
        Question q16 = new Question("32*72 = ?", "2304", "2314", "2324", "2334", "2344",  "2304");
        addQuestion(q16);
        Question q17 = new Question("774/9 = ?", "82", "83", "84", "85", "86", "86");
        addQuestion(q17);
        Question q18 = new Question("112/4 = ?", "27", "28", "29", "30", "31", "28");
        addQuestion(q18);
        Question q19 = new Question("498/6 = ?", "83", "84", "85", "86", "87", "83");
        addQuestion(q19);
        Question q20 = new Question("129/3 = ?", "40", "41", "42", "43", "44", "43");
        addQuestion(q20);
        Question q21 = new Question("152/8 = ?", "19", "20", "21", "22", "23", "19");
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
