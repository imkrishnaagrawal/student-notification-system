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
import com.codingstrings.sns.sql.SqlNotice;
import com.codingstrings.sns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestNotice {

    SQLiteDatabase writeAble,readAble;
    SqlNotice sqLiteDatabase;

    public HttpRequestNotice(){

        sqLiteDatabase = new SqlNotice(SplashScreenActivity.mGetContext());
        writeAble      = sqLiteDatabase.getWritableDatabase();
        readAble       = sqLiteDatabase.getReadableDatabase();

    }
    public void Request() {

        final Context context = SplashScreenActivity.mGetContext();
        String token  = HomeActivity.token;
        Log.d("Token Http", "Request: "+token);
        Map<String, String> params = new HashMap<String, String>();
        params.put("token",token);

        String url = context.getResources().getString(R.string.http_endpoint)+"/api/ndata";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if(response!=null){

                                Log.d("httpRequest", "Json Response From Server: "+response.toString());

                                JSONArray jsonArray = new JSONArray(response.get("notices").toString());
                                for(int i = 0 ; i < jsonArray.length() ; i++ ){

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    if(jsonObject!=null) {

                                        Log.d("httpRequest", "Json Object From Server: "+jsonObject.toString());
                                        final ContentValues values = new ContentValues();
                                        values.put(SqlNotice.COLUMN_ID, jsonObject.get("_id").toString());
                                        values.put(SqlNotice.COLUMN_MESSAGE, jsonObject.get("message").toString());
                                        values.put(SqlNotice.COLUMN_SUBJECT, jsonObject.get("msubject").toString());
                                        values.put(SqlNotice.COLUMN_SEEN, jsonObject.get("seen").toString());

                                        if (values!=null) {

                                            String selectQuery = "SELECT _id FROM " + SqlNotice.TABLE_NAME+" WHERE _id='"+jsonObject.get("_id").toString()+"'";
                                            Cursor cursor      = readAble.rawQuery(selectQuery, null);
                                            if(cursor.getCount()>0){
                                                Log.d("httpRequest", "Notice Already Exists: " );
                                            }
                                            else {
                                                long newRowId = writeAble.insert(SqlNotice.TABLE_NAME, null, values);
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
