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
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imamportal.model.AllLocationResponse;
import com.imamportal.model.NameInfo;
import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.BitmapUtils;
import com.imamportal.utils.NetInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
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

public class RegistrationActivity extends AppCompatActivity {

    Context context;
    private ImageView imgcam;
    private CircleImageView imgPic;
    private File file;
    String filePath;
    String picture = "";
    Bitmap userBmp;
    private static File dir = null;
    String imageLocal = "";
    public final int imagecaptureid = 0;
    public final int galarytakid = 1;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private final static int IMAGE_RESULT = 200;
    private EditText input_name,input_email,input_UserName,input_UserMobile,input_password,input_password_confirm,input_nid,input_mosq,
    input_mosq_address,input_wordno,input_postoffice,input_village;
    Spinner spinnerPodobi,spinnerDivision,spinnerCity,spinnerDistrict,spinnerUpojila,spinnerUnion;
    private TextView tvBirthdate;
    private Button btnSubmit;
    private ScrollView scrolliew;

    List<NameInfo> listDivision = new ArrayList<>();
    List<NameInfo> listCity = new ArrayList<>();
    List<NameInfo> listDistrict = new ArrayList<>();
    List<NameInfo> listUpozila = new ArrayList<>();
    List<NameInfo> listUnion = new ArrayList<>();

    NameInfo select = new NameInfo();

    private String name,email,username,mobile,password,confirmpass,birthdate,nid,mosqname,mosqaddress,wardno,postoffice,village,podobi,
            division="",divisionId,
    city="",cityId="",district="",districtId="",upojila="",upojilaId="",union="",unionId="",photo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context= this;
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        getAlldata();

