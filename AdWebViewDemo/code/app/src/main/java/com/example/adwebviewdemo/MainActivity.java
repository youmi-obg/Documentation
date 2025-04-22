package com.example.adwebviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText etWebViewUrl;

    private EditText etNameSpace;

    private Button btJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWebViewUrl = findViewById(R.id.et_webView_url);
        etNameSpace = findViewById(R.id.et_javascript_interface_namespace);
        btJump = findViewById(R.id.bt_jump);

        etWebViewUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btJump.setEnabled(!TextUtils.isEmpty(editable.toString().trim()));
            }
        });

        btJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isUrl(etWebViewUrl.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this,"Please input legal url",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_WEBVIEW_URL,etWebViewUrl.getText().toString().trim());
                intent.putExtra(WebViewActivity.EXTRA_JAVASCRIPT_INTERFACE_NAMESPACE,etNameSpace.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private boolean isUrl(String input) {
        String urlPattern = "^((https?|ftp|file)://)?([\\w+\\-]+\\.)+[\\w]{2,63}(:[0-9]{1,5})?(/.*)?$";
        Pattern pattern = Pattern.compile(urlPattern);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}