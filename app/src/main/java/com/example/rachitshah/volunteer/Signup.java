package com.example.rachitshah.volunteer;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Signup extends AppCompatActivity {
    String path;
    Uri imageUri;
    ImageView img;
    Spinner gender;
    DatabaseReference myref;
    private StorageReference fileRef;
    private StorageReference imageReference;
    FirebaseAuth mauth;

    private Uri fileUri;

    TextView lin, sup;
    private static final int PICK_IMAGE = 100;
    EditText name, email, phone, pswd, address, lic, dob;
    String vemail,passwrd,vname,mob,vaddress,vdob,vlic,vgender,key;
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_signup);


        mauth= FirebaseAuth.getInstance();
        myref = FirebaseDatabase.getInstance().getReference("Volunteers");
        //mstorage = FirebaseStorage.getInstance().getReference();
        sup = (TextView) findViewById(R.id.sbtn);
        img = (ImageView)findViewById(R.id.profile);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.mono);
        pswd = (EditText) findViewById(R.id.pass);
        address = (EditText) findViewById(R.id.add);
        lic = (EditText) findViewById(R.id.lic);
        img = (ImageView) findViewById(R.id.profile);
        dob = (EditText) findViewById(R.id.dob);
        gender = (Spinner) findViewById(R.id.gen);
        lin = (TextView)findViewById(R.id.login);

        imageReference = FirebaseStorage.getInstance().getReference().child("images");
        fileRef = null;


        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar crdate = Calendar.getInstance();
                    int yr = crdate.get(Calendar.YEAR);
                    int mn = crdate.get(Calendar.MONTH);
                    int dd = crdate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog;
                    datePickerDialog = new DatePickerDialog(Signup.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dob.setText(year + "-" + month + "-" + dayOfMonth);
                        }
                    }, yr, mn, dd);

                    datePickerDialog.setTitle("Select_Date");
                    datePickerDialog.show();
                }
            }
        });

        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
        //Image Selector
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                startActivityForResult(gallery, PICK_IMAGE);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        //Signup Button Listener
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean notnull;
                vemail = email.getText().toString().trim();
                passwrd = pswd.getText().toString().trim();
                vname = name.getText().toString().trim();
                vaddress= address.getText().toString().trim();
                mob = phone.getText().toString().trim();
                vdob = dob.getText().toString().trim();
                vlic = lic.getText().toString().trim();
                vgender = gender.getSelectedItem().toString().trim();
                notnull = isnotnull();

                if (notnull) {
                    savedata();

                    mauth.createUserWithEmailAndPassword(vemail,passwrd).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Signup.this ,"Sign up error",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(Signup.this, vemail,Toast.LENGTH_SHORT).show();
                            }
                            else{

                                FirebaseUser user = mauth.getCurrentUser();
                                key= user.getUid();

                                Volunteers vol = new Volunteers();
                                vol.setKey(key);
                                vol.setVname(vname);
                                vol.setemail(vemail);
                                vol.setPasswrd(passwrd);
                                vol.setMob(mob);
                                vol.setVaddress(vaddress);
                                vol.setVdob(vdob);
                                vol.setVlic(vlic);
                                vol.setVgender(vgender);

                                myref.child(key).setValue(vol);


                                startActivity(new Intent(Signup.this, Login.class));
                                finish();

                            }
                        }
                    });




                } else {
                    Toast toast = Toast.makeText(Signup.this, "Something is Wrong", Toast.LENGTH_LONG);
                    toast.show();
                }
            }


            private void savedata() {
                SharedPreferences sharedPreferences = getSharedPreferences("Volunteer", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Key",key);
                editor.putString("Name", name.getText().toString());
                editor.putString("Email", email.getText().toString());
                editor.putString("Phone", phone.getText().toString());
                editor.putString("Address", address.getText().toString());
                editor.putString("lic", lic.getText().toString());
                editor.putString("Password", pswd.getText().toString());
                editor.putString("Gender", gender.getSelectedItem().toString());
                editor.putString("Profile", path);
                editor.commit();

            }
        });

    }

    private Boolean isnotnull() {
        boolean notnull = false;
        String n, e, p, ph, add, licno, gen, bd;
        n = name.getText().toString();
        e = email.getText().toString();
        p = pswd.getText().toString();
        ph = phone.getText().toString();
        add = address.getText().toString();
        licno = lic.getText().toString();
        gen = gender.getSelectedItem().toString();
        bd = dob.getText().toString();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = e;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        Boolean check = true;
        if (n.length() == 0) {
            name.setError("Name cannot be Empty");
            check = false;
        }
        if (gen.length() == 0) {
            gender.setPrompt("Gender cannot be Empty");
            check = false;
        }
        if (bd.length() == 0) {
            dob.setError("Date of birth cannot be Empty");
            check = false;
        }


        if (!matcher.matches()) {
            email.setError("Email not Valid");
            check = false;
        }
        if (p.length() == 0) {
            pswd.setError("Password cannot be Empty");
            check = false;
        }
        if (ph.length() != 10) {
            phone.setError("Phone number not Valid");
            check = false;
        }
        if (add.length() == 0) {
            address.setError("Address cannot be Empty");
            check = false;
        }
        if (licno.length() == 0) {
            lic.setError("License cannot be Empty");
            check = false;
        }
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                UploadImageFileToFirebaseStorage(fileUri);
                img.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void UploadImageFileToFirebaseStorage(Uri fileUri) {
        fileRef = imageReference.child(UUID.randomUUID().toString() + "." + getFileExtension(fileUri));

        fileRef.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                /*String name = taskSnapshot.getMetadata().getName();
                //final String url = taskSnapshot.getDownloadUrl().toString();
               // myref.child(key).child("Image").setValue(url);
            */

                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri downloadUrl = urlTask.getResult();

                final String sdownload_url = String.valueOf(downloadUrl);
                myref.child(key).child("Image").setValue(downloadUrl);



            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }
}
