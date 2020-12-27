package com.codingstrings.sns.screens;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.codingstrings.sns.sql.SqlAssignments;
import com.codingstrings.sns.sql.SqlNotice;
import com.codingstrings.sns.sql.SqlToken;
import com.codingstrings.sns.sql.SqlWriteUps;
import com.codingstrings.sns.utils.http.HttpRequestAssignments;
import com.codingstrings.sns.utils.http.HttpRequestNotice;
import com.codingstrings.sns.utils.http.HttpRequestWriteups;
import com.google.android.material.navigation.NavigationView;
import com.codingstrings.sns.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteDatabase readAble = null;
    private SQLiteDatabase writeAble = null;
    private SqlToken sqLiteDatabase = null;
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sqLiteDatabase = new SqlToken(SplashScreenActivity.mGetContext());
        readAble       = sqLiteDatabase.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + SqlToken.TABLE_NAME ;
        Cursor cursor      = readAble.rawQuery(selectQuery, null);


        Log.d("HomeActivityToken", " Cursor Count: "+cursor.getCount());

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            token =  cursor.getString(1);
            Log.d("Home2ActivityToken", "Token: "+token);
            cursor.close();
        }
        readAble.close();

        new HttpRequestAssignments().Request();
        new HttpRequestNotice().Request();
        new HttpRequestWriteups().Request();

    }

    public void assignments(View view){

        new HttpRequestAssignments().Request();
        Intent assignments = new Intent(this, AssignmentActivity.class);
        startActivity(assignments);

    }
    public void writeups(View view){

        new HttpRequestWriteups().Request();
        Intent writeup = new Intent(this, WriteUpsActivity.class);
        startActivity(writeup);

    }
    public void notices(View view){

        new HttpRequestNotice().Request();
        Intent notice = new Intent(this, NoticeActivity.class);
        startActivity(notice);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {

            SqlAssignments a = new SqlAssignments(SplashScreenActivity.mGetContext());
            SqlNotice n = new SqlNotice(SplashScreenActivity.mGetContext());
            SqlWriteUps w = new SqlWriteUps(SplashScreenActivity.mGetContext());

            writeAble       = sqLiteDatabase.getWritableDatabase();

            SQLiteDatabase awriteAble   = a.getWritableDatabase();
            SQLiteDatabase nwriteAble   = n.getWritableDatabase();
            SQLiteDatabase wwriteAble   = w.getWritableDatabase();


            int count1 = awriteAble.delete(SqlAssignments.TABLE_NAME,null,null);
            int count2 = nwriteAble.delete(SqlNotice.TABLE_NAME,null,null);
            int count3 = wwriteAble.delete(SqlWriteUps.TABLE_NAME,null,null);

            int count = writeAble.delete(SqlToken.TABLE_NAME,null,null);
            Log.d("Logout Count", "Logout: "+count);

            awriteAble.close();
            nwriteAble.close();
            wwriteAble.close();

            writeAble.close();


            Intent i = new Intent(HomeActivity.this, SplashScreenActivity.class);
            startActivity(i);
           }
        else if (id == R.id.nav_credits) {
            Intent i = new Intent(HomeActivity.this, CreditsActivity.class);
            startActivity(i);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
