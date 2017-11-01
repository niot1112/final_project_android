package kmitl.chanchai.koiauction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private EditText confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user = findViewById(R.id.regis_Username);
        pass = findViewById(R.id.regis_Password);
        confirmpass = findViewById(R.id.regis_ConfirmPassword);
    }

    public void cancel(View view) {
        finish();
    }

    public void Signup(View view) {
        String username = user.getText().toString();
        String password = pass.getText().toString();
        String conf_password = confirmpass.getText().toString();

        if(!password.equals(conf_password)){
            Toast.makeText(this, "password does not match", Toast.LENGTH_SHORT).show();
        }else if(checkusername()){
            Toast.makeText(this, "username already exist", Toast.LENGTH_SHORT).show();
        }else{
            signup();
            finish();
        }
    }

    private boolean checkusername() {
        return true;
    }

    private void signup() {
    }
}
