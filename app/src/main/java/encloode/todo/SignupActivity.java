package encloode.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class SignupActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.emailField);
        password = (EditText) findViewById(R.id.passwordField);
        signUpButton = (Button) findViewById(R.id.signupButton);

        final Firebase ref = new Firebase(Constants.FIREBASE_URL);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordText = password.getText().toString();
                String emailText = email.getText().toString();

                passwordText = passwordText.trim();
                emailText = emailText.trim();

                if(passwordText.isEmpty() || emailText.isEmpty()) {
                    //generate a builder of a alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

                    //set the messages etc
                    builder.setMessage(R.string.signup_error_message)
                            .setTitle(R.string.signup_error_title)
                            .setPositiveButton("OK",null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //signup
                    ref.createUser(emailText, passwordText, new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            //generate a builder of a alert dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);

                            //set the messages etc
                            builder.setMessage(R.string.signup_success)
                                    .setPositiveButton(R.string.login_button_label, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setTitle(R.string.signup_error_title)
                                    .setMessage(R.string.signup_error_message)
                                    .setPositiveButton("OK",null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    });
                }


            }
        });
    }
}
