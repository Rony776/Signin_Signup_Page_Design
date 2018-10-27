package techdoctorbd.com.technewsbangla;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private EditText user_email,user_password;
    private Button sign_in,sign_up_here;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;

    //For Firebase
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        user_email = (EditText) findViewById(R.id.input_email_sign_in);
        user_password = (EditText) findViewById(R.id.input_password_sign_in);
        sign_in = (Button) findViewById(R.id.button_sign_in);
        sign_up_here = (Button) findViewById(R.id.button_sign_up_here);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_sign_in);

        //Progressdialog
        progressDialog = new ProgressDialog(this);

        //For Firebase
        auth = FirebaseAuth.getInstance();



        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    user_email.setError("This field can't be empty");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    user_email.setError("Enter valid email address");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    user_password.setError("This field can't be empty");
                    return;
                }
                if (password.length() < 6)
                {
                    user_password.setError("Minimum 6 character required ");
                    return;
                }

                else {

                }

            }
        });


    }

}
