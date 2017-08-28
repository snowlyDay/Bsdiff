package com.example.administrator.bsdiff;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import io.sigpipe.jbsdiff.Diff;

public class MainActivity extends AppCompatActivity {

    private Button mbtnCopyapk;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnCopyapk = (Button) findViewById(R.id.btn_copyapk);
        mContext = this.getApplicationContext();
        mbtnCopyapk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        boolean copied = CopyFileToPath.copyToPath(new File(MainActivity.this.getPackageCodePath()), new File(mContext.getExternalCacheDir(), "copied.apk"));
                        Log.d("BaseApplication", "copy finished, success = " + copied);
                    }
                }.start();

            }
        });
        Toast.makeText(mContext,"This is new file ", Toast.LENGTH_LONG).show();
        Diff diff = new Diff();
    }

}
