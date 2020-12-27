package com.codingstrings.sns.utils.http;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
import com.codingstrings.sns.screens.SplashScreenActivity;
import com.codingstrings.sns.sql.SqlWriteUps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestWriteups {

    SQLiteDatabase writeAble,readAble;
    SqlWriteUps sqLiteDatabase;

    public HttpRequestWriteups(){

        sqLiteDatabase = new SqlWriteUps(SplashScreenActivity.mGetContext());
        writeAble      = sqLiteDatabase.getWritableDatabase();
        readAble       = sqLiteDatabase.getReadableDatabase();

    }
    public void Request() {

        final Context context = SplashScreenActivity.mGetContext();
        String token  = HomeActivity.token;
        Log.d("Token Http", "Request: "+token);
        Map<String, String> params = new HashMap<String, String>();
        params.put("token",token);

        String url = "http://139.59.76.112:3000/api/wdata";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            if(response!=null){
                                Log.d("httpRequest", "Json Response From Server: "+response.toString());

                                JSONArray jsonArray = new JSONArray(response.get("writeup").toString());
                                for(int i = 0 ; i < jsonArray.length() ; i++ ){

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if(jsonObject!=null) {

                                        Log.d("httpRequest", "Json Object From Server: "+jsonObject.toString());
                                        final ContentValues values = new ContentValues();
                                        values.put(SqlWriteUps.COLUMN_NAME, jsonObject.get("name").toString());
                                        values.put(SqlWriteUps.COLUMN_NUMBER, jsonObject.get("number").toString());
                                        values.put(SqlWriteUps.COLUMN_GROUP, jsonObject.get("grp").toString());
                                        values.put(SqlWriteUps.COLUMN_SUBJECT, jsonObject.get("subject").toString());
                                        values.put(SqlWriteUps.COLUMN_SEEN, jsonObject.get("seen").toString());
                                        values.put(SqlWriteUps.COLUMN_ID, jsonObject.get("_id").toString());
                                        if (values!=null) {

                                            String selectQuery = "SELECT _id FROM " + SqlWriteUps.TABLE_NAME+" WHERE _id='"+jsonObject.get("_id").toString()+"'";
                                            Cursor cursor      = readAble.rawQuery(selectQuery, null);
                                            if(cursor.getCount()>0){
                                                Log.d("httpRequest", "Assignment Already Exists: " );
                                            }
                                            else {
                                                long newRowId = writeAble.insert(SqlWriteUps.TABLE_NAME, null, values);
                                                Log.d("httpRequest", "Inserted Successfully: " + newRowId);
                                            }
                                            cursor.close();
                                        }
                                        else{
                                            Log.d("httpRequest", "Context Values Are Null");

                                        }
                                    }else{
                                        Log.d("httpRequest", "Json Object is Null");

                                    }
                                }
                            }
                            else{

                                Toast.makeText(context,response.get("NoResponse").toString(),Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("httpRequest", "Error: " + error.getMessage());
                //pDialog.hide();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
    }


}
