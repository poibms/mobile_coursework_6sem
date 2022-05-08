package com.example.project.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Database.DBHelper;
import com.example.project.Helper.SharedPreferencesHelper;
import com.example.project.R;
import com.example.project.api.OnFetchDataListener;
import com.example.project.api.requestManager.RequestAccountManager;
import com.example.project.models.Account;
import com.example.project.models.Register;

import retrofit2.Response;

public class Signup extends AppCompatActivity {

    EditText emailEdit, passwordEdit;
    String login, email, password;
    Button signUpBtn, signInActBtn;
    SharedPreferences sharedPreferences;
    private static final String KEY_ACCOUNT = "Account";

    private SQLiteDatabase db;
    private final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        setButtonListeners();
        db = new DBHelper(getApplicationContext()).getWritableDatabase();

    }

    public void initViews() {
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        signUpBtn = findViewById(R.id.signup_button);

        signInActBtn = findViewById(R.id.loginActivity_button);
    }

    public void bindingValues() {

        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
    }

    public void setButtonListeners() {
        signUpBtn.setOnClickListener(view -> {
            try {
//                bindingValues();

//                if(login.length() >=4 && login.length()<=15) {
//                    if (password.length() >= 4 && password.length() <= 15) {
                        Register register = new Register(emailEdit.getText().toString(), passwordEdit.getText().toString());

                        RequestAccountManager requestManager = new RequestAccountManager();
                        requestManager.register(responseListener, register);
//                    } else {
//                        Toast.makeText(this, "password length must be between 4 and 15 characters",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(this, "login length must be between 4 and 15 characters",
//                            Toast.LENGTH_SHORT).show();
//                }
            } catch (SQLiteConstraintException e) {
                Toast.makeText(this, "sql error",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Check your input values",
                        Toast.LENGTH_SHORT).show();
            }
        });
        signInActBtn.setOnClickListener(view -> {
//            startActivity(new Intent(this, signin.class));
            finish();
        });
    }

    private final OnFetchDataListener<Account> responseListener = new OnFetchDataListener<Account>() {
        @Override public void onFetchData(Response<Account> response) {
            if(response.isSuccessful()) {
                SharedPreferencesHelper.addAccount(context, response.body());
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(KEY_ACCOUNT, response.body().getToken()).apply();
//                startActivity(new Intent(context, MainActivity.class));
            } else {
                emailEdit.setError("Email already taken");

            }
        }
        @Override public void onFetchError(Throwable error) {
            Log.e(TAG, "Получено исключение", error);
        }
    };

    public void clearFields() {
        emailEdit.setText("");
        passwordEdit.setText("");
    }
}