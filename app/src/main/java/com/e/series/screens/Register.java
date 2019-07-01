package com.e.series.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.e.series.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText emailInput;
    private EditText passwordInput1;
    private EditText passwordInput2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.email);
        passwordInput1 = findViewById(R.id.password1);
        passwordInput2 = findViewById(R.id.password2);

        findViewById(R.id.mainID).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public void createAccount (View view) {

        AlertDialog alert;

        final String email = emailInput.getText().toString();
        String password = passwordInput1.getText().toString();

        if(email.equals("") || password.equals("") || (passwordInput2.getText().toString()).equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Campos inválidos");
            builder.setMessage("Preencha todos os campos");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(Register.this, "Conta não criada", Toast.LENGTH_SHORT).show();
                }
            });

            alert = builder.create();
            alert.show();

        } else if (!password.equals(passwordInput2.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Campos inválidos");
            builder.setMessage("As senhas não conferem");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(Register.this, "Conta não criada", Toast.LENGTH_SHORT).show();
                }
            });

            alert = builder.create();
            alert.show();

        } else {
            mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Register.this, Browser.class);
                            intent.putExtra("username", email);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Register.this, "Conta não criada!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    }
}
