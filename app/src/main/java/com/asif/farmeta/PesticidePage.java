package com.asif.farmeta;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PesticidePage extends AppCompatActivity {

    WebView webView;
    Button btnDetails, btnPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesticide_page);

        webView = findViewById(R.id.webView);
        btnPDF = findViewById(R.id.btnPDF);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Keep navigation inside WebView
        webView.setWebViewClient(new WebViewClient());

        // Default: show details page
        loadDetailsPage();


        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPDF();
            }
        });
    }

    private void loadDetailsPage() {
        String detailsUrl = "https://www.pesticidebd.com/"; // Details page URL
        webView.loadUrl(detailsUrl);
    }

    private void loadPDF() {
        String pdfUrl = "https://bcpabd.com/wp-content/uploads/2021/01/Registered-pesticide-List-of-Bangladesh-upto-2020.pdf";
        String googleDocsUrl = "https://docs.google.com/gview?embedded=true&url=" + pdfUrl;
        webView.loadUrl(googleDocsUrl);
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}