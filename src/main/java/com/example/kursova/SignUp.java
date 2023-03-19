package com.example.kursova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextName, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSignUp;
    TextView textViewLogin;

    static long newRowId;

    public boolean validateEmail(String email) {
        boolean isValid = true;

        // перевірка на відповідність стандарту email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.setError("Неправильний формат email");
            isValid = false;
        }

        // перевірка на унікальність email
        if (isValid) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columns = {DatabaseHelper.KEY_EMAIL};
            String selection = DatabaseHelper.KEY_EMAIL + "=?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            cursor.close();

            if (count > 0) {
                textInputEditTextEmail.setError("Користувач з таким email вже існує");
                isValid = false;
            }
        }

        return isValid;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //метод забезпечує видимість всіх елементів
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        textInputEditTextEmail = findViewById(R.id.emailInputEditLayout);
        textInputEditTextName = findViewById(R.id.nameRegTextInputEditText);
        textInputEditTextPassword = findViewById(R.id.passwordInputEditLayout);
        buttonSignUp = findViewById(R.id.button);
        textViewLogin = findViewById(R.id.textView6);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Код для переходу на іншу активність
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        buttonSignUp.setOnClickListener(view -> {

            String name = textInputEditTextName.getText().toString().trim();
            String email = textInputEditTextEmail.getText().toString().trim();
            String password = textInputEditTextPassword.getText().toString().trim();

            boolean isValid = true;

            // перевірка введених даних
            if (TextUtils.isEmpty(name)) {
                textInputEditTextName.setError("Введіть ім'я");
                isValid = false;
            }

            if (TextUtils.isEmpty(email)) {
                textInputEditTextEmail.setError("Введіть email");
                isValid = false;
            } else {
                if (!validateEmail(email)) {
                    isValid = false;
                }
            }

            if (TextUtils.isEmpty(password)) {
                textInputEditTextPassword.setError("Введіть пароль");
                isValid = false;
            }

            if (isValid){// Створюємо об'єкт користувача зі значеннями ім'я, email та пароль
                User user = new User(name, email, password);

                // Додаємо користувача до бази даних
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                //Перевірка на існування таблиці користувачів
                databaseHelper.addUser(user, db);


                if (newRowId != -1) {
                    // Реєстрація пройшла успішно, показуємо повідомлення користувачеві
                    Toast.makeText(getApplicationContext(), "Реєстрація пройшла успішно!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Якщо вставка не вдалася, виводимо повідомлення про помилку
                    Toast.makeText(getApplicationContext(), "Помилка при реєстрації", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}
