package com.example.kursova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Objects;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextName, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSignUp;
    TextView textViewLogin;
    final String fileName = "user.dat";
    User user;

    static long newRowId;

    public boolean validateEmail(String email) {
        boolean isValid = true;

        // перевірка на відповідність стандарту email
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEditTextEmail.setError("Неправильний формат email");
            isValid = false;
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

            if (isValid){
                // Створення об'єкту користувача з введеними даними
                user = new User(name, email, password);
                Login.users.add(user);
                try {
                    FileOutputStream fos = getBaseContext().openFileOutput(fileName, Context.MODE_PRIVATE);
                    ObjectOutputStream os = new ObjectOutputStream(fos);
                    os.writeObject(Login.users);
                    os.close();
                    fos.close();

                    // Повідомлення про успішну серіалізацію
                    Toast.makeText(getApplicationContext(), "Реєстрація пройшла успішно", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e){
                    Toast.makeText(getApplicationContext(), "Не вдалось знайти файл. Спробуйте знову!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Не вдалось створити користувача. Спробуйте знову!", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
}
