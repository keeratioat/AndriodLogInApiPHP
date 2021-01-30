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

public class NewAcountActivity extends Activity {

    private EditText userSignInEditText;
    private EditText passwordSignInEditText;
    private EditText cpSignInEditText;
    private Button signInButton;
    private String userName;
    private String password;
    private String cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acount);

        userSignInEditText = findViewById(R.id.user_sign_in_edit_text);
        passwordSignInEditText = findViewById(R.id.password_sign_in_edit_text);
        signInButton = findViewById(R.id.sign_in_button);
        cpSignInEditText = findViewById(R.id.cpass_sign_in_edit_text2);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userSignInEditText.getText().toString();
                password = passwordSignInEditText.getText().toString();
                cpass = cpSignInEditText.getText().toString();

                if(!password.equals(cpass)){
                    //password ใส่ไม่ตรงกับ confirm password
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewAcountActivity.this);
                    dialog.setTitle("Sign in");
                    dialog.setMessage("Plese enter same password");
                    dialog.setPositiveButton("OK",null);
                    dialog.show();
                    return;
                }
                if(userName.length() == 0 || password.length() == 0 ||cpass.length() == 0){
                    //กรอกข้อมูลไม่ครบ
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewAcountActivity.this);
                    dialog.setTitle("Sign in");
                    dialog.setMessage("Plese enter name and password");
                    dialog.setPositiveButton("OK",null);
                    dialog.show();
                    return;
                }
                OkHttpClient client = new OkHttpClient();
                String url = "http://10.65.243.228/network/addData.php?name="+userName+"&password="+password;
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
                            NewAcountActivity.this.runOnUiThread(new Runnable() {
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