package com.example.myapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends Activity {
    private EditText mNameEditText;
    private EditText mLastNameEditText;
    private TextView mTextViewResult;
    private Button loginButton;
    private TextView noAccountTextView;

    private String name;
    private String pwd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.textView);
        loginButton = findViewById(R.id.login_button);
        mNameEditText = findViewById(R.id.name_edit_text);
        mLastNameEditText = findViewById(R.id.last_name_edit_text);
        noAccountTextView = findViewById(R.id.no_account_text_view);

        OkHttpClient client = new OkHttpClient();
        String url = "http://10.65.243.228/network/getHttpGet.php";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mNameEditText.getText().toString();
                pwd = mLastNameEditText.getText().toString();

                if(name.length() == 0 || pwd.length() == 0){
                    //กรอกข้อมูลไม่ครบ
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Login");
                    dialog.setMessage("Plese enter name and password");
                    dialog.setPositiveButton("OK",null);
                    dialog.show();
                    return;
                }

                OkHttpClient client = new OkHttpClient();
                String url = "http://10.65.243.228/network/showData.php?name="+name+"&password="+pwd;
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTextViewResult.setText(myResponse);
                                    if(myResponse.equals("No Account")){//ไม่มีบัญชีในระบบ
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                        dialog.setTitle("No Account");
                                        dialog.setMessage("Plese Register account");
                                        dialog.setPositiveButton("OK",null);
                                        dialog.show();
                                    }else{
                                        Intent intent = new Intent(MainActivity.this , MyAccountActivity.class);
                                        intent.putExtra("account" , myResponse);
                                        startActivity(intent);

                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        SpannableString content = new SpannableString("If you haven't account click this");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        noAccountTextView.setText(content);
        noAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewAcountActivity.class);
                startActivity(intent);
            }
        });
    }
}