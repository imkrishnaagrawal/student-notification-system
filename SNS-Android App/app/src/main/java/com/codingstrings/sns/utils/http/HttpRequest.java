package com.codingstrings.sns.utils.http;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.codingstrings.sns.screens.HomeActivity;
import com.codingstrings.sns.screens.LoginActivity;
import com.codingstrings.sns.screens.SplashScreenActivity;
import com.codingstrings.sns.sql.SqlToken;
import com.codingstrings.sns.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by root on 10/8/17.
 */

public class HttpRequest {
    SQLiteDatabase writeAble,readAble;
    SqlToken sqLiteDatabase;

    public HttpRequest(){
        sqLiteDatabase = new SqlToken(SplashScreenActivity.mGetContext());
        writeAble      = sqLiteDatabase.getWritableDatabase();
    }

    public void Request(final Context context, Map params,final String type) {
        String url = context.getResources().getString(R.string.http_endpoint) + "/"+type;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //pDialog.hide();
                        try {
                            if(response.get("success").toString()== "true"){

                             switch (type) {
                                 case "login":

                                     Log.d("JSONPost", response.get("token").toString());
                                     final ContentValues values = new ContentValues();
                                     values.put(SqlToken.COLUMN_TOKEN, response.get("token").toString());
                                     long newRowId = writeAble.insert(SqlToken.TABLE_NAME, null, values);
                                     Log.d("httpRequest", "Inserted Successfully: " + newRowId);

                                     writeAble.close();

                                     Intent home = new Intent(context, HomeActivity.class);
                                     home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                     context.startActivity(home);
                                     break;
                                 case"register":
                                     Intent login = new Intent(context, LoginActivity.class);
                                     login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                     context.startActivity(login);
                                     break;
                             }
                            }
                            else{

                                Toast.makeText(context,response.get("message").toString(),Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSONPost", "Error: " + error.getMessage());
                //pDialog.hide();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
    }
}