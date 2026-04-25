package com.example.medicalzone;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PdfViewerActivity extends AppCompatActivity {

    private WebView pdfWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        pdfWebView = findViewById(R.id.pdfWebView);

        // Configure WebView settings
        WebSettings webSettings = pdfWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        pdfWebView.setWebViewClient(new WebViewClient());

        // Load a PDF from assets or external URL
        String pdfUrl = "https://drive.google.com/file/d/18lEqlLeE6-3yHDliB4lfAZT_LagY3P5D/view?usp=drive_link"; // Replace with your PDF URL
        pdfWebView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdfUrl);
    }
}
