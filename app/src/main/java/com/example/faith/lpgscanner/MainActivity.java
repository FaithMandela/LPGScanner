package com.example.faith.lpgscanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvClick;
    ProgressDialog progressDialog;
    OkHttpClient okHttpClient;

    public static final String url = "http://demo.dewcis.com/revenuecollection/dataserver";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            etEmail = (EditText)findViewById(R.id.etEmail);
            etPassword = (EditText)findViewById(R.id.etPassword);
            btnLogin = (Button)findViewById(R.id.btnLogin);
            tvClick = (TextView)findViewById(R.id.tvClick);

            progressDialog = new ProgressDialog(this);
        }
    }

    public void logIn(View view) throws JSONException, IOException {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("telephone",email);
        jsonObject.put("password",password);

        authenticate(url,email,password);
    }

    ///Method to get token
    public String authenticate(String url, String appKey, String appPass) throws IOException, JSONException {
        okHttpClient = new OkHttpClient();
        byte[] user = appKey.getBytes("UTF-8");
        byte[] pass = appPass.getBytes("UTF-8");

        String authUser = Base64.encodeToString(user,Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP);
        String authPass = Base64.encodeToString(pass, Base64.URL_SAFE|Base64.NO_PADDING|Base64.NO_WRAP);


        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .addHeader("action", "authorization")
                .addHeader("authuser", authUser)
                .addHeader("authpass", authPass)
                .addHeader("cache-control", "no-cache")
                .build();

        okhttp3.Response response = okHttpClient.newCall(request).execute();
        String rBody = response.body().string();

        System.out.println("BASE 1010 : " + rBody);
        //BASE 1010 : {"ResultDesc":"Wrong username or password","ResultCode":1}

        return rBody;
    }

    public void send(JSONObject jsonObject){

    }


    public void register(View view){
        Intent intent = new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }
}
