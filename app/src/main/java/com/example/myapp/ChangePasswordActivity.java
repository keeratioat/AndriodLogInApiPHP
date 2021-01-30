package com.example.myapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePasswordActivity extends Activity {

    private EditText mNameChangeEditText;
    private EditText mpassChangeEditText;
    private EditText mcpassChangeEditText;
    private Button mchangeButton;
    private String name;
    private String pwd;
    private String cpwd;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mNameChangeEditText = findViewById(R.id.name_change_edit_text);
        mpassChangeEditText = findViewById(R.id.password_change_edit_text);
        mcpassChangeEditText = findViewById(R.id.cpass_change_pass_edit_text);
        mchangeButton = findViewById(R.id.ok_change_pass_button);
        user = getIntent().getExtras().getString("user");
        mNameChangeEditText.setText(user);

        mchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mNameChangeEditText.getText().toString();
                pwd = mpassChangeEditText.getText().toString();
                cpwd = mcpassChangeEditText.getText().toString();

                if(name.length() == 0 || pwd.length() == 0 || cpwd.length() == 0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ChangePasswordActivity.this);
                    dialog.setTitle("Change Password");
                    dialog.setMessage("Plese enter name, password and change password");
                    dialog.setPositiveButton("OK",null);
                    dialog.show();
                    return;
                }

                OkHttpClient client = new OkHttpClient();
                String url = "http://10.65.243.228/network/changePwd.php?name="+name+"&pass="+pwd+"&cpass="+cpwd;
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
                            ChangePasswordActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }
                });
                finish();
            }
        });
    }
}