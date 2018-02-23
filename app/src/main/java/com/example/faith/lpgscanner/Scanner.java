package com.example.faith.lpgscanner;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Scanner extends AppCompatActivity {

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        btnScan = (Button)findViewById(R.id.btnScan);
        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("Scan");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.setBarcodeImageEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!=null){
            if (result.getContents()==null){
                Toast.makeText(this,"Scanning canceled",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result.getContents()));
                startActivity(browserIntent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
