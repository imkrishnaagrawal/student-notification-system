package com.codingstrings.sns.screens;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.database.Cursor;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codingstrings.sns.sql.Hint;
import com.codingstrings.sns.utils.components.NotificationHandler;
import com.codingstrings.sns.utils.http.HttpRequest;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.codingstrings.sns.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class  LoginActivity extends AppCompatActivity implements Validator.ValidationListener{


    @NotEmpty
    AutoCompleteTextView email_login;

    @NotEmpty
    @Length(min = 6)
    EditText password_login;




    JSONObject jsonObject;

    Validator validator;

    Button signin;
    Button register;

    Context context;

    SQLiteDatabase writeAble,readAble;
    Hint sqLiteDatabase;

    String []emailArray;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startService(new Intent(this, NotificationHandler.class));

        this.context = getApplicationContext();
        jsonObject = new JSONObject();


        FirebaseMessaging.getInstance().subscribeToTopic("ComputerDepartment");

        sqLiteDatabase = new Hint(this);
        writeAble      = sqLiteDatabase.getWritableDatabase();
        readAble       = sqLiteDatabase.getReadableDatabase();

        email_login = (AutoCompleteTextView) findViewById(R.id.email);
        password_login = (EditText) findViewById(R.id.password);


        signin = (Button) findViewById(R.id.button_signin);
        register = (Button) findViewById(R.id.button_goto_register);

        validator = new Validator(this);
        validator.setValidationListener(this);

        emailArray = getAppCategoryDetail();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,emailArray);
        email_login.setAdapter(adapter);


    }


    public void goToRegister(View view){
                Intent registerPage = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerPage);
    }

    public void signIn(View view){
                validator.validate();
    }



    @Override
    public void onValidationSucceeded() {
        String email_log = email_login.getText().toString();
        String password_log = password_login.getText().toString();

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email_log);
        params.put("password" ,password_log);
        String url = "login";
        new HttpRequest().Request(getApplication(),params,url);
        saveToDB(email_log);
    }

    private void saveToDB(String user) {

        ContentValues values = new ContentValues();
        String email;

        if (user.contains("@")) { email = user;  user = ""; }
        else { email = ""; }

        values.put(Hint.COLUMN_USER_NAME, user);
        values.put(Hint.COLUMN_EMAIL, email);
        long newRowId = writeAble.insert(Hint.TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            view.requestFocus();
                // Display error messages ;)
            if (view instanceof EditText)
            { ((EditText) view).setError(message); }
            else
            { Toast.makeText(this, message, Toast.LENGTH_LONG).show(); }
        }
    }

    public String[] getAppCategoryDetail() {

        String selectQuery = "SELECT  username,email FROM " + Hint.TABLE_NAME;
        Cursor cursor      = readAble.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();

        while (cursor.moveToNext()) {
            if (!cursor.getString(0).isEmpty()) {
                if(!list.contains(cursor.getString(0)))
                    list.add(cursor.getString(0));
            }
                if (!cursor.getString(1).isEmpty()) {
                    if(!list.contains(cursor.getString(1)))
                        list.add(cursor.getString(1));
            }
        }

        cursor.close();

        return list.toArray(new String[list.size()]);
    }


}
