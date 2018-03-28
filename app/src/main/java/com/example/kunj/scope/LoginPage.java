package com.example.kunj.scope;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class LoginPage extends AppCompatActivity {

    private static final String RES_URL ="https://kunjdesai96.000webhostapp.com/login.php" ;
    EditText username,passowrd;
    TextView forpass,signup;
    Button loginbtn;
    CheckBox checkBox;

    SharedPreferences preferences;
    ScopeDBHelper scopeDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        preferences = getSharedPreferences("userpref",MODE_PRIVATE);
        username= (EditText) findViewById(R.id.login_name_et);
        passowrd = (EditText) findViewById(R.id.login_pass_et);
        forpass = (TextView) findViewById(R.id.login_forgotpass_tv);
        signup = (TextView) findViewById(R.id.login_signup_tv);
        loginbtn = (Button) findViewById(R.id.login_btn);
        checkBox = (CheckBox) findViewById(R.id.chechbox);



       // scopeDBHelper = new ScopeDBHelper(this);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernames = username.getText().toString();
                final String passwords = passowrd.getText().toString();

                preferences.edit().putString("username",usernames).commit();
                preferences.edit().putString("password",passwords).commit();

                final ProgressDialog dialog = new ProgressDialog(LoginPage.this);
                dialog.setTitle("Please wait...!!");
                dialog.setMessage("Loging In");
                dialog.setCancelable(false);
                dialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, RES_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            dialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            int res = obj.getInt("success");
                            //String name = obj.getString("name");
                            if(res == 1)
                            {
                               //Toast.makeText(LoginPage.this,"Login Successfull",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginPage.this,HomePage.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(LoginPage.this,"Invalid User",Toast.LENGTH_LONG).show();
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

                        Map<String, String > params = new HashMap<String, String>();
                        params.put("number",usernames);
                        params.put("password",passwords);
                        return params;
                    }
                };
                Volley.newRequestQueue(LoginPage.this).add(request);

            }
        });

        forpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginPage.this,"Forgot",Toast.LENGTH_SHORT).show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this,SignUp.class);
                startActivity(intent);
            }
        });
    }
}