        select.setName("নির্বাচন করুন");
//        if(AppConstant.allData!=null){
//            innitUi();
//        }else {
//            getAlldata();
//        }
    }

    private void innitUi() {
        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        scrolliew = (ScrollView)findViewById(R.id.scrolliew);
        input_name = (EditText)findViewById(R.id.input_name);
        input_email = (EditText)findViewById(R.id.input_email);
        input_UserName = (EditText)findViewById(R.id.input_UserName);
        input_UserMobile = (EditText)findViewById(R.id.input_UserMobile);
        input_password = (EditText)findViewById(R.id.input_password);
        input_password_confirm = (EditText)findViewById(R.id.input_password_confirm);

        input_nid = (EditText)findViewById(R.id.input_nid);
        input_mosq = (EditText)findViewById(R.id.input_mosq);
        input_mosq_address = (EditText)findViewById(R.id.input_mosq_address);
        input_wordno = (EditText)findViewById(R.id.input_wordno);
        input_postoffice = (EditText)findViewById(R.id.input_postoffice);
        input_village = (EditText)findViewById(R.id.input_village);

        spinnerPodobi = (Spinner) findViewById(R.id.spinnerPodobi);
        final List<NameInfo> listPodobi = new ArrayList<NameInfo>();

        String podobis [] = {"নির্বাচন করুন","ইমাম","মুয়াজ্জিন","অন্যান্য"};
        for (int i = 0; i <podobis.length ; i++) {
            NameInfo info = new NameInfo();
            info.setName(podobis[i]);
            listPodobi.add(info);
        }


        CustomAdapter adapterPodobi = new CustomAdapter(this, listPodobi);
        spinnerPodobi.setAdapter(adapterPodobi);

        spinnerPodobi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    podobi = listPodobi.get(i).getName();
                }
        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinnerDivision = (Spinner) findViewById(R.id.spinnerDivision);
        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerUpojila = (Spinner) findViewById(R.id.spinnerUpojila);
        spinnerUnion = (Spinner) findViewById(R.id.spinnerUnion);

        listDivision.clear();
        listDivision.add(0,select);

        for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
            if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_divisions")){
                for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                    NameInfo divisionData = new NameInfo();
                    divisionData.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                    divisionData.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getDivision_name_bng());
                    listDivision.add(divisionData);
                }
            }
        }
        CustomAdapter adapter = new CustomAdapter(this, listDivision);
        spinnerDivision.setAdapter(adapter);


        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                listDistrict.clear();
                listDistrict.add(0,select);
                if(!spinnerDivision.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    division = spinnerDivision.getSelectedItem().toString();
                    divisionId = listDivision.get(pos).getId();
                }

                for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                    if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_districts")){
                        for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                            if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_division_id().equalsIgnoreCase(divisionId)){
                                NameInfo data = new NameInfo();
                                data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getDistrict_name_bng());
                                listDistrict.add(data);
                            }

                        }
                    }
                }

                CustomAdapter adapterDistrict = new CustomAdapter(context, listDistrict);
                spinnerDistrict.setAdapter(adapterDistrict);



                listCity.clear();
                listCity.add(0,select);
                for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                    if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_city_corporations")){
                        for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                            if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_division_id().equalsIgnoreCase(divisionId)){
                                NameInfo data = new NameInfo();
                                data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getCity_corporation_name_bng());
                                listCity.add(data);
                            }

                        }
                    }
                }

                CustomAdapter adapterCity = new CustomAdapter(context, listCity);
                spinnerCity.setAdapter(adapterCity);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = spinnerCity.getSelectedItem().toString();
                cityId = listCity.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listUpozila.clear();
                listUpozila.add(0,select);
                if(!spinnerDistrict.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    district = spinnerDistrict.getSelectedItem().toString();
                    districtId = listDistrict.get(position).getId();
                }

                for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                    if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_thanas")){
                        for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                            if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_district_id().
                                    equalsIgnoreCase(districtId)){
                                NameInfo data = new NameInfo();
                                data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getThana_name_bng());
                                listUpozila.add(data);
                            }

                        }
                    }
                }

                CustomAdapter adapterDistrict = new CustomAdapter(context, listUpozila);
                spinnerUpojila.setAdapter(adapterDistrict);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerUpojila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                listUnion.clear();
                listUnion.add(0,select);
                if(!spinnerUpojila.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    upojila= spinnerDistrict.getSelectedItem().toString();
                    upojilaId = listUpozila.get(position).getId();
                }

                for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                    if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_unions")){
                        for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                            if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_upazila_id().
                                    equalsIgnoreCase(upojilaId)){
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
                union = spinnerUnion.getSelectedItem().toString();
                unionId = listUnion.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






        imgcam = (ImageView)findViewById(R.id.imgcam);
        imgPic = (CircleImageView) findViewById(R.id.imgPic);

        imgcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
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
                mobile=input_UserMobile.getText().toString();
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
                    AlertMessage.showMessage(context,"Alert!","আপনার নাম লিখুন");
                    input_name.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_name.getBottom());
                        }
                    });
                }
                else if(email.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার ইমেইল লিখুন");
                    input_email.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_email.getBottom());
                        }
                    });
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    AlertMessage.showMessage(context,"Alert!","ইমেইল সঠিক নয়");
                    input_email.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_email.getBottom());
                        }
                    });
                }
                else if(username.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার ইউসার নাম লিখুন");
                    input_UserName.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_UserName.getBottom());
                        }
                    });
                }
                else if(mobile.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","আপনার মোবাইল নম্বর নাম লিখুন");
                    input_UserMobile.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_UserMobile.getBottom());
                        }
                    });
                }
                else if(password.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","পাসওয়ার্ড লিখুন");
                    input_password.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_password.getBottom());
                        }
                    });
                }
                else if(password.length()<6){
                    AlertMessage.showMessage(context,"Alert!","পাসওয়ার্ড 6 ডিজিট হতে হবে");
                    input_password.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_password.getBottom());
                        }
                    });
                }
                else if(!confirmpass.equalsIgnoreCase(password)){
                    AlertMessage.showMessage(context,"Alert!","পাসওয়ার্ড মিলেনি");
                    input_password_confirm.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_password_confirm.getBottom());
                        }
                    });
                }
                else if(TextUtils.isEmpty(podobi)){
                    AlertMessage.showMessage(context,"Alert!","পদবী নির্বাচন করুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, spinnerPodobi.getBottom());
                        }
                    });
                }else if(birthdate.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","জন্ম তারিখ লিখুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, tvBirthdate.getBottom());
                        }
                    });
                }else if(nid.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","জাতীয় পরিচয় পত্র নম্বর লিখুন");
                }else if(mosqname.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","মসজিদের নাম লিখুন");
                    input_mosq.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_mosq.getBottom());
                        }
                    });
                }else if(mosqaddress.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","মসজিদের ঠিকানা লিখুন");
                    input_mosq_address.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_mosq_address.getBottom());
                        }
                    });
                }else if(division.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","বিভাগ নির্বাচন করুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, spinnerDivision.getBottom());
                        }
                    });
                }else if(city.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","সিটি নির্বাচন করুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, spinnerCity.getBottom());
                        }
                    });
                }else if(wardno.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","ওয়ার্ড এর নাম লিখুন");
                    input_wordno.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_wordno.getBottom());
                        }
                    });
                }else if(district.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","জেলা নির্বাচন করুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, spinnerDistrict.getBottom());
                        }
                    });
                }else if(upojila.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","উপজেলা নির্বাচন করুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, spinnerUpojila.getBottom());
                        }
                    });
                }else if(union.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","ইউনিয়ন নির্বাচন করুন");
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, spinnerUnion.getBottom());
                        }
                    });
                }else if(postoffice.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","পোস্ট অফিস এর নাম লিখুন");
                    input_postoffice.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_postoffice.getBottom());
                        }
                    });
                }else if(village.isEmpty()){
                    AlertMessage.showMessage(context,"Alert!","গ্রামের নাম লিখুন");
                    input_village.requestFocus();
                    scrolliew.post(new Runnable() {
                        @Override
                        public void run() {
                            scrolliew.scrollTo(0, input_village.getBottom());
                        }
                    });
                }else {

                    //username email password designation name mobile_no nid dob masjid_name masjid_address division_id
                    // city_corporation_id word_no district_id upazila_id union_id village image
                    String img = "";
                    if(userBmp!=null){
                        img = getBase64String(userBmp);
                        Log.e("",""+img);
                    }


                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("username",username);
                        jsonObject.put("email",email);
                        jsonObject.put("password",password);
                        jsonObject.put("password_confirmation",confirmpass);
                        jsonObject.put("designation",podobi);
                        jsonObject.put("name",name);
                        jsonObject.put("mobile_no",mobile);
                        jsonObject.put("nid",nid);
                        jsonObject.put("dob",birthdate);
                        jsonObject.put("masjid_name",mosqname);
                        jsonObject.put("masjid_address",mosqaddress);
                        jsonObject.put("division_id",divisionId);
                        jsonObject.put("city_corporation_id",cityId);
                        jsonObject.put("word_no",wardno);
                        jsonObject.put("district_id",districtId);
                        jsonObject.put("upazila_id",upojilaId);
                        jsonObject.put("union_id",unionId);
                        jsonObject.put("village",village);
                        //jsonObject.put("image",img);
                        jsonObject.put("image","");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(TextUtils.isEmpty(filePath)){
                        AlertMessage.showMessage(context,"Alert!","প্রোফাইল ফটো নির্বাচন করুন");
                    }else {
                        registration(jsonObject.toString());
                    }

                    //signUp(jsonObject.toString());
                    Log.e("jsonObject",""+jsonObject.toString());

                }


            }
        });
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalFilesDir("");
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
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

    private String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
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

                filePath = getImageFilePath(data);
                Log.e("filePath",""+filePath);
                if (filePath != null) {
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    selectedImage =  getResizedBitmap(selectedImage,150,150);
                    userBmp = selectedImage;
                    imgPic.setImageBitmap(selectedImage);

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




    public class CustomAdapter  extends BaseAdapter implements SpinnerAdapter {

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

    private MultipartBody.Part multipartBody(String filpath) {
        MultipartBody.Part multipartBody = null;
        if(!TextUtils.isEmpty(filpath)){
            File file = new File(filpath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filpath);
            multipartBody = MultipartBody.Part.createFormData("image",file.getName(),requestFile);
        }else {
            Toast.makeText(context, "File not taken", Toast.LENGTH_SHORT).show();
        }
        return multipartBody;
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

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RequestBody dataStr =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, data);

        Api api = retrofit.create(Api.class);
        Call<SignUpResponse> userCall = api.signup(multipartBody(filePath),dataStr);
        userCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                pd.dismiss();

                SignUpResponse responsData = response.body();
//                Log.e("regResponse",""+responsData.toString());
                if(responsData!=null){

                    Log.e("regResponse",""+responsData.toString());
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, ""+responsData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                if(responsData!=null){
                    Log.e("regResponse",""+responsData.toString());
                    if(responsData.getStatus().equalsIgnoreCase("error")){

                        if(responsData.getData().getEmail()!=null){
                            for (int i = 0; i <responsData.getData().getEmail().size() ; i++) {
                                Toast.makeText(context, ""+responsData.getData().getEmail().get(i), Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(responsData.getData().getUsername()!=null){
                            for (int i = 0; i <responsData.getData().getUsername().size() ; i++) {
                                Toast.makeText(context, ""+responsData.getData().getUsername().get(i), Toast.LENGTH_SHORT).show();
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


    protected void registration(String data) {
        String url = Api.BASE_URL+"api/signup/store";

        if (!NetInfo.isOnline(context)) {
            AlertMessage.showMessage(context, "Alert",
                    "Check Internet");
            return;
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setCancelable(false);
        pd.setMessage("Data uploading...");
        pd.show();

        final AsyncHttpClient client = new AsyncHttpClient();

        // String credentials = Username + ":" + Password;
        // String base64EncodedCredentials =
        // Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
        // client.addHeader("Authorization", "Basic " +
        // base64EncodedCredentials);

        final RequestParams param = new RequestParams();

        try {

            //String path = PersistData.getStringData(con, AppConstant.path);
            param.put("data",data);
            param.put("image",new File(filePath));


        } catch (final Exception e1) {
            e1.printStackTrace();
        }

        client.post(url, param, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] response) {
                // called when response HTTP status is "200 OK"

                pd.dismiss();

//                Toast.makeText(context, "Prescription Uploaded",
//                        Toast.LENGTH_LONG).show();

                Log.e("resposne ", ">>" + new String(response));

                Gson g = new Gson();
                SignUpResponse responsData = g.fromJson(new String(response), SignUpResponse.class);

                if(responsData!=null){

                    Log.e("regResponse",""+responsData.toString());
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, ""+responsData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                if(responsData!=null){
                    Log.e("regResponse",""+responsData.toString());
                    if(responsData.getStatus().equalsIgnoreCase("error")){

                        if(responsData.getData().getEmail()!=null){
                            for (int i = 0; i <responsData.getData().getEmail().size() ; i++) {
                                Toast.makeText(context, ""+responsData.getData().getEmail().get(i), Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(responsData.getData().getUsername()!=null){
                            for (int i = 0; i <responsData.getData().getUsername().size() ; i++) {
                                Toast.makeText(context, ""+responsData.getData().getUsername().get(i), Toast.LENGTH_SHORT).show();
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
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

                // Log.e("errorResponse", new String(errorResponse));

                pd.dismiss();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });

    }

    private void getAlldata() {

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
        Call<AllLocationResponse> userCall = api.get_all_location_data();
        userCall.enqueue(new Callback<AllLocationResponse>() {
            @Override
            public void onResponse(Call<AllLocationResponse> call, Response<AllLocationResponse> response) {
                pd.dismiss();

                AllLocationResponse allData = response.body();

                if(allData!=null){

                    AppConstant.allData = allData;
                    Log.e("allData",""+allData.getResult().size());
                    innitUi();
                    if(allData.getResult().size()==0){
                        Toast.makeText(context, "Location not data found!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }

            @Override
            public void onFailure(Call<AllLocationResponse> call, Throwable t) {


                pd.dismiss();
            }
        });


    }

}
