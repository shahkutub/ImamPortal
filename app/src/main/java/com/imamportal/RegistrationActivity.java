package com.imamportal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.BitmapUtils;
import com.imamportal.utils.PersistData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RegistrationActivity extends AppCompatActivity {

    Context context;
    private ImageView imgcam;
    private CircleImageView imgPic;
    private File file;
    String picture = "";
    private static File dir = null;
    String imageLocal = "";
    public final int imagecaptureid = 0;
    public final int galarytakid = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;

    private EditText input_name,input_email,input_UserName,input_password,input_password_confirm,input_nid,input_mosq,
    input_mosq_address,input_wordno,input_postoffice,input_village;
    Spinner spinnerPodobi,spinnerDivision,spinnerCity,spinnerDistrict,spinnerUpojila,spinnerUnion;
    private TextView tvBirthdate;
    private Button btnSubmit;

    private String name,email,username,password,confirmpass,birthdate,nid,mosqname,mosqaddress,wardno,postoffice,village,podobi="",division="",
    city="",district="",upojila="",union="",photo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context= this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        innitUi();
    }

    private void innitUi() {
        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        input_name = (EditText)findViewById(R.id.input_name);
        input_email = (EditText)findViewById(R.id.input_email);
        input_UserName = (EditText)findViewById(R.id.input_UserName);
        input_password = (EditText)findViewById(R.id.input_password);
        input_password_confirm = (EditText)findViewById(R.id.input_password_confirm);
        input_nid = (EditText)findViewById(R.id.input_nid);
        input_mosq = (EditText)findViewById(R.id.input_mosq);
        input_mosq_address = (EditText)findViewById(R.id.input_mosq_address);
        input_wordno = (EditText)findViewById(R.id.input_wordno);
        input_postoffice = (EditText)findViewById(R.id.input_postoffice);
        input_village = (EditText)findViewById(R.id.input_village);

        spinnerPodobi = (Spinner) findViewById(R.id.spinnerPodobi);
        List<String> listPodobi = new ArrayList<String>();
        listPodobi.add("নির্বাচন করুন");
        listPodobi.add("ইমাম");
        listPodobi.add("মুয়াজ্জিন");
        listPodobi.add("অন্যান্য");
        ArrayAdapter<String> adapterPodobi = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, listPodobi);
        spinnerPodobi.setAdapter(adapterPodobi);

        spinnerPodobi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!spinnerPodobi.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    podobi = spinnerPodobi.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinnerDivision = (Spinner) findViewById(R.id.spinnerDivision);
        List<String> listDivision = new ArrayList<String>();
        listDivision.add("নির্বাচন করুন");
        listDivision.add("বরিশাল");
        listDivision.add("চট্টগ্রাম");
        listDivision.add("ঢাকা");
        listDivision.add("সিলেট");
        listDivision.add("রংপুর");
        listDivision.add("রাজশাহী");
        listDivision.add("ময়মনসিংহ");
        ArrayAdapter<String> adapterDivision = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, listDivision);
        spinnerDivision.setAdapter(adapterDivision);

        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!spinnerDivision.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    division = spinnerDivision.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        List<String> listCity = new ArrayList<String>();
        listCity.add("নির্বাচন করুন");
        listCity.add("বরিশাল");
        listCity.add("চট্টগ্রাম");
        listCity.add("ঢাকা");
        listCity.add("সিলেট");
        listCity.add("রংপুর");
        listCity.add("রাজশাহী");
        listCity.add("ময়মনসিংহ");
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, listCity);
        spinnerCity.setAdapter(adapterCity);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!spinnerCity.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    city = spinnerCity.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerUpojila = (Spinner) findViewById(R.id.spinnerUpojila);
        spinnerUnion = (Spinner) findViewById(R.id.spinnerUnion);

        imgcam = (ImageView)findViewById(R.id.imgcam);
        imgPic = (CircleImageView) findViewById(R.id.imgPic);

        imgcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCaptureDialogue();
            }
        });
        if(!checkPermission()){
            requestPermission();
        }


        tvBirthdate = (TextView) findViewById(R.id.tvBirthdate);
        tvBirthdate.setText(new SimpleDateFormat("dd-MM-yyy").format(new Date()));

        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog startTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tvBirthdate.setText(new SimpleDateFormat("dd-MM-yyy").format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        tvBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime.show();
            }
        });


        btnSubmit = (Button)findViewById(R.id.btnSubmit);
//        password,confirmpass,nid,mosqname,mosqaddress,wardno,postoffice,village,pdobi,division,
//                city,district,upojila,union,photo
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=input_name.getText().toString();
                email=input_email.getText().toString();
                username=input_UserName.getText().toString();
                password=input_password.getText().toString();
                confirmpass=input_password_confirm.getText().toString();
                birthdate=tvBirthdate.getText().toString();
                nid=input_nid.getText().toString();
                mosqname=input_mosq.getText().toString();
                mosqaddress = input_mosq_address.getText().toString();
                wardno=input_wordno.getText().toString();
                postoffice=input_postoffice.getText().toString();
                village=input_village.getText().toString();

                if(name.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","name is required");
                }else if(email.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","email is required");
                }else if(username.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","username is required");
                }else if(password.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","password is required");
                }else if(confirmpass.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","confirmpass is required");
                }else if(podobi.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","podobi is required");
                }else if(birthdate.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","Birth date is required");
                }else if(nid.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","nid is required");
                }else if(mosqname.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","Mosq name is required");
                }else if(mosqaddress.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","Mosq address is required");
                }else if(division.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","Division is required");
                }else if(city.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","City is required");
                }else if(wardno.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","wardno is required");
                }else if(district.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","District is required");
                }else if(upojila.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","Upojila is required");
                }else if(union.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","Union is required");
                }else if(postoffice.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","post office is required");
                }else if(village.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","village is required");
                }


            }
        });
    }

    private void imageCaptureDialogue() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.chang_photo_dialogue);

        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout tvUseCam = (LinearLayout) dialog
                .findViewById(R.id.tvUseCam);
        LinearLayout tvRoll = (LinearLayout) dialog
                .findViewById(R.id.tvRoll);
        LinearLayout tvCance = (LinearLayout) dialog
                .findViewById(R.id.tvCance);


        tvRoll.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                AppConstant.isGallery = true;
