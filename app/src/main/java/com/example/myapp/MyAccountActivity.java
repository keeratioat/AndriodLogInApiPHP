package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class MyAccountActivity extends Activity {
    private String user;
    private TextView mUserTextView;
    private TextView mChangePwdTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        user = getIntent().getExtras().getString("account");
        mUserTextView = findViewById(R.id.user_text_view);
        mUserTextView.setText(user);

        mChangePwdTextView = findViewById(R.id.change_pwd_text_view);
        SpannableString content = new SpannableString("Change Password");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        mChangePwdTextView.setText(content);

        mChangePwdTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountActivity.this , ChangePasswordActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
    }
}