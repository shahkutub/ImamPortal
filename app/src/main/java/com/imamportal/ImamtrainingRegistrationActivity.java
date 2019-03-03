package com.imamportal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.model.NameInfo;
import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.BitmapUtils;
import com.imamportal.utils.NetInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ImamtrainingRegistrationActivity extends AppCompatActivity {

    Context context;
    private ImageView imgcam,imgPic;
    private File file;
    String picture = "";
    Bitmap bmpuser,bmpmulsonod,bmptestimo,bmpNid,bmpChairman,bmpIfa,bmpLeave,bmpAuth;
    private static File dir = null;
    String imageLocal = "";
    public final int imagecaptureid = 0;
    public final int galarytakid = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private final static int IMAGE_RESULT = 200;
    private EditText input_name,input_name_father,input_father_name_bn,input_name_mother,input_name_mosq,input_mobile,input_birtdate,
    input_mosq_address,input_wordno,input_postoffice_present,input_village_present,input_postoffficePermanent,input_villagePermanent,
            etExamName1, etBoard1,etInstitute1,etsubject1,etgpa1,etYear1,etExamName2,etBoard2,etInstitute2,etsubject2,
            etgpa2,etYear2,etBoard3,etInstitute3,etsubject3,etgpa3,etYear3,etExamName4,etBoard4,etInstitute4,etsubject4,
            etgpa4,etYear4,etExamName5,etBoard5,etInstitute5,etsubject5,etgpa5,etYear5,etExamName6,etBoard6,etInstitute6,
            tvsubject6,etgpa6,etYear6;
    Spinner spinnerCity,spinnerDistrict,spinnerUpozila,spinnerUnion,spinnerDistrictPermanent,spinnerUpojilaPermanent,spinnerUnionPermanent;
    private AppCompatButton btnSubmit;


    List<NameInfo> listDistrict = new ArrayList<>();
    List<NameInfo> listDistrictPermanent = new ArrayList<>();
    List<NameInfo> listUpozila = new ArrayList<>();
    List<NameInfo> listUpozilaPermanent = new ArrayList<>();
    List<NameInfo> listUnion = new ArrayList<>();
    List<NameInfo> listUnionPermanent = new ArrayList<>();

    private String name,nameFather,mothername,mobile,birthdate,mosqname,mosqaddress,wardno,postofficePresent,
            villagePresent,postoffficePermanent,villagePermanent,podobi="", division="",divisionId, city="",cityId="",districtPresent="",
            districtPermanent="",districtIdPresent="",districtIdPermanent="",upojilaPresent="",upojilaPermanent="",upojilaIdPresent="",
            upojilaIdPermanent="",unionPresent="",unionPermanent="",unionIdPresent="",unionIdPermanent,photo="";

    AppCompatButton btnChoseFileSonod,btnChoseFileTestimoni,btnChoseNid,btnChoseChairmanCopy,btnChoseIsfaCopy,
            btnChoseLeaveCopy,btnChoseAuthPermissionCopy;
    private ImageView imgMulsonod,imgTestimonial,imgnidFile,imgChaimanFile,imgIFAFile,imgLeaveFile,imgauthPermissionFile;
    private String imgId;

    String userPicPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imam_training_reg_form);
        context= this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        innitUi();
    }

    private void innitUi() {


        btnChoseFileSonod = (AppCompatButton)findViewById(R.id.btnChoseFileSonod);
        btnChoseFileTestimoni = (AppCompatButton)findViewById(R.id.btnChoseFileTestimoni);
        btnChoseNid = (AppCompatButton)findViewById(R.id.btnChoseNid);
        btnChoseChairmanCopy = (AppCompatButton)findViewById(R.id.btnChoseChairmanCopy);
        btnChoseIsfaCopy = (AppCompatButton)findViewById(R.id.btnChoseIsfaCopy);
        btnChoseLeaveCopy = (AppCompatButton)findViewById(R.id.btnChoseLeaveCopy);
        btnChoseAuthPermissionCopy = (AppCompatButton)findViewById(R.id.btnChoseAuthPermissionCopy);


        btnChoseFileSonod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "mulsonod";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });


        btnChoseFileTestimoni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "testimonial";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });

        btnChoseNid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "nid";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });


        btnChoseChairmanCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "chairman";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });


        btnChoseIsfaCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "ifa";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });


        btnChoseLeaveCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "leave";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });


        btnChoseAuthPermissionCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "permission";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });



        imgMulsonod = (ImageView)findViewById(R.id.imgMulsonod);
        imgTestimonial = (ImageView)findViewById(R.id.imgTestimonial);
        imgnidFile = (ImageView)findViewById(R.id.imgnidFile);
        imgChaimanFile = (ImageView)findViewById(R.id.imgChaimanFile);
        imgIFAFile = (ImageView)findViewById(R.id.imgIFAFile);
        imgLeaveFile = (ImageView)findViewById(R.id.imgLeaveFile);
        imgauthPermissionFile = (ImageView)findViewById(R.id.imgauthPermissionFile);
        imgcam = (ImageView)findViewById(R.id.imgcam);
        imgPic = (ImageView)findViewById(R.id.imgPic);

        imgcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "user";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });

        input_name = (EditText)findViewById(R.id.input_name);
        input_name_father = (EditText)findViewById(R.id.input_name_father);
        input_father_name_bn = (EditText)findViewById(R.id.input_father_name_bn);
        input_name_mother = (EditText)findViewById(R.id.input_name_mother);
        input_name_mosq = (EditText)findViewById(R.id.input_name_mosq);
        input_mobile = (EditText)findViewById(R.id.input_mobile);

        input_birtdate = (EditText)findViewById(R.id.input_birtdate);
        input_mosq_address = (EditText)findViewById(R.id.input_mosq_address);
        input_wordno = (EditText)findViewById(R.id.input_wordno);
        input_postoffice_present = (EditText)findViewById(R.id.input_postoffice_present);
        input_village_present = (EditText)findViewById(R.id.input_village_present);
        input_postoffficePermanent = (EditText)findViewById(R.id.input_postoffficePermanent);
        input_villagePermanent = (EditText)findViewById(R.id.input_villagePermanent);
        etExamName1 = (EditText)findViewById(R.id.etExamName1);
        etBoard1 = (EditText)findViewById(R.id.etBoard1);
        etInstitute1 = (EditText)findViewById(R.id.etInstitute1);
        etsubject1 = (EditText)findViewById(R.id.etsubject1);
        etgpa1 = (EditText)findViewById(R.id.etgpa1);
        etYear1 = (EditText)findViewById(R.id.etYear1);
        etExamName2 = (EditText)findViewById(R.id.etExamName2);
        etBoard2 = (EditText)findViewById(R.id.etBoard2);
        etInstitute2 = (EditText)findViewById(R.id.etInstitute2);
        etsubject2 = (EditText)findViewById(R.id.etsubject2);
        etgpa2 = (EditText)findViewById(R.id.etgpa2);
        etYear2 = (EditText)findViewById(R.id.etYear2);
        etBoard3 = (EditText)findViewById(R.id.etBoard3);
        etInstitute3 = (EditText)findViewById(R.id.etInstitute3);
        etsubject3 = (EditText)findViewById(R.id.etsubject3);
        etgpa3 = (EditText)findViewById(R.id.etgpa3);
        etYear3 = (EditText)findViewById(R.id.etYear3);
        etExamName4 = (EditText)findViewById(R.id.etExamName4);
        etBoard4 = (EditText)findViewById(R.id.etBoard4);
        etInstitute4 = (EditText)findViewById(R.id.etInstitute4);
        etsubject4 = (EditText)findViewById(R.id.etsubject4);
        etgpa4 = (EditText)findViewById(R.id.etgpa4);
        etYear4 = (EditText)findViewById(R.id.etYear4);
        etExamName5 = (EditText)findViewById(R.id.etExamName5);
        etBoard5 = (EditText)findViewById(R.id.etBoard5);
        etInstitute5 = (EditText)findViewById(R.id.etInstitute5);
        etsubject5 = (EditText)findViewById(R.id.etsubject5);
        etgpa5 = (EditText)findViewById(R.id.etgpa5);
        etYear5 = (EditText)findViewById(R.id.etYear5);
        etExamName6 = (EditText)findViewById(R.id.etExamName6);
        etBoard6 = (EditText)findViewById(R.id.etBoard6);
        etInstitute6 = (EditText)findViewById(R.id.etInstitute6);
        tvsubject6 = (EditText)findViewById(R.id.tvsubject6);
        etgpa6 = (EditText)findViewById(R.id.etgpa6);
        etYear6 = (EditText)findViewById(R.id.etYear6);



        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerUpozila = (Spinner) findViewById(R.id.spinnerUpozila);
        spinnerUnion = (Spinner) findViewById(R.id.spinnerUnion);


        spinnerDistrictPermanent = (Spinner) findViewById(R.id.spinnerDistrictPermanent);
        spinnerUpojilaPermanent = (Spinner) findViewById(R.id.spinnerUpzilaPermanent);
        spinnerUnionPermanent = (Spinner) findViewById(R.id.spinnerUnionPermanent);

        if(AppConstant.allData != null){
            for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_districts")){
                    for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                        NameInfo divisionData = new NameInfo();
                        divisionData.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                        divisionData.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getDistrict_name_bng());
                        listDistrict.add(divisionData);
                        listDistrictPermanent.add(divisionData);

                    }
                }
            }

            CustomAdapter adapterDistrict = new CustomAdapter(context, listDistrict);
            spinnerDistrict.setAdapter(adapterDistrict);

            CustomAdapter adapterDistrictPermanent = new CustomAdapter(context, listDistrictPermanent);
            spinnerDistrictPermanent.setAdapter(adapterDistrictPermanent);


            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listUpozila.clear();
                    if(!spinnerDistrict.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        districtPresent = spinnerDistrict.getSelectedItem().toString();
                        districtIdPresent = listDistrict.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_thanas")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_district_id().
                                        equalsIgnoreCase(districtIdPresent)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getThana_name_bng());
                                    listUpozila.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUpozila);
                    spinnerUpozila.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerUpozila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    listUnion.clear();
                    if(!spinnerUpozila.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        upojilaPresent= spinnerDistrict.getSelectedItem().toString();
                        upojilaIdPresent = listUpozila.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_unions")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_upazila_id().
                                        equalsIgnoreCase(upojilaIdPresent)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getUnion_name_bng());
                                    listUnion.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUnion);
                    spinnerUnion.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerUnion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(!spinnerUnion.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        unionPresent= spinnerUnion.getSelectedItem().toString();
                        unionIdPresent = listUnion.get(position).getId();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerDistrictPermanent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listUpozilaPermanent.clear();
                    if(!spinnerDistrictPermanent.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        districtPermanent = spinnerDistrictPermanent.getSelectedItem().toString();
                        districtIdPermanent = listDistrictPermanent.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_thanas")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_district_id().
                                        equalsIgnoreCase(districtIdPermanent)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getThana_name_bng());
                                    listUpozilaPermanent.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUpozilaPermanent);
                    spinnerUpojilaPermanent.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinnerUpojilaPermanent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    listUnionPermanent.clear();
                    if(!spinnerUpojilaPermanent.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        upojilaPermanent= spinnerUpojilaPermanent.getSelectedItem().toString();
                        upojilaIdPermanent = listUpozilaPermanent.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_unions")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_upazila_id().
                                        equalsIgnoreCase(upojilaIdPermanent)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getUnion_name_bng());
                                    listUnionPermanent.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUnionPermanent);
                    spinnerUnionPermanent.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        spinnerUnionPermanent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!spinnerUpojilaPermanent.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    unionPermanent = spinnerUnionPermanent.getSelectedItem().toString();
                    unionIdPermanent = spinnerUnionPermanent.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if(!checkPermission()){
            requestPermission();
        }


        input_birtdate = (EditText) findViewById(R.id.input_birtdate);
        input_birtdate.setText(new SimpleDateFormat("dd-MM-yyy").format(new Date()));

        Calendar newCalendar = Calendar.getInstance();
        final DatePickerDialog startTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                input_birtdate.setText(new SimpleDateFormat("dd-MM-yyy").format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        input_birtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime.show();
            }
        });


        btnSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
