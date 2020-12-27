package com.codingstrings.sns.screens;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.google.firebase.iid.FirebaseInstanceId;
import com.codingstrings.sns.utils.components.CustomDatePickerDialog;
import com.codingstrings.sns.utils.components.ShowCustomDialogBox;
import com.codingstrings.sns.utils.http.HttpRequest;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.codingstrings.sns.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by krishna on 8/30/17.
 */

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {


    int year, month, day;
    static final int DIALOG_ID = 0;
    private Calendar myCalendar;
    final Context context = this;

    //Personal Info
    @Length(min = 2 ,max = 40)
    @NotEmpty(message = "Cannot Be Empty")
    EditText firstName;

    @NotEmpty(message =  "Cannot Be Empty")
    @Length(min = 2 ,max = 40)
    EditText lastName;

    @Length(min = 10 , max = 10 ,message ="Enter Valid Mobile Number")
    EditText mobileNumber;

    @NotEmpty(message = "Cannot Be Empty")
    EditText dateOfBirth;
    @NotEmpty(message = "Cannot Be Empty")
    EditText gender ;
    @NotEmpty(message = "Cannot Be Empty")
    EditText address;
    @NotEmpty(message = "Cannot Be Empty")
    EditText city;

    @Length(min = 12 , max = 12 ,message ="Enter Valid Aadhaar Number")
    EditText aadhaarNumber;

    //College Info
    @NotEmpty(message = "Cannot Be Empty")
    EditText branch;
    @NotEmpty(message = "Cannot Be Empty")
    EditText yearOfEngineering;
    @NotEmpty(message = "Cannot Be Empty")
    EditText division;
    @NotEmpty(message = "Cannot Be Empty")
    EditText batch;
    @NotEmpty(message = "Cannot Be Empty")

    @Max(value = 200)
    @Min(value = 1)
    EditText rollNumber;

    //SignUp
    @NotEmpty(message = "Cannot Be Empty")
    @Email
    EditText email;
    @NotEmpty(message = "Cannot Be Empty")
    EditText userName;


    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC , message = "Min Length 6 must Contains Alphabets And Numbers")
    EditText password;
    @ConfirmPassword
    EditText confirmPassword;


    //Buttons
    Button submit ;
    Button goToLoginPage;

    Validator validator;

    Dialog dialog;
    // Button btn;
    String refreshedToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Log.i(String.valueOf(1), "onCreate: "+refreshedToken);

        //Personal Info
        firstName       = (EditText) findViewById(R.id.firstname);
        lastName        = (EditText) findViewById(R.id.lastname);
        mobileNumber    = (EditText) findViewById(R.id.mobile_no);
        dateOfBirth     = (EditText) findViewById(R.id.date_picker);

        gender          = (EditText) findViewById(R.id.gender);
        address         = (EditText) findViewById(R.id.address);
        city            = (EditText) findViewById(R.id.city);
        aadhaarNumber   = (EditText) findViewById(R.id.aadhaar);

        //College Info
        branch              = (EditText) findViewById(R.id.branch);
        yearOfEngineering   = (EditText) findViewById(R.id.year);
        division            = (EditText) findViewById(R.id.division);
        batch               = (EditText) findViewById(R.id.batch);
        rollNumber          = (EditText) findViewById(R.id.roll_no);


        //SignUp
        email             = (EditText) findViewById(R.id.email_register_form);
        userName          = (EditText) findViewById(R.id.username_register_form);
        password          = (EditText) findViewById(R.id.password_register_form);
        confirmPassword   = (EditText) findViewById(R.id.confirm_password);


        // Buttons
        submit          = (Button) findViewById(R.id.button_register);
        goToLoginPage   = (Button) findViewById(R.id.button_goto_login);


        //editTextGender = (EditText) findViewById(R.id.gender);
        //editTextFromDate = (EditText) findViewById(R.id.date_picker);


        new CustomDatePickerDialog(RegisterActivity.this,dateOfBirth);
        new ShowCustomDialogBox(RegisterActivity.this,gender,(R.layout.dialog_gender)).show();

        new ShowCustomDialogBox(RegisterActivity.this,branch,(R.layout.dialog_branch)).show();
        new ShowCustomDialogBox(RegisterActivity.this,yearOfEngineering,(R.layout.dialog_year_of_engineering)).show();
        new ShowCustomDialogBox(RegisterActivity.this,division,(R.layout.dialog_division)).show();
        new ShowCustomDialogBox(RegisterActivity.this,batch,(R.layout.dialog_batch)).show();


        //To make dialog Boxes non-editable

        dateOfBirth.setClickable(true);
        dateOfBirth.setFocusable(false);

        gender.setClickable(true);
        gender.setFocusable(false);

        branch.setClickable(true);
        branch.setFocusable(false);

        yearOfEngineering.setClickable(true);
        yearOfEngineering.setFocusable(false);

        division.setClickable(true);
        division.setFocusable(false);

        batch.setClickable(true);
        batch.setFocusable(false);

        validator = new Validator(this);
        validator.setValidationListener(this);



    }

    public void goToLogin(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    public void onSubmit(View view){ validator.validate(); }

    @Override
    public void onValidationSucceeded() {


        Map<String, String> params = new HashMap<String, String>();
        String url = "register";

            params.put("firstname",firstName.getText().toString());
            params.put("lastname",lastName.getText().toString());
            params.put("mobile",mobileNumber.getText().toString());
            params.put("dob",dateOfBirth.getText().toString());

            params.put("gender",gender.getText().toString());
            params.put("address",address.getText().toString());
            params.put("city",city.getText().toString());
            params.put("aadhaar",aadhaarNumber.getText().toString());

            params.put("branch",branch.getText().toString());
            params.put("year",yearOfEngineering.getText().toString());
            params.put("division",division.getText().toString());
            params.put("batch",batch.getText().toString());

            params.put("rollno",rollNumber.getText().toString());
            params.put("email",email.getText().toString());
            params.put("username",userName.getText().toString());
            params.put("password",password.getText().toString());
            if(refreshedToken == null){ refreshedToken = "INVALID" ;}
            params.put("firebasetoken",refreshedToken.toString());

        new HttpRequest().Request(getApplication(),params,url);


    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            view.requestFocus();
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

        }
    }


}