//                if (ActivityCompat.checkSelfPermission(con, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions((Activity) ProfileSettingsActivity.this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AppConstant.WRITEEXTERNAL_PERMISSION_RUNTIME);
//                    dialog.dismiss();
//                } else {
                final Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), galarytakid);
                dialog.dismiss();
                // }
            }




        });

        tvUseCam.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                AppConstant.isGallery = false;
                final Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, imagecaptureid);
                dialog.dismiss();
                // }
                //}
            }


//                if (ContextCompat.checkSelfPermission(con,Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    requestPermissions(new String[]{Manifest.permission.CAMERA},
//                            1);
//
//                }else if(ContextCompat.checkSelfPermission(con,Manifest.permission.CAMERA)
//                        == PackageManager.PERMISSION_GRANTED){
//                    final Intent i = new Intent(
//                            "android.media.action.IMAGE_CAPTURE");
//                    startActivityForResult(i, imagecaptureid);
//                    dialog.dismiss();
//                }


        });

        tvCance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    private void getBirthDate(){


    }



    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED
                && result1 == PackageManager.PERMISSION_GRANTED
                && result2 == PackageManager.PERMISSION_GRANTED
                && result3 == PackageManager.PERMISSION_GRANTED
                && result4 == PackageManager.PERMISSION_GRANTED;

    }


    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA,
                        ACCESS_COARSE_LOCATION,READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readPhoneAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    //  Toast.makeText(con, ""+imei, Toast.LENGTH_SHORT).show();

                    if (locationAccepted && cameraAccepted && readPhoneAccepted) {
                        // Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();

                    } else {

                        //Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA, READ_PHONE_STATE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("On Activity Result", "On Activity Result");
        if (requestCode == galarytakid && resultCode == Activity.RESULT_OK) {

            Log.e("In gallelrly", "lllll..........");
            try {

                final Uri selectedImageUri = data.getData();

                final Bitmap bitmap = BitmapFactory
                        .decodeStream(context.getContentResolver().openInputStream(
                                selectedImageUri));

                final String path = setToImageView(bitmap);
                Log.e("Bitmap >>",
                        "W: " + bitmap.getWidth() + " H: " + bitmap.getHeight());
                Log.e("path", ">>>>>" + path);
                //PersistData.setStringData(con,AppConstant.path,"");
                picture = path;
                PersistData.setStringData(context, AppConstant.localpic,path);
                Glide.with(context)
                        .load(picture)
                        .override(100,100)
                        .into(imgPic);


            } catch (final Exception e) {
                return;
            }

        } else if (requestCode == imagecaptureid
                && resultCode == Activity.RESULT_OK) {

            try {

                //onCaptureImageResult(data);
                final Bundle extras = data.getExtras();
                final Bitmap b = (Bitmap) extras.get("data");

                final String path = setToImageView(b);
                Log.e("Bitmap >>",
                        "W: " + b.getWidth() + " H: " + b.getHeight());
                picture = path;
                Log.e("path", ">>>>>" + path);
                PersistData.setStringData(context,AppConstant.localpic,path);
                PersistData.setStringData(context,AppConstant.path,"");
                Glide.with(context)
                        .load(picture)
                        .override(100,100)
                        .into(imgPic);

            } catch (final Exception e) {
                return;
            }

        }

    }



    private String setToImageView(Bitmap bitmap) {

        try {

            // if (isImage) {
            final Bitmap bit = BitmapUtils.getResizedBitmap(bitmap, 100);
            final double time = System.currentTimeMillis();

            imageLocal = saveBitmapIntoSdcard(bit, "3ss" + time + ".png");

            Log.e("camera saved URL :  ", " " + imageLocal);


        } catch (final IOException e) {
            e.printStackTrace();

            imageLocal = "";
            Log.e("camera saved URL :  ", e.toString());

        }

        return imageLocal;

    }

    private String saveBitmapIntoSdcard(Bitmap bitmap22, String filename)
            throws IOException {
        /*
         *
         * check the path and create if needed
         */
        createBaseDirctory();

        try {

            new Date();

            OutputStream out = null;
            file = new File(this.dir, "/" + filename);

            if (file.exists()) {
                file.delete();
            }

            out = new FileOutputStream(file);

            bitmap22.compress(Bitmap.CompressFormat.PNG, 50, out);

            out.flush();
            out.close();
            // Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            return file.getAbsolutePath();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createBaseDirctory() {
        final String extStorageDirectory = Environment
                .getExternalStorageDirectory().toString();
        dir = new File(extStorageDirectory + "/3ss");

        if (this.dir.mkdir()) {
            System.out.println("Directory created");
        } else {
            System.out.println("Directory is not created or exists");
        }
    }


}