//        password,confirmpass,nid,mosqname,mosqaddress,wardno,postoffice,village,pdobi,division,
//                city,district,upojila,union,photo
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=input_name.getText().toString();
                nameFather=input_name_father.getText().toString();
                mothername=input_name_mother.getText().toString();
                mosqname=input_name_mosq.getText().toString();
                mobile=input_mobile.getText().toString();
                birthdate=input_birtdate.getText().toString();
                postofficePresent=input_postoffice_present.getText().toString();
                villagePresent=input_village_present.getText().toString();
                postoffficePermanent=input_postoffficePermanent.getText().toString();
                villagePermanent=input_villagePermanent.getText().toString();



                if(name.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার নাম লিখুন");
                }else if(nameFather.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার পিতার নাম লিখুন");
                }else if(mothername.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার মায়ের নাম লিখুন");
                }else if(mosqname.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","মসজিদের নাম লিখুন");
                }else if(mobile.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার মোবাইল নম্বর লিখুন");
                }else if(birthdate.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার জন্ম তারিখ লিখুন");
                }else if(districtIdPresent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার বর্তমান জেলা নির্বাচন করুন");
                }else if(upojilaIdPresent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার বর্তমান উপজেলা নির্বাচন করুন");
                }else if(unionIdPresent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার বর্তমান ইউনিয়ন নির্বাচন করুন");
                }else if(postofficePresent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার বর্তমান পোস্ট অফিস লিখুন");
                }else if(villagePresent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার বর্তমান গ্রাম লিখুন");
                }else if(districtIdPermanent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার স্থায়ী জেলা নির্বাচন করুন");
                }else if(upojilaIdPermanent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার স্থায়ী উপজেলা নির্বাচন করুন");
                }else if(unionIdPermanent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার স্থায়ী ইউনিয়ন নির্বাচন করুন");
                }else if(postoffficePermanent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার স্থায়ী পোস্ট অফিস লিখুন");
                }else if(villagePermanent.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার স্থায়ী গ্রাম লিখুন");
                }else {

                }


            }
        });
    }



    public Intent getPickImageChooserIntent() {

        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
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

    public Bitmap getResizedBitmap(Bitmap bm,int maxWidth, int maxHeight) {

        Bitmap bmp = null;

        if (maxHeight > 0 && maxWidth > 0) {
            int width = bm.getWidth();
            int height = bm.getHeight();

            Log.e("bm.getWidth()",""+bm.getWidth());
            Log.e("bm.getHeight()",""+bm.getHeight());

            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            bmp = Bitmap.createScaledBitmap(bm, finalWidth, finalHeight, true);
            return bmp;
        } else {
            return bmp;
        }

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == IMAGE_RESULT) {

                String filePath = getImageFilePath(data);
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    selectedImage =  getResizedBitmap(selectedImage,1024,768);


                    if(imgId.equalsIgnoreCase("user")){
                        selectedImage =  getResizedBitmap(selectedImage,300,300);
                        userPicPath = filePath;
                        bmpuser = selectedImage;
                        imgPic.setImageBitmap(selectedImage);
                        Log.e("userPicPath",""+userPicPath);
                    }


                    if(imgId.equalsIgnoreCase("mulsonod")){
                        bmpmulsonod = selectedImage;
                        imgMulsonod.setImageBitmap(selectedImage);
                    }


                    if(imgId.equalsIgnoreCase("testimonial")){
                        bmptestimo = selectedImage;
                        imgTestimonial.setImageBitmap(selectedImage);
                    }


                   if(imgId.equalsIgnoreCase("nid")){
                       bmpNid = selectedImage;
                        imgnidFile.setImageBitmap(selectedImage);
                    }


                   if(imgId.equalsIgnoreCase("chairman")){
                       bmpChairman = selectedImage;
                       imgChaimanFile.setImageBitmap(selectedImage);
                    }


                   if(imgId.equalsIgnoreCase("ifa")){
                       bmpIfa = selectedImage;
                       imgIFAFile.setImageBitmap(selectedImage);
                    }


                   if(imgId.equalsIgnoreCase("leave")){
                       bmpLeave = selectedImage;
                       imgLeaveFile.setImageBitmap(selectedImage);
                    }


                   if(imgId.equalsIgnoreCase("permission")){
                       bmpAuth = selectedImage;
                       imgauthPermissionFile.setImageBitmap(selectedImage);
                    }


                }
            }

        }

    }

    private String getImageFromFilePath(Intent data) {
        boolean isCamera = data == null || data.getData() == null;

        if (isCamera) return getCaptureImageOutputUri().getPath();
        else return getPathFromURI(data.getData());

    }

    public String getImageFilePath(Intent data) {
        return getImageFromFilePath(data);
    }

    private String getPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    private class CustomAdapter  extends BaseAdapter implements SpinnerAdapter {

        List<NameInfo> company;
        Context context;


        public CustomAdapter(Context context, List<NameInfo> company) {
            this.company = company;
            this.context = context;
        }

        @Override
        public int getCount() {
            return company.size();
        }

        @Override
        public Object getItem(int position) {
            return company.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view =  View.inflate(context, R.layout.company_main, null);
            TextView textView = (TextView) view.findViewById(R.id.main);
            textView.setText(company.get(position).getName());
            return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View view;
            view =  View.inflate(context, R.layout.company_dropdown, null);
            final TextView textView = (TextView) view.findViewById(R.id.dropdown);
            textView.setText(company.get(position).getName());



            return view;
        }
    }


    private void signUp(String data) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<SignUpResponse> userCall = api.signup(data);
        userCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                pd.dismiss();

                SignUpResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, ""+responsData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("error")){

                        if(responsData.getData().getEmail()!=null){
                            for (int i = 0; i <responsData.getData().getEmail().size() ; i++) {
                                Toast.makeText(context, ""+responsData.getData().getEmail().get(i), Toast.LENGTH_SHORT).show();
                            }
                        }


//                        if(responsData.getData().getPassword()!=null){
//                            for (int i = 0; i <responsData.getData().getPassword().size() ; i++) {
//                                Toast.makeText(context, ""+responsData.getData().getPassword().get(i), Toast.LENGTH_SHORT).show();
//                            }
//                        }

                    }
                }

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {


                pd.dismiss();
            }
        });


    }


    private void filUpload(){
        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        File file = new File(userPicPath);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(),
                RequestBody.create(MediaType.parse("image/*"), file));

        Call<String> call = api.uploadAttachment(filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

}
