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
import com.codingstrings.sns.sql.SqlAssignments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestAssignments {

    SQLiteDatabase writeAble,readAble;
    SqlAssignments sqLiteDatabase;


    public HttpRequestAssignments(){

        sqLiteDatabase = new SqlAssignments(SplashScreenActivity.mGetContext());
        writeAble      = sqLiteDatabase.getWritableDatabase();
        readAble       = sqLiteDatabase.getReadableDatabase();



    }
    public void Request() {

        final Context context = SplashScreenActivity.mGetContext();
        String token  = HomeActivity.token;

        Log.d("Assignments", " After : "+token);
        Map<String, String> params = new HashMap<String, String>();
        params.put("token",token);

        String url = "http://139.59.76.112:3000/api/data";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            if(response!=null){
                                Log.d("httpRequest", "Json Response From Server: "+response.toString());

                                JSONArray jsonArray = new JSONArray(response.get("notification").toString());
                                for(int i = 0 ; i < jsonArray.length() ; i++ ){

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if(jsonObject!=null) {

                                        Log.d("httpRequest", "Json Object From Server: "+jsonObject.toString());
                                        final ContentValues values = new ContentValues();
                                        values.put(SqlAssignments.COLUMN_NAME, jsonObject.get("name").toString());
                                        values.put(SqlAssignments.COLUMN_NUMBER, jsonObject.get("number").toString());
                                        values.put(SqlAssignments.COLUMN_UNIT, jsonObject.get("unit").toString());
                                        values.put(SqlAssignments.COLUMN_SUBJECT, jsonObject.get("subject").toString());
                                        values.put(SqlAssignments.COLUMN_SEEN, jsonObject.get("seen").toString());
                                        values.put(SqlAssignments.COLUMN_ID, jsonObject.get("_id").toString());
                                        if (values!=null) {

                                            String selectQuery = "SELECT _id FROM " + SqlAssignments.TABLE_NAME+" WHERE _id='"+jsonObject.get("_id").toString()+"'";
                                            Cursor cursor      = readAble.rawQuery(selectQuery, null);
                                            if(cursor.getCount()>0){
                                                Log.d("httpRequest", "Assignment Already Exists: " );
                                            }
                                            else {
                                                long newRowId = writeAble.insert(SqlAssignments.TABLE_NAME, null, values);
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
                Log.d("httpRequest", "Execption: " + error.toString());

                //pDialog.hide();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
    }


}
