

package com.codingstrings.sns.screens;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.codingstrings.sns.utils.components.PdfRendererBasicFragment;
import com.codingstrings.sns.R;

public class PdfActivity extends AppCompatActivity {

    Context context= null;
    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setContentView(R.layout.activity_main_real);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PdfRendererBasicFragment(),
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();
        }
    }

    public  Context mGetContext(){return this.context;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                new AlertDialog.Builder(this)
                        .setMessage("Credits: \nKrishna Agrawal\n" +
                                "Ojas Chaudhari\n" +
                                "Sanket Kadam")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
