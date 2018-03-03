package com.example.mattr.myyearbook;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ResumeActivity extends AppCompatActivity {
private WebView webView;
private Button downloadBtn;
private ImageButton backBtn;
private Button goBtn;
private EditText addressBar;
private String fileUrl = "http://www.mcufollower.com/resume/resumePdf.pdf";
private String url = "http://www.mcufollower.com/resume/resume";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        this.getSupportActionBar().hide();

        webView = findViewById(R.id.webView);
        downloadBtn = findViewById(R.id.downloadBtn);
        backBtn = findViewById(R.id.backBtn);
        goBtn = findViewById(R.id.goBtn);
        addressBar = findViewById(R.id.addressBar);

        webView.loadUrl(url);
        addressBar.setText(url);
        addressBar.setSelectAllOnFocus(true);

        while (addressBar.isFocused()) {
            ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);

        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);


        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = addressBar.getText().toString();
               String temp2 = isValidAddress(temp);

                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(temp2);
                addressBar.clearFocus();
                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);           }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(ResumeActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // if cancelled array=0.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
                    request.setDescription("I'll just leave this here...");
                    request.setTitle("Matt's Resume");


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "resumePdf.pdf");

                    // get download service and enqueue file
                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);



                } else {

                    // permission denied
                    Toast.makeText(ResumeActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    downloadBtn.setVisibility(View.INVISIBLE);
                }
                return;
            }


        }
    }
    public String isValidAddress(String url) {

       if (url.startsWith("http://") || url.startsWith("https://")
                || url.startsWith("ftp://")) {

       } else {
           url = "https://" + url;
       }
        return url;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}
