package com.example.bookfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url = getIntent().getStringExtra("URL");

        WebView webView = findViewById(R.id.webViewBook);
        webView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
