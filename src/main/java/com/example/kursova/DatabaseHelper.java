package com.example.kursova;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper{
    // Ім'я бази даних
    private static final String DATABASE_NAME = "mydatabase";
    // Версія бази даних
    private static final int DATABASE_VERSION = 1;
    // Ім'я таблиці для зберігання даних про користувачів
    public static final String TABLE_USERS = "users";
    // Ідентифікатор користувача
    public static final String KEY_ID = "id";
    // Ім'я користувача
    public static final String KEY_NAME = "name";
    // Електронна адреса користувача
    public static final String KEY_EMAIL = "email";
    // Пароль користувача
    public static final String KEY_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + ")";

    private static final String TAG = "SignUp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Підключення до бази даних
            String url = "jdbc:mysql://localhost:3307/mydatabase";
            String username = "root";
            String password = "";
            Connection conn = DriverManager.getConnection(url, username, password);
            Log.i(TAG, "Connection established.");

            // Створення таблиці користувачів
            String createUsersTableQuery = "CREATE TABLE " + TABLE_USERS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT, "
                    + KEY_EMAIL + " TEXT, "
                    + KEY_PASSWORD + " TEXT"
                    + ")";

            db.execSQL(createUsersTableQuery);
            Log.i(TAG, "Table created.");

            // Закриваємо з'єднання з базою даних
            conn.close();
            Log.i(TAG, "Connection closed.");
        } catch (SQLException e) {
            Log.e(TAG, "SQL exception occurred: " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Видалення таблиці при оновленні бази даних
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("ALTER TABLE " + TABLE_USERS + " RENAME TO " + "temp_" + TABLE_USERS);
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT NOT NULL, " +
                KEY_EMAIL + " TEXT NOT NULL UNIQUE, " +
                KEY_PASSWORD + " TEXT NOT NULL);");
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + KEY_NAME + ", " + KEY_EMAIL + ", " + KEY_PASSWORD + ") " +
                "SELECT " + KEY_NAME + ", " + KEY_EMAIL + ", " + KEY_PASSWORD + " FROM temp_" + TABLE_USERS);
        db.execSQL("DROP TABLE temp_" + TABLE_USERS);

    }

    // Додавання даних про користувача до бази даних
    public void addUser(User user, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        SignUp.newRowId = db.insert(TABLE_USERS, null, values);
    }


    // Отримання даних про користувача з бази даних за його ідентифікатором
    public User getUserById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID,
                        KEY_NAME, KEY_EMAIL, KEY_PASSWORD }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return user;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID};
        String selection = KEY_EMAIL + " = ?" + " AND " + KEY_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }



}
