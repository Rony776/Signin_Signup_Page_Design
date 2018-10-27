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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.jar.Attributes;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText user_name,user_email,user_password,user_confirm_password;
    private Button register,sign_in_here;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;


    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        user_name = (EditText) findViewById(R.id.input_name);
        user_email = (EditText) findViewById(R.id.input_email_sign_up);
        user_password = (EditText) findViewById(R.id.password_sign_up);
        user_confirm_password = (EditText) findViewById(R.id.confirm_password);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        register = (Button) findViewById(R.id.register);
        sign_in_here = (Button) findViewById(R.id.button_sign_in_here);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_sign_up);

        //Progressdialog
        progressDialog = new ProgressDialog(this);

        //For Firebase
        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = user_name.getText().toString().trim();
                final String email = user_email.getText().toString().trim();
                final String password = user_password.getText().toString().trim();
                String confirm_password = user_confirm_password.getText().toString().trim();

                if (name.isEmpty())
                {
                    user_name.setError("This field can't be empty");
                    return;
                }
                if (name.length() <3 || name.length() >30)
                {
                    user_name.setError("Enter valid name");
                    return;
                }
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
                if (TextUtils.isEmpty(confirm_password))
                {
                    user_confirm_password.setError("Confirm your password here");
                    return;
                }
                if (confirm_password.length() < 6)
                {
                    user_confirm_password.setError("Confirm password doesn't matched");
                    return;
                }
                if (!password.equals(confirm_password))
                {
                    user_confirm_password.setError("Confirm password doesn't matched");
                    return;
                }
                if (!checkBox.isChecked())
                {
                   checkBox.setError("Click this field");
                   return;
                }

                else {
                    progressDialog.setTitle("Creating your account");
                    progressDialog.setMessage("Please wait, while we are creating new account for you");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    auth.fetchProvidersForEmail(user_email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        boolean check = !task.getResult().getProviders().isEmpty();

                                        if (!check)
                                        {
                                            //Email Does't Exists ,So We Can Create Account Using This Email

                                            auth.createUserWithEmailAndPassword(email,password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                                            if (task.isSuccessful())
                                                            {
                                                                //Insert Value in Real Time Database
                                                                progressDialog.dismiss();
                                                                Toast.makeText(getApplicationContext(),"Registration success",Toast.LENGTH_SHORT).show();
                                                                Intent myIntent = new Intent(SignUpActivity.this,SignInActivity.class);
                                                                startActivity(myIntent);
                                                            }
                                                            else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });


                                        }
                                        else
                                        {
                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(),"This Email Already Registered",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }


            }
        });



    }

}
