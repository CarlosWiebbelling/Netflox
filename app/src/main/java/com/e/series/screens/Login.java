package com.e.series.screens;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.series.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.quickstart.auth.R;


public class Login extends AppCompatActivity {

    private ImageView logo;
    private TextView username;
    private TextView password;
    private View loginID;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logo = findViewById(R.id.imageView);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginID = findViewById(R.id.loginID);

        logo.setImageResource(R.drawable.netflox_bb);

        getSupportActionBar().hide();
        loginID.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mAuth = FirebaseAuth.getInstance();
    }

    public void sign (View view) {
        if((username.getText().toString()).equals("") || (password.getText().toString()).equals("")) {
            AlertDialog alert;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Campos inválidos");
            builder.setMessage("Preencha todos os campos");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(Login.this, "Autentication failure", Toast.LENGTH_SHORT).show();
                }
            });

            alert = builder.create();
            alert.show();
            return;
        }
        mAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(Login.this, Browser.class);
                        intent.putExtra("username", (username.getText().toString()));

                        startActivity(intent);

                    } else {
                        Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    public void createAccount (View view) {
        startActivity(new Intent(this, Register.class));
        finish();
    }
}
