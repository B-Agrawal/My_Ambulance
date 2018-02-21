package com.androidtutorialpoint.googlemapsdrawroute;

import android.annotation.SuppressLint;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //  private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    Button b1, b2, b3, b4;
    EditText email_1, pass_1;
int num;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.registerbtn);

        b4 = (Button) findViewById(R.id.skip);
        email_1 = (EditText) findViewById(R.id.email);
        pass_1 = (EditText) findViewById(R.id.pword);
        //click event of register button
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new  Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });

        //click event of skip button

        b4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });


// getIntent() is a method from the started activity
        Intent myIntent = getIntent(); // gets the previously created intent
        String id = myIntent.getStringExtra("id"); // will return "FirstKeyValue"
        String name = myIntent.getStringExtra("name"); // will return "SecondKeyValue"
        final String email = myIntent.getStringExtra("email");
        mProgressView = findViewById(R.id.login_progress);

        b1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaill = email_1.getText().toString();

                Intent i = new Intent(LoginActivity.this, MapsActivity.class);
                startActivity(i);


            }
        });


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @SuppressLint("StaticFieldLeak")
    class get_request extends AsyncTask<String, Void, String> {

        RegisterActivity act;
     int NUM;
        public get_request(int num) {
this.NUM=num;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(act.getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            JSONObject json= null;
            try {
                json = new JSONObject(s);
                String id=json.getString("id");
                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
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

  add.put("num",NUM);
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

