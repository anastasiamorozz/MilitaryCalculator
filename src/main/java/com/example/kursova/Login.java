package com.example.kursova;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextView textViewSignUp;

    public static ArrayList<User> users = new ArrayList<User>();

    public ArrayList<User> loadUsers() {
        try {
            FileInputStream fis = getBaseContext().openFileInput("user.dat");
            ObjectInputStream is = new ObjectInputStream(fis);
            users = (ArrayList<User>) is.readObject();
            is.close();
            fis.close();
        }  catch (FileNotFoundException e){
            Toast.makeText(getApplicationContext(), "Не вдалось знайти файл. Спробуйте знову!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Не вдалось відкрити базу користувачів. Спробуйте знову!", Toast.LENGTH_SHORT).show();
        }
        catch (ClassNotFoundException e){
            Toast.makeText(getApplicationContext(), "Помилка при вході", Toast.LENGTH_SHORT).show();
        }
        return users;
    }

    public boolean login(String email, String password) {
        loadUsers();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                Toast.makeText(getApplicationContext(), "Вітаємо з успішним входом!", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        Toast.makeText(getApplicationContext(), "Не вдалось знайти користувача з такими email та паролем. Спробуйте знову!", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                if (login(email, password)){
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}