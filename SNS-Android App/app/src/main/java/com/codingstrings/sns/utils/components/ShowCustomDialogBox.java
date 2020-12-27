package com.codingstrings.sns.utils.components;

import android.app.Dialog;
import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.codingstrings.sns.R;

import static com.codingstrings.sns.R.layout.dialog_batch;
import static com.codingstrings.sns.R.layout.dialog_branch;
import static com.codingstrings.sns.R.layout.dialog_division;
import static com.codingstrings.sns.R.layout.dialog_gender;
import static com.codingstrings.sns.R.layout.dialog_year_of_engineering;

/**
 * Created by krishna on 9/5/17.
 */

public class ShowCustomDialogBox {

    Dialog dialog= null;
    Context context=null;
    EditText text = null;
    int id ;


    RadioGroup radioGroup;
    RadioButton btn1 , btn2, btn3;
    Button Ok,Cancel;

    public ShowCustomDialogBox(Context context,View view,int id){

        this.text = (EditText) view;
        this.context = context;
        this.id = id;



    }

    public void show(){
        dialog = new Dialog(context);
        switch (id){

            case dialog_gender: dialog.setContentView(dialog_gender);
                radioGroup       =  dialog.findViewById(R.id.genderRadioGroup);
                Cancel           =  dialog.findViewById(R.id.genderCancel);
                Ok               =  dialog.findViewById(R.id.genderOk);
                break;

            case dialog_branch: dialog.setContentView(dialog_branch);

                radioGroup       =  dialog.findViewById(R.id.branchRadioGroup);
                Cancel           =  dialog.findViewById(R.id.branchCancel);
                Ok               =  dialog.findViewById(R.id.branchOk);
                break;
            case dialog_year_of_engineering: dialog.setContentView(dialog_year_of_engineering);

                radioGroup       =  dialog.findViewById(R.id.yearRadioGroup);
                Cancel           =  dialog.findViewById(R.id.yearCancel);
                Ok               =  dialog.findViewById(R.id.yearOk);
                break;
            case dialog_division : dialog.setContentView(dialog_division);

                radioGroup       =  dialog.findViewById(R.id.divisionRadioGroup);
                Cancel           =  dialog.findViewById(R.id.divisionCancel);
                Ok               =  dialog.findViewById(R.id.divisionOk);
                break;
            case dialog_batch: dialog.setContentView(dialog_batch);

                radioGroup       =  dialog.findViewById(R.id.batchRadioGroup);
                Cancel           =  dialog.findViewById(R.id.batchCancel);
                Ok               =  dialog.findViewById(R.id.batchOk);
                break;



        }


        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        getValue();
    }

    public void getValue()
    {
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioSelected = dialog.findViewById(radioGroup.getCheckedRadioButtonId());
                if(radioSelected!=null) {
                    Toast.makeText(context, radioSelected.getText(), Toast.LENGTH_LONG).show();
                    text.setText(radioSelected.getText());
                }
                dialog.dismiss();
            }
        });
    }




}
