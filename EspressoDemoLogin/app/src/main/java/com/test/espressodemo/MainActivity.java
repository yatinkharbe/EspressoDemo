package com.test.espressodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditPassword;
    private EditText mEditUserId;
    private Button mButtonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mEditPassword = (EditText) findViewById(R.id.edtPass);
        mEditUserId = (EditText) findViewById(R.id.edtUserId);
        mButtonLogin = (Button) findViewById(R.id.loginBtn);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validData()) {

                    if (mEditUserId.getText().toString().equalsIgnoreCase(mEditPassword.getText().toString())) {
                        Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, R.string.failure, Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

    }

    boolean validData() {
        if (mEditUserId.getText().toString().trim().equalsIgnoreCase("")) {
            return false;
        }
        if (mEditPassword.getText().toString().trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

}

