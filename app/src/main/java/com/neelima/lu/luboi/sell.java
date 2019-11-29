package com.neelima.lu.luboi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sell extends AppCompatActivity {

    private Button frontiamge,backimage,postad;
    private SharedPreferences sharedPreferences;
    private String coursess,semesters,subjects,subjectid,imageid1,imageid2;
    private CharSequence[] options={"Take Photo","Gallery","Close"};
    private Integer REQUEST_CAMERA = 101, SELECT_FILE = 102;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,

    };
    private boolean f,f2;
    private RelativeLayout  progresssell;


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        sharedPreferences = getSharedPreferences("luboi.com", MODE_PRIVATE);
        frontiamge=(Button)findViewById(R.id.frontimage);
        backimage=(Button)findViewById(R.id.backimage);
        postad=(Button)findViewById(R.id.postad);
        final EditText book_name=(EditText)findViewById(R.id.book_name);
        final EditText prices=(EditText)findViewById(R.id.book_price);
        final EditText desc=(EditText)findViewById(R.id.book_desc);
        final Spinner spinnercourse=(Spinner)findViewById(R.id.spinnercourse);
        final Spinner spinnersemester=(Spinner)findViewById(R.id.spinnersemester);
        final Spinner spinnersubject=(Spinner)findViewById(R.id.spinnersubject);
        progresssell=(RelativeLayout)findViewById(R.id.progressell);
        subjectid=FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                .child("Myad").push().getKey();


        //BBA list
        final String[] bcom1 = new String[]{"financial accounting", "mercantile laws", "workshop on computer applications", "micro economics", "funtional communication skills-1", "principles of management and organization behavior", "workshop on excel modelling"};
        final String[] bcom2 = new String[]{"financial accounting-2", "workshopon computerized accounting", "company law", "environment and ecology", "micro economics", "advanced workshopon excel modelling", "communication skills-2"};
        final String[] bcom3 = new String[]{"auditing theory and practice", "corporate accounting", "meetings incentives conventions and exhibitions", "business ethics and values", "essentials of marketing", "analytical skills-1", "quantitative techniques"};
        final String[] bcom4 = new String[]{"corporate accounting 2", "cost accounting", "e-commerce", "research methodology", "enterpreneurship development", "workshop on business plan", "analytical skills-2"};
        final String[] bcom5 = new String[]{"managment accounting", "income tax laws", "workshopon practical taxation", "indian financial system", "banking insurance", "general bank operations", "business tourism and event management"};
        final String[] bcom6 = new String[]{"indirect tax laws", "legal aspects in banking and insurance", "indian economics problemsand policies", "basic financial management", "travel agency and tour operartion", "corporate strategy", "marketing of financial servics", "communication skills-3"};
        final String[] bcom7 = new String[]{"financial accounting", "mercantile laws", "workshop on computer applications", "micro economics", "funtional communication skills-1", "principles of management and organization behavior", "workshop on excel modelling"};
        final String[] bcom8 = new String[]{"financial accounting-2", "workshopon computerized accounting", "company law", "environment and ecology", "micro economics", "advanced workshopon excel modelling", "communication skills-2"};
        final String[] bcom9 = new String[]{"auditing theory and practice", "corporate accounting", "meetings incentives conventions and exhibitions", "business ethics and values", "essentials of marketing", "analytical skills-1", "quantitative techniques"};
        final String[] bcom10 = new String[]{"corporate accounting 2", "cost accounting", "e-commerce", "research methodology", "enterpreneurship development", "workshop on business plan", "analytical skills-2"};
        final String[] bcom11 = new String[]{"managment accounting", "income tax laws", "workshopon practical taxation", "indian financial system", "banking insurance", "general bank operations", "business tourism and event management"};
        final String[] bcom12 = new String[]{"indirect tax laws", "legal aspects in banking and insurance", "indian economics problemsand policies", "basic financial management", "travel agency and tour operartion", "corporate strategy", "marketing of financial servics", "communication skills-3"};

        //cse list
        final String[] cse1 = new String[]{"Maths", "Physics", "Basic electrical and electronics engineering", "Mechanics", "Computer programming", "Environmental studies"};
        final String[] cse2 = new String[]{"Biology", "Enginering chemistry", "OOPs", "Engineering Graphics", "Differential equations", "Applied physics"};
        final String[] cse3 = new String[]{"Data structure and algorithm", "Computer organisation and design", "Digital electronics", "Python programming", "Database", "Discrete mathematics"};
        final String[] cse4 = new String[]{"Computer networks", "Programming in java", "Operatin Systems", "Software engineering", "Automata", "Probability and statics", "Design and analysis of algorithm"};
        final String[] cse5 = new String[]{"Computer graphics and visualization", "Compiler design", "Cyber security", "SAP", "Quantative aptitude"};
        final String[] cse6 = new String[]{"Cloud computing", "Information system security", "Securing computing systems", "Artificial intelligence", "Reasoning aptitude"};
        final String[] cse7 = new String[]{"Information security", "Penetration testing", "Mobile computing"};
        final String[] cse8 = new String[]{"Cryptography", "Data storage", "Software management", "Open source"};
        final String[] cse9 = new String[]{"Computer graphics and visualization", "Compiler design", "Cyber security", "SAP", "Quantative aptitude"};
        final String[] cse10 = new String[]{"Cloud computing", "Information system security", "Securing computing systems", "Artificial intelligence", "Reasoning aptitude"};
        final String[] cse11 = new String[]{"Information security", "Penetration testing", "Mobile computing"};
        final String[] cse12 = new String[]{"Cryptography", "Data storage", "Software management", "Open source"};

        //Law list
        final String[] bsc1 = new String[]{"mercantile laws", "workshop on computer applications", "environmental studies", "micro economics", "principles and practices of management", "entrepreneurship development", "communication skills-1"};
        final String[] bsc2 = new String[]{"financial accounting", "macro economics", "essentials of organization behaviour", "workshop on excel modelling", "business ethics", "business communication skills-1"};
        final String[] bsc3 = new String[]{"cost and management accounting", "banking and insurance", "essentials of marketing", "introduction to acting", "quantitative aptitude", "business communication skills-2", "business mathematics"};
        final String[] bsc4 = new String[]{"company law", "basic financial management", "human resource management", "building a character", "reasoning aptitude", "soft skills", "quantitative techniques"};
        final String[] bsc5 = new String[]{"research methodology", "business environment", "summer project", "event management", "essentials of consumer behavior", "production and perations management", "creating a role"};
        final String[] bsc6 = new String[]{"introduction to international business", "corporate strategy", "e-business", "fundamentals of promotions managament", "selling and sales management", "scene work"};
        final String[] bsc7 = new String[]{"mercantile laws", "workshop on computer applications", "environmental studies", "micro economics", "principles and practices of management", "entrepreneurship development", "communication skills-1"};
        final String[] bsc8 = new String[]{"financial accounting", "macro economics", "essentials of organization behaviour", "workshop on excel modelling", "business ethics", "business communication skills-1"};
        final String[] bsc9 = new String[]{"cost and management accounting", "banking and insurance", "essentials of marketing", "introduction to acting", "quantitative aptitude", "business communication skills-2", "business mathematics"};
        final String[] bsc10 = new String[]{"company law", "basic financial management", "human resource management", "building a character", "reasoning aptitude", "soft skills", "quantitative techniques"};
        final String[] bsc11 = new String[]{"research methodology", "business environment", "summer project", "event management", "essentials of consumer behavior", "production and perations management", "creating a role"};
        final String[] bsc12 = new String[]{"introduction to international business", "corporate strategy", "e-business", "fundamentals of promotions managament", "selling and sales management", "scene work"};
        //Einglish list
        final String[] mech1 = new String[]{"Maths", "Physics", "Basic electrical and electronics engineering", "Mechanics", "Computer programming", "Environmental studies"};
        final String[] mech2 = new String[]{"Biology", "Enginering chemistry", "OOPs", "Engineering Graphics", "Differential equations", "Applied physics"};
        final String[] mech3 = new String[]{"Kinematics", "Thermodynamics", "Material technology", "Electrical technology", "Industrial drafting and machine design"};
        final String[] mech4 = new String[]{"Dynamicsd of machinery", "Mathematics for engineering", "Manufacturing process", "Fluid mechanics"};
        final String[] mech5 = new String[]{"Dynamics of machinery 2", "Power plant engineering", "Fluid power engineering", "Heat and mass transfer", "Controlengineering"};
        final String[] mech6 = new String[]{"Controlengineering", "Machine design 1", "Thermal engineering", "Law for enguineers"};
        final String[] mech7 = new String[]{"Computer aided design", "Machine design 2", "Computer aided manufacturing"};
        final String[] mech8 = new String[]{"Major Projects"};
        final String[] mech9 = new String[]{"Computer aided design", "Machine design 2", "Computer aided manufacturing"};
        final String[] mech10 = new String[]{"Major Projects"};

        //civil list
        final String[] cvl1 = new String[]{"Maths", "Physics", "Basic electrical and electronics engineering", "Mechanics", "Computer programming", "Environmental studies"};
        final String[] cvl2 = new String[]{"Biology", "Enginering chemistry", "OOPs", "Engineering Graphics", "Differential equations", "Applied physics"};
        final String[] cvl3 = new String[]{"Building and town planning", "Concrete technology", "Surveying 1", "Structural mechanics", "Ethics and values"};
        final String[] cvl4 = new String[]{"Geology and geotechnical engineering", "Structural mechanics 2", "Building construction", "Surveying 2", "Fluid mechanics"};
        final String[] cvl5 = new String[]{"Design of structure 1", "Water resources and irrrigation", "Transportation engineering  1", "Advanced construction", "Foundation engineering"};
        final String[] cvl6 = new String[]{"Design of structure 2", "Environmental engineering", "Transpotation engineering 2", "Law for engineer"};
        final String[] cvl7 = new String[]{"Professional pratices", "Design of structures 3", "Construction and project management"};
        final String[] cvl8 = new String[]{"Major Projects"};

        //EEE list
        final String[] ece1 = new String[]{"Maths", "Physics", "Basic electrical and electronics engineering", "Mechanics", "Computer programming", "Environmental studies"};
        final String[] ece2 = new String[]{"Biology", "Enginering chemistry", "OOPs", "Engineering Graphics", "Differential equations", "Applied physics"};
        final String[] ece3 = new String[]{"Electronic devices and circuits 1", "Digital electronics", "Network analysics", "Linear control system", "Vector calculus", "Economics for engineeering"};
        final String[] ece4 = new String[]{"Signal and systems", "Electronics devices and circuits 2", "Electrical machines and drives", "Communication systems", "Electronics design"};
        final String[] ece5 = new String[]{"Electromagnetics engineering", "Integrated circuits and applications", "Microprocessor and computer architecture", "Modern measurement and instrumentation", "Digital electronics", "Microprocessor and microcontroller"};
        final String[] ece6 = new String[]{"Digital system design", "Antenna and wave propagation", "Fibre opticss", "Digital integrated circuit design", "Telecom  networks", "Modern processor archetecture"};
        final String[] ece7 = new String[]{"Satellite communiocation", "Data communicaion", "Embedded systems", "Microwave engineering", "Multimedia systems", "Wireless sensor", "VLSI design", "RF communication circuits"};
        final String[] ece8 = new String[]{"Major Projects"};
        final String[] ece9 = new String[]{"Electromagnetics engineering", "Integrated circuits and applications", "Microprocessor and computer architecture", "Modern measurement and instrumentation", "Digital electronics", "Microprocessor and microcontroller"};
        final String[] ece10 = new String[]{"Digital system design", "Antenna and wave propagation", "Fibre opticss", "Digital integrated circuit design", "Telecom  networks", "Modern processor archetecture"};
        final String[] ece11 = new String[]{"Satellite communiocation", "Data communicaion", "Embedded systems", "Microwave engineering", "Multimedia systems", "Wireless sensor", "VLSI design", "RF communication circuits"};
        final String[] ece12 = new String[]{"Major Projects"};


        String[] items = new String[]{ "Department of CSE","Department of EEE","Department of Civil","Department of English","Department of BBA","Department of Law"};
        String[] items1 = new String[]{ "Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6","Semester 7","Semester 8"};
        String[] items2 = new String[]{ "Semester 1","Semester 2","Semester 3","Semester 4","Semester 5","Semester 6"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        final ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        final ArrayAdapter<String> adapterss = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        spinnercourse.setAdapter(adapter);
        postad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!book_name.getText().toString().equals("") && !subjects.equals("") && !coursess.equals("") && !semesters.equals(""))
                {
                    progresssell.setVisibility(View.VISIBLE);
                    Log.i("demo subjectid",subjectid);
                    Log.i("demo subject",subjects);
                    Log.i("demo course",coursess);
                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child(subjectid).child("name").setValue(book_name.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child(subjectid).child("price").setValue(prices.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child(subjectid).child("course").setValue(coursess);
                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child(subjectid).child("semester").setValue(semesters);
                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child(subjectid).child("subjects").setValue(subjects);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("name").setValue(book_name.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("price").setValue(prices.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("course").setValue(coursess);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("semester").setValue(semesters);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("subject").setValue(subjects);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("Seller Address").setValue(sharedPreferences.getString("address",""));
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("Phone Number").setValue(sharedPreferences.getString("phone",""));
                    FirebaseDatabase.getInstance().getReference().child("users").child(sharedPreferences.getString("handle",""))
                            .child("Myad").child(subjectid).child("image").setValue(imageid1);
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("Description").setValue(desc.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("Seller").setValue(sharedPreferences.getString("name",""));
                    FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                            .child(subjectid).child("email").setValue(sharedPreferences.getString("email",""));
                    Toast.makeText(getApplicationContext(),"Ad Published",Toast.LENGTH_LONG).show();
                    progresssell.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(),landingpage.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid Feilds or Empty Feilds.",Toast.LENGTH_LONG).show();
                }

            }
        });

        spinnercourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coursess=parent.getItemAtPosition(position).toString();
                Log.i("Course",coursess);
                if(coursess.equals("Department of BBA") || coursess.equals("Department of Law"))
                {
                    spinnersemester.setAdapter(adapterss);
                }else
                {
                    spinnersemester.setAdapter(adapters);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                 coursess="";
            }
        });

        spinnersemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semesters=parent.getItemAtPosition(position).toString();
                if(semesters.equals("Semester 1") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse1);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 2") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse2);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 3") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse3);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 4") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse4);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 5") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse5);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 6") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse6);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 7") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse7);
                    spinnersubject.setAdapter(adapterss);
                }
                if(semesters.equals("Semester 8") && coursess.equals("Department of CSE"))
                {
                    ArrayAdapter<String> adapterss = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, cse8);
                    spinnersubject.setAdapter(adapterss);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               semesters="";
            }
        });
        spinnersubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjects=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                subjects="";
            }
        });
        frontiamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=true;
                Log.i("f status", ""+f);
                final AlertDialog.Builder builder = new AlertDialog.Builder(sell.this);
                builder.setTitle("Select Options").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i].equals("Take Photo"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                openCamera();
                            }
                        }
                        else if(options[i].equals("Gallery"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                gallerys();
                            }
                        }
                        else if(options[i].equals(""))
                        {
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f2=true;
                final AlertDialog.Builder builder = new AlertDialog.Builder(sell.this);
                builder.setTitle("Select Options").setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i].equals("Take Photo"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                openCamera();
                            }
                        }
                        else if(options[i].equals("Gallery"))
                        {
                            if (!hasPermissions(sell.this)) {
                                ActivityCompat.requestPermissions(sell.this, PERMISSIONS, PERMISSION_ALL);
                            } else {
                                gallerys();
                            }
                        }
                        else if(options[i].equals(""))
                        {
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                progresssell.setVisibility(View.VISIBLE);
                uploadfront(bitmap);
            }
        } else if (requestCode == SELECT_FILE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imagedata = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(sell.this.getContentResolver(), imagedata);
                    uploadfront(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void gallerys() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select image"), SELECT_FILE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0 && grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
            if (requestCode == REQUEST_CAMERA)
                openCamera();
            else if (requestCode == SELECT_FILE)
                gallerys();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(sell.this);
            builder.setMessage("Permission not granted").setTitle("Permission Error");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void uploadfront(Bitmap bitmap1) {
        String date=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference refrence = storage.getReferenceFromUrl("gs://lubook-127d0.appspot.com");
        final StorageReference storageReference = refrence.child(md5(date));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = storageReference.putBytes(data);
        Log.i("upload", "uploads started1");
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Log.i("image", task.getResult().toString()+ "status"+f);
                            imageid1=task.getResult().toString();
                            if(f)
                            {
                                FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                                        .child(subjectid).child("image2").setValue(imageid1);
                                f=false;
                                progresssell.setVisibility(View.INVISIBLE);
                            }
                            if(f2)
                            {

                                FirebaseDatabase.getInstance().getReference().child("Books").child(coursess).child(semesters).child(subjects)
                                        .child(subjectid).child("image").setValue(imageid1);
                                Log.i("image logged", task.getResult().toString());
                                f2=false;
                                progresssell.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
            }
        });
    }
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
