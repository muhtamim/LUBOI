package com.neelima.lu.luboi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private String state;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final EditText email=(EditText)findViewById(R.id.email);
        sharedPreferences = getSharedPreferences("luboi.com", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        final EditText password=(EditText)findViewById(R.id.password);
        final EditText fullname=(EditText)findViewById(R.id.fullname);
        final EditText phone=(EditText)findViewById(R.id.phonenumber);
        final EditText address=(EditText)findViewById(R.id.address);
        final EditText pincode=(EditText)findViewById(R.id.pincode);
        final RelativeLayout progress=(RelativeLayout)findViewById(R.id.progress);
        Button emailsign=(Button)findViewById(R.id.email_signup);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");
        String[] items = new String[]{ "Sylhet Sadar","Kamal Bazar","Bondor","Kodomtoli","shekgat"
        ,"Ragib Nagar","Zindabazar","Chondipul","TilaGor","Amborkana","Shahieidga","Kumarpar","Modina Market","Shibgong","Jitumiar Point","Surma Tower",
        "Rongmohol Tower","Uposhor","Subhanigat","Barutkana Point","Jallroad","Dupadigirpar","Botashor","Puran Bridg","Kamal Bazar","Babna Point",
        "Jollar par","Nayorpul"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state=parent.getItemAtPosition(position).toString();
                Log.i("Location",state);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                state="Kamal Bazar";
                Log.i("Location",state);
            }
        });
        emailsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckInternetConnection.checkConnection(getApplicationContext()))
                {
                    if(!email.getText().toString().equals("") || !password.getText().toString().equals("")
                            || !fullname.getText().toString().equals("") || !phone.getText().toString().equals("")
                            || !address.getText().toString().equals("") || !pincode.getText().toString().equals(""))
                    {


                        if(password.getText().toString().length() >= 8 )
                        if(email.getText().toString().length()>=1)


                        {
                            progress.setVisibility(View.VISIBLE);
                            mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        myRef.child(email.getText().toString().replaceAll("[.]","_")).child("details").child("email").setValue(email.getText().toString());
                                        myRef.child(email.getText().toString().replaceAll("[.]","_")).child("details").child("fullname").setValue(fullname.getText().toString());
                                        myRef.child(email.getText().toString().replaceAll("[.]","_")).child("details").child("address").setValue(address.getText().toString());
                                        myRef.child(email.getText().toString().replaceAll("[.]","_")).child("details").child("pincode").setValue(pincode.getText().toString());
                                        myRef.child(email.getText().toString().replaceAll("[.]","_")).child("details").child("phone").setValue(phone.getText().toString());
                                        myRef.child(email.getText().toString().replaceAll("[.]","_")).child("details").child("Location").setValue(state);
                                        sharedPreferences.edit().putString("handle",email.getText().toString().replaceAll("[.]","_")).apply();
                                        sharedPreferences.edit().putString("email", email.getText().toString()).apply();
                                        sharedPreferences.edit().putString("password", password.getText().toString()).apply();
                                        sharedPreferences.edit().putString("login", "1").apply();
                                        sharedPreferences.edit().putString("fullname",fullname.getText().toString()).apply();
                                        sharedPreferences.edit().putString("address",address.getText().toString()).apply();
                                        sharedPreferences.edit().putString("pincode",pincode.getText().toString()).apply();
                                        sharedPreferences.edit().putString("phone",phone.getText().toString()).apply();
                                        sharedPreferences.edit().putString("Location",state).apply();
                                        progress.setVisibility(View.GONE);
                                        startActivity(new Intent(getApplicationContext(), landingpage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                    }



                                    else
                                    {
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Server Error.", Toast.LENGTH_LONG).show();
                                        email.setError("Email is required");
                                        email.requestFocus();
                                        password.setError("Password is required");
                                        password.requestFocus();

                                    }




                                }




                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Password should be minimum 8 character and valid Email is required", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Email is required", Toast.LENGTH_LONG).show();
                        }


                    }



                    else {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Fields cannot be blank.", Toast.LENGTH_LONG).show();
                        email.setError("Email is required");
                        email.requestFocus();
                        password.setError("Password is required");
                        password.requestFocus();
                        fullname.setError("Full Name is required");
                        fullname.requestFocus();
                    }



                }else
                {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "No Internet.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
