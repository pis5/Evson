package com.example.mkass.evson;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import customfonts.MyEditText;
import customfonts.MyTextView;
import entities.Personne;

public class signin extends AppCompatActivity {

    TextView create;

    Typeface fonts1;

    MyEditText emailET;
    MyEditText passwordET;
    MyTextView errorMsgET;
    MyTextView btnSignIn;

    ProgressBar progressBar;
    Personne P=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        create = (TextView)findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(signin.this, signup.class);
                startActivity(it);

            }
        });




        fonts1 =  Typeface.createFromAsset(signin.this.getAssets(),
                "fonts/Lato-Regular.ttf");




        TextView t4 =(TextView)findViewById(R.id.create);
        t4.setTypeface(fonts1);

        emailET = (MyEditText)findViewById(R.id.email);
        passwordET = (MyEditText)findViewById(R.id.password);
        errorMsgET = (MyTextView)findViewById(R.id.errorMsg);
        btnSignIn = (MyTextView)findViewById(R.id.signin1);
        progressBar = (ProgressBar)findViewById(R.id.progress);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();

            }
        });

    }

    /**
     * Method gets triggered when Login button is clicked
     *
     */
    public void loginUser(){
        // Get Email Edit View Value
        String email = emailET.getText().toString();
        // Get Password Edit View Value
        String password = passwordET.getText().toString();
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull(email) && Utility.isNotNull(password)){
            // When Email entered is Valid
            if(Utility.validate(email)){
                // Put Http parameter username with value of Email Edit View control
                params.put("email", email.trim());
                // Put Http parameter password with value of Password Edit Value control
                params.put("motDePasse", password);
                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);
            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        progressBar.setVisibility(View.VISIBLE);
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        String ip =getString(R.string.ipAdress);
        AsyncHttpResponseHandler RH=new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                progressBar.setVisibility(View.GONE);
                try {
                    // JSON Object

                    // When the JSON response has status boolean value assigned with true
                    if((!response.equals(""))&&!response.equals(null)){

                        // Navigate to Home screen
                        ObjectMapper mapper = new ObjectMapper();
                        Log.i("test tets", response);
                        P = mapper.readValue(response, Personne.class);


                        Toast.makeText(getApplicationContext(), P.getNom()+" "+P.getPrenom()+ " connecté", Toast.LENGTH_LONG).show();


                        Intent it = new Intent(signin.this, HomeActivity.class);
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        it.putExtra("personne",P);
                        startActivity(it);
                        finish();
                    }
                    // Else display error message
                    else{
                        errorMsgET.setText("E-Mail ou mot de passe incorrect");
                        Toast.makeText(getApplicationContext(), "E-Mail ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                    }
                } catch (JsonParseException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // When the response returned by REST has Http response code other than '200'

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                progressBar.setVisibility(View.GONE);
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        };

        client.get( ip +"/login/dologin",params ,RH);


    }

}
