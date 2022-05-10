package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.project.Database.DBHelper;
import com.example.project.Helper.SharedPreferencesHelper;
import com.example.project.R;
import com.example.project.api.OnFetchDataListener;
import com.example.project.api.requestManager.RequestAccountManager;
import com.example.project.models.Account;
import com.example.project.models.Register;

import retrofit2.Response;

public class signin extends AppCompatActivity {
        EditText emailEdit, passwordEdit;
    String email, password;
    private SQLiteDatabase db;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userInfo";
    private static final String KEY_ID = "userId";
    private static final String KEY_ACCOUNT = "Account";

    private final Context context = this;

    private final OnFetchDataListener<Account> responseListener = new OnFetchDataListener<Account>() {
        @Override public void onFetchData(Response<Account> response) {
            if(response.isSuccessful()) {
                SharedPreferencesHelper.addAccount(context, response.body());
                JWT parsedJWT = new JWT(response.body().getToken());
                Claim subscriptionMetaData = parsedJWT.getClaim("status");
                String parsedValue = subscriptionMetaData.asString();
                if(parsedValue != "BANNED") {
                    startActivity(new Intent(context, MainActivity.class));
                } else {
                    emailEdit.setError("user with such email was banned!");
                }

            } else if(response.code() == 404) {
                emailEdit.setError("Account with this email not exist");
            } else if(response.code() == 401) {
                passwordEdit.setError("Invalid password");
            }
        }
        @Override public void onFetchError(Throwable error) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initViews();
        db = new DBHelper(getApplicationContext()).getReadableDatabase();
        if(SharedPreferencesHelper.isLogin(context)) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void initViews() {
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
    }

    public void bindingValues() {
        email = emailEdit.getText().toString();
        password = passwordEdit.getText().toString();
    }

    public void signUpActivity_btn(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void signin_btn(View view) {
        try {
            Register login = new Register(emailEdit.getText().toString(), passwordEdit.getText().toString());

            RequestAccountManager requestManager = new RequestAccountManager();
            requestManager.login(responseListener, login);
        } catch (Exception e) {
            Toast.makeText(this, "Something was wrong", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkInputs(String email, String password){
        if( email.equals("") || password.equals("")){
            Toast.makeText(this, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}