package com.example.kunj.scope;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private static final String REQ_URL ="https://kunjdesai96.000webhostapp.com/registration.php" ;
    EditText name,phone,email,password,confirmpass;
    TextView signin;
    Button signinbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText) findViewById(R.id.signup_name);
        phone = (EditText) findViewById(R.id.signup_number);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_pass);
        confirmpass = (EditText) findViewById(R.id.signup_confirmpass);
        signin = (TextView) findViewById(R.id.signup_signin);
        signinbtn = (Button) findViewById(R.id.signup_btn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUp.this,LoginPage.class);
                startActivity(intent);
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                String confirmpasss= confirmpass.getText().toString();
                final String passwords = password.getText().toString();
                final String username = name.getText().toString();
                final String number = phone.getText().toString();
                final String emailid = email.getText().toString();
                if(username.isEmpty())
                {
                    name.setError("Please fill the username");
                    return;
                }
                if(number.isEmpty())
                {
                    phone.setError("Mandatory field");
                    return;
                }
                if(emailid.isEmpty())
                {
                    email.setError("Mandatory field");
                    return;
                }
                if(passwords.isEmpty())
                {
                    password.setError("Mandatory field");
                    return;
                }
                if(confirmpasss.isEmpty())
                {
                    confirmpass.setError("Mandatory field");
                    return;
                }
                if(confirmpasss.equals(passwords))
                {

                }
                else
                {
                    confirmpass.setError("Password mismatch!!");
                    return;
                }
                // Toast.makeText(SignUp.this,"Done",Toast.LENGTH_SHORT).show();

                final ProgressDialog dialog = new ProgressDialog(SignUp.this);
                dialog.setTitle("Please wait...!!");
                dialog.setMessage("Registering");
                dialog.setCancelable(false);
                dialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, REQ_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            int res = object.getInt("success");

                            if (res == 1)
                            {
                                Toast.makeText(SignUp.this,"Registered Sucessfully",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUp.this,LoginPage.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SignUp.this,"Something went wrong",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("name",username);
                        params.put("email",emailid);
                        params.put("password",passwords);
                        params.put("number",number);

                        return params;
                    }
                };

                Volley.newRequestQueue(SignUp.this).add(request);
            }
        });
    }
}
