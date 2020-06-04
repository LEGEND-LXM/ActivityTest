package com.example.broadcastbest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText accountEditText;  //用于获取账号输入框对象
    private EditText passwordEditText;  //用于获取密码输入框对象
    private Button loginButton;  //用于获取登陆按钮对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountEditText = findViewById(R.id.account);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                //判断账号密码，此处账号为 ：legend；密码为 ：28339492
                if (account.equals("legend") && password.equals("28339492")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is inValid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
