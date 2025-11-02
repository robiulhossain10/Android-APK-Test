package com.robiul.firstapk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText etUser,etPass;

    Button btnLogin;

    FloatingActionButton fab;

    Button btnSing;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        fab = findViewById(R.id.floatingActionButton);
        btnSing = findViewById(R.id.btnSignup);

        btnSing.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            startActivity(intent);
        });

        fab.setOnClickListener(View -> {
            Toast.makeText(MainActivity.this, "Floating Action Button", Toast.LENGTH_SHORT).show();
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUser.getText().toString().trim();
                String password = etPass.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please All Fields", Toast.LENGTH_SHORT).show();
                } else if(username.equals("admin") && password.equals("1234")){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(findViewById(android.R.id.content), "Message Deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(MainActivity.this, "Action Undone", Toast.LENGTH_SHORT).show();
//                        }
//                    }).show();

//                    new AlertDialog.Builder(MainActivity.this).
//                            setTitle("Delete Item").setMessage("Are you sure you want to delete this?").
//                            setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton("Yes",(dialog, which) -> {
//                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
//                            }).setNegativeButton("No", (dialog, which) -> {
//                                Snackbar.make(findViewById(android.R.id.content), "Message Deleted", Snackbar.LENGTH_LONG).show();
//                            }).show();

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, null);

                    TextView text = layout.findViewById(R.id.toast_text);
                    text.setText("Hello Robiul! Custom Toast Works!");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}