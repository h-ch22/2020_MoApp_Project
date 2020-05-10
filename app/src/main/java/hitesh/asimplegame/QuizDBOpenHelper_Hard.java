package hitesh.asimplegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuizDBOpenHelper_Hard extends SQLiteOpenHelper {

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

    public QuizDBOpenHelper_Hard(Context context) {
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
        Question q1 = new Question("y=-2x를 y축의 방향으로 -3만큼 평행이동한 그래프의 식", "y=-2x-3", "y=-2x-1", "y=-2x+1", "y=-2x+3", "y=-2x-1", "y=-2x-3");
        addQuestion(q1);
        Question q2 = new Question("f(x)=x+2라 할 때 f(0)+f(1)의 값", "-1", "0", "1", "5", "10", "5");
        addQuestion(q2);
        Question q3 = new Question("f(x)=ax+b에서 f(1)=3이고 f(2)=5일 때, f(3)의 값", "-3", "0", "1", "4", "7", "7");
        addQuestion(q3);
        Question q4 = new Question("y=-3x+2가 (k, -1)을 지날 때, k의 값", "-1", "1", "2", "3", "5", "1");
        addQuestion(q4);
        Question q5 = new Question("y=-2x+1 위의 점이 아닌 것은?", "(-3,7)", "(-2,5)", "(0,1)", "(3/2,-2)", "(2,3)", "(2,3)");
        addQuestion(q5);
        Question q6 = new Question("y=-3x+3는 y=-3x-1의 그래프를 얼마만큼 y축 평행이동한 것인가?", "-4", "-2", "2", "3", "4", "4");
        addQuestion(q6);
        Question q7 = new Question("y=-2x+4 의 x절편을 a, y절편을 b라 할 때, a+b의 값", "2", "4", "6", "8", "10", "6");
        addQuestion(q7);
        Question q8 = new Question("y=x+k의 x절편이 -2일 때, k의 값", "-2", "-1", "0", "1", "2", "2");
        addQuestion(q8);
        Question q9 = new Question("x절편이 다른 하나는?", "y=x+2", "y=-1/2x-1", "y=2x-4", "y=-6+3x", "y=x-2", "y=x+2");
        addQuestion(q9);
        Question q10 = new Question("기울기가 3인 그래프가 (-3,k), (2,5)를 지날 때,k의 값", "-10", "-5", "0", "5", "10", "-10");
        addQuestion(q10);
        Question q11 = new Question("a<0,b>0일 때, y=-ax+b의 그래프가 지나지 않는 사분면", "1사분면", "2사분면", "3사분면", "4사분면", "모두지난다", "4사분면");
        addQuestion(q11);
        Question q12 = new Question("480 km를 시속 80 km의 속력으로 갈 때, 도착까지 걸린 시간", "6시간 30분", "6시간", "5시간 30분", "5시간", "4시간", "6시간");
        addQuestion(q12);
        Question q13 = new Question("20L의 석유를 10분마다 0.5L씩 연소한다. 90분 후에 남은 석유의 양", "14.5 L", "15 L", "15.5 L", "16 L", "16.5 L", "15.5 L");
        addQuestion(q13);
        Question q14 = new Question("두 개의 주사위의 나오는 수의 합이 6 또는 7이 되는 경우의 수", "11가지", "12가지", "13가지", "14가지", "15가지", "11가지");
        addQuestion(q14);
        Question q15 = new Question("5cm, 6cm, 7cm, 9cm, 10cm, 11cm 선분 중 3개를 골라 삼각형을 만들 때의 모든 경우의 수", "16가지", "17가지", "18가지", "19가지", "20가지", "19가지");
        addQuestion(q15);
        Question q16 = new Question("다섯 팀이 서로 한 번씩 시합을 가지려면 모두 몇 번의 시합", "5", "10", "15", "20", "25",  "10");
        addQuestion(q16);
        Question q17 = new Question("2/7을 소수로 나타낼 때 소수점 아래 100번째 숫자", "7", "5", "4", "2", "1", "7");
        addQuestion(q17);
        Question q18 = new Question("x/6 = 0.8333..일 때, x값", "4", "5", "6", "7", "8", "5");
        addQuestion(q18);
        Question q19 = new Question("남4명, 여3명 중에서 2명 뽑을 때, 적어도 1명은 여자일 확률", "2/7", "3/7", "4/7", "5/7", "6/7", "5/7");
        addQuestion(q19);
        Question q20 = new Question("0,1,2,3중에서 다른 두 개의 숫자로 두자리수를 만들 때, 그 수가 짝수일 확률", "1/12", "1/6", "1/4", "1/3", "5/12", "5/12");
        addQuestion(q20);
        Question q21 = new Question("70부터 89중에서 수를 택할 때, 일의자리 수가 십의자리의 수의 약수일 확률?", "1/10", "1/5", "3/10", "2/5", "1/2", "3/10");
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
