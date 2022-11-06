package com.loginui.tapbit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PDFViewer extends AppCompatActivity {

    private String url;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        url = getIntent().getStringExtra("PDFUrl");
        String title = getIntent().getStringExtra("title");

        setTitle(title);
        System.out.println(url);
        if(url.startsWith("http://localhost")){
            url = GlobalVariable.link+url.substring(22);
        }

//        WebView webview = (WebView) findViewById(R.id.webView);
//        webview.getSettings().setJavaScriptEnabled(true);
//        webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + url);


        pdfView = findViewById(R.id.pdfView);

        new PDFDownloadForView().execute(url);

    }

    private class  PDFDownloadForView extends AsyncTask<String,Void, InputStream>{

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                if(httpURLConnection.getResponseCode()==200){
                    inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                }
            }catch (IOException e){
                e.printStackTrace();
            }


            return  inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream){
            pdfView.fromStream(inputStream).spacing(0).pages(0,1).enableDoubletap(true).load();
        }
    }
}