package com.androidtutorialpoint.googlemapsdrawroute;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    RegisterActivity act;
    String urlLink = "http://192.168.1.103:3000/users.json";
/*inputEmail=(EditText)findViewById(R.id.email);
            intputPhone=(EditText)findViewById(R.id.fname);
            inputConfirmPass*/
    EditText phone, inputEmail, inputConfirmPass;
    EditText username;
    EditText pass;
    Button btnRegister, back;
    TextView registerErrorMsg;
    String Uname, Pass, Email, Phone, ConfirmPass;

    final RequestParams params = new RequestParams();
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back = (Button) findViewById(R.id.bktologin);
        btnRegister = (Button) findViewById(R.id.register);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


        phone=(EditText)findViewById(R.id.fname);
        inputEmail=(EditText)findViewById(R.id.email);
        inputConfirmPass=(EditText)findViewById(R.id.pword);
        username=(EditText)findViewById(R.id.lname);
        pass=(EditText)findViewById(R.id.uname);
        act=this;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Uname=username.getText().toString();
                Pass=pass.getText().toString();
                Email=inputEmail.getText().toString();
                Phone=phone.getText().toString();
                ConfirmPass=inputConfirmPass.getText().toString();
                /*
                new get_request().execute();
                new get_request(this, name, this).execute();
                new get_request(this, name, this).execute();
                new get_request(this, name, this).execute();
                new get_request(this, name, this).execute();
              */

                new get_request(Uname,Pass,Email,Phone,ConfirmPass,act).execute();

            /*    params.put("name",Uname);
                params.put("password",Pass);
                params.put("email",Email);
                params.put("phone",Phone);
                params.put("confirm_password",ConfirmPass);
                //params.put("status",1);
                letsDoSomeNetworking(params);*/



            }

            private void letsDoSomeNetworking(RequestParams params) {
              /*  URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL("http://www.mysite.se/index.asp?data=99");

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        char current = (char) data;
                        data = isw.read();
                        System.out.print(current);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }*/

                AsyncHttpClient client = new AsyncHttpClient();

                client.post(urlLink, params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statuscode, Header[] headers, JSONObject response){
                        Log.d("OUR_App","Success! JSON: " + response.toString());
                    }

                    @Override
                    public void onFailure(int statuscode, Header[] headers, Throwable e, JSONObject response){
                        Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }





    @SuppressLint("StaticFieldLeak")
    class get_request extends AsyncTask<String, Void, String> {

        String Uname,Email,Phone,Pass,ConPass;
        RegisterActivity act;
        public get_request(String uname, String pass1, String email1, String Cpass, String phone, RegisterActivity act) {
            this.Uname = uname;
            this.act=act;
            this.Email = email1;
            this.Phone = phone;
            this.Pass=pass1;
            this.ConPass=Cpass;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(act.getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            try {
                JSONObject object = new JSONObject(s);
                String id = object.getString("id");
                String name = object.getString("name");
                String email= object.getString("email");
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                myIntent.putExtra("id",id);
                myIntent.putExtra("name",name);
                myIntent.putExtra("email",email);
                startActivity(myIntent);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //ba= new ArrayAdapter<String>(this, R.layout.spinner_item, value);
        }

        @Override

        protected String doInBackground(String... params) {

            try {
                String link = "http://192.168.1.103:3000/users.json";

                URL url = new URL(link);
                URLConnection con = url.openConnection();

                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

                JSONObject add = new JSONObject();

                add.put("name", Uname);
                add.put("email",Email);
                add.put("mobile",Phone);
                add.put("password",Pass);
                add.put("confirm_password",ConPass);

                wr.write(add.toString());
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    Log.d("LINE : ", line);
                    if (line.equals("true")) {
                        //TODO: 3/20/2017 add response checking from server format is in jason
                       // flag = true;
                    } else
                       // flag = false;

                    sb.append(line);
                }
                return sb.toString();


            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
                return "Exception: " + e.getMessage();
            }

        }
    }


}

