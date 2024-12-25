package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PHONE";
    public static final String TABLE_FOOD = "food";
    public static final String COL_FOOD_ID = "ID";
    public static final String COL_FOOD_USER_ID = "USER_ID"; // Изменено на USER_ID
    public static final String COL_FOOD_DATE = "DATE";
    public static final String COL_FOOD_NAME = "FOOD_NAME";
    public static final String COL_FOOD_GRAMS = "GRAMS";
    public static final String COL_FOOD_CALORIES = "CALORIES";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, EMAIL TEXT, PHONE TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, DATE TEXT, FOOD_NAME TEXT, GRAMS INTEGER, CALORIES INTEGER)");
    }

    public boolean insertFoodData(int userId, String date, String foodName, int grams, int calories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FOOD_USER_ID, userId);
        contentValues.put(COL_FOOD_DATE, date);
        contentValues.put(COL_FOOD_NAME, foodName);
        contentValues.put(COL_FOOD_GRAMS, grams);
        contentValues.put(COL_FOOD_CALORIES, calories);
        long result = db.insert(TABLE_FOOD, null, contentValues);
        return result != -1;
    }

    public Cursor getFoodDataByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FOOD + " WHERE USER_ID=?", new String[]{String.valueOf(userId)});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String login, String pass, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, login);
        contentValues.put(COL_3, pass);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, phone);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String login, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME=? AND PASSWORD=?", new String[]{login, pass});
        return cursor.getCount() > 0;
    }
}



