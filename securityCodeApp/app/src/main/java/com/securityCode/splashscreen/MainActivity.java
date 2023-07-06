package com.securityCode.splashscreen;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
Button btn_login,btn_signup;
EditText email_ed,password_ed;
TextView tv;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btn_login=(Button) findViewById(R.id.login_btn);
        btn_signup=(Button) findViewById(R.id.signup_btn);
        tv=(TextView)findViewById(R.id.tv);
        email_ed=(EditText) findViewById(R.id.email_id);
        password_ed=(EditText) findViewById(R.id.password_input);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email_ed.getText().toString(), password_ed.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(MainActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    tv.setText(user.toString());
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed."+task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    tv.setText(""+task.getException());
//                                    updateUI(null);
                                }
                            }
                        });
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
            Toast.makeText(this, "Please signin/ create new account", Toast.LENGTH_SHORT).show();
            tv.setText(currentUser.toString());

        }
    }
}