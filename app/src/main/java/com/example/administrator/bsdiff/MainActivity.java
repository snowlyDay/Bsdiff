package com.example.administrator.bsdiff;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.compress.compressors.CompressorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.sigpipe.jbsdiff.Diff;
import io.sigpipe.jbsdiff.InvalidHeaderException;
import io.sigpipe.jbsdiff.Patch;
import io.sigpipe.jbsdiff.ui.FileUI;

public class MainActivity extends AppCompatActivity {

    private Button mbtnCopyapk,mGenerateFile,mCreatePatch;
    private Context mContext;
    private File mOldApkPath;
    private File mPatchFile;
    private File mNewFile;
    private File mNewFile1;
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbtnCopyapk = (Button) findViewById(R.id.btn_copyapk);
        mGenerateFile = (Button)findViewById(R.id.btn_newfile);
        mCreatePatch = (Button) findViewById(R.id.btn_newPatch);
        mContext = this.getApplicationContext();

        mOldApkPath = new File(mContext.getExternalCacheDir(), "copied.apk");
        mPatchFile = new File(mContext.getExternalCacheDir(), "update.patch");
        mNewFile = new File(mContext.getExternalCacheDir(), "new.apk");
        mNewFile1 = new File(mContext.getExternalCacheDir(), "new1.apk");
        mbtnCopyapk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        boolean copied = CopyFileToPath.copyToPath(new File(MainActivity.this.getPackageCodePath()), mOldApkPath);
                        Log.d("BaseApplication", "copy finished, success = " + copied);
                    }
                }.start();

            }
        });

        mGenerateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thread.start();
            }
        });

        mCreatePatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "createPatch");
                diffRun.start();
            }
        });
        Toast.makeText(mContext,"This is new file ", Toast.LENGTH_LONG).show();
    }

   Thread thread = new Thread( new Runnable() {
        @Override
        public void run() {
            FileInputStream fis = null;
            FileInputStream fis1 = null;
            FileOutputStream fot = null;
            try {
                fis = new FileInputStream(mOldApkPath);
                byte[] oldApkByte = new byte[(int)fis.available()];
                fis.read(oldApkByte);
                fis.close();

                fis1 = new FileInputStream(mPatchFile);
                byte[] patchFile = new byte[(int)fis1.available()];
                fis1.read(patchFile);
                fis1.close();

                fot = new FileOutputStream(mNewFile);

                Patch.patch(oldApkByte,patchFile,fot);
                fot.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidHeaderException e) {
                e.printStackTrace();
            } catch (CompressorException e) {
                e.printStackTrace();
            }


        }

        });

    Thread diffRun = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                FileUI.diff(mOldApkPath,mNewFile1,mPatchFile);
            } catch (CompressorException e) {
                e.printStackTrace();
            } catch (InvalidHeaderException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

}
