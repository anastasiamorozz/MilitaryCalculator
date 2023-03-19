package com.example.kursova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    TextView textViewSignUp;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // створюємо об'єкт DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        TextInputEditText emailInput = findViewById(R.id.emailInputEditLayout);
        TextInputEditText passwordInput = findViewById(R.id.passwordInputEditLayout);
        Button loginButton = findViewById(R.id.button);


        textViewSignUp = findViewById(R.id.textView6);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Код для переходу на іншу активність
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        // встановлюємо обробник події для кнопки входу
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // зчитуємо введені дані
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                // перевіряємо, чи існує користувач з таким email та паролем
                boolean userExists = dbHelper.checkUser(email, password);

                // якщо користувач існує, повідомляємо про вхід
                if (userExists) {
                    Toast.makeText(Login.this, "Успішний вхід!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // якщо користувача не знайдено, повідомляємо про помилку
                    Toast.makeText(Login.this, "Невірний email або пароль!", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // закриваємо з'єднання з базою даних перед закриттям активності
        dbHelper.close();
    }
}