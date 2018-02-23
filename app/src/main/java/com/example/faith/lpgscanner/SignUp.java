package com.example.faith.lpgscanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Faith on 1/10/2018.
 */

public class SignUp  extends AppCompatActivity {
    EditText etFname, etLname, etEmail, etPhone, etPassword,etConfirm;
    Button btnRegister;

    ProgressDialog progressDialog;

    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        progressDialog = new ProgressDialog(this);

        etFname = (EditText)findViewById(R.id.etFirst);
        etLname = (EditText)findViewById(R.id.etLast);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etConfirm = (EditText)findViewById(R.id.etConfirm);

        btnRegister = (Button) findViewById(R.id.btnRegister);
    }

    public void register(View view) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String fName = etFname.getText().toString();
        String lName = etLname.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();
        String phone = etPhone.getText().toString();

        jsonObject.put("username",fName +" "+lName);
        jsonObject.put("email",email);
        jsonObject.put("phone",phone);
        jsonObject.put("first_password",password);


    }

    public void send(String url,String json){
        okHttpClient = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(MainActivity.JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("action","udata")
                .addHeader("content-type", "application/json")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("IOError",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try(ResponseBody responseBody = response.body()){
                    if (!response.isSuccessful())throw new IOException("Unexpected code " + response);
                    Headers responseHeaders = response.headers();

                    Log.e("Returned","TEST"+String.valueOf(responseHeaders.size()));
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String respo = responseBody.string();
                    System.out.println("BASE1020 "+respo);

                }
            }
        });

    }

    public void logIn(View view){
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        startActivity(intent);
    }

    public void clearEdits(EditText editText){
        editText.setText("");
    }
}
