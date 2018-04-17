package com.example.kunj.scope;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kc on 08/02/2018.
 */
public class Session  {
    private String email;
    SharedPreferences sharedPreferences;
    Context context;
    public Session(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("emailInfo",context.MODE_PRIVATE);
    }
    public String getEmail() {
        email=sharedPreferences.getString("userData","");
        return email;
    }

    public void setEmail(String email) {
        sharedPreferences.edit().putString("userData",email).commit();
        this.email = email;
    }
    public void removeEmail()
    {
        sharedPreferences.edit().clear().commit();
    }

}
