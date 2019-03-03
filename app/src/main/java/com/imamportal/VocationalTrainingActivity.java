package com.imamportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.imamportal.model.NameInfo;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.AppConstant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VocationalTrainingActivity extends AppCompatActivity {

    Context context;
    private Spinner spinnerGender,spinnerMarraige,spinnerNatinality,spinnerOccuPation,spinnerDivision,spinnerDistrict,spinnerUpzila,
            spinnerUnion,spinnerCity,spinnerQualification,spinnerInstituteType,spinnerDivisioInsti,spinnerDistrictInsti,spinnerInstituteUpjila,
            spinnerTrade,spinnerCenter,spinnerZilaSM,spinnerUpozilaSm;
    private EditText input_name_bn,input_name_eng,input_father_name_bn,input_father_name_eng,input_mother_name_bn,
            input_mother_name_eng, input_nid,input_email,input_mobile,input_institute,input_lastqualification,input_name_sm,
            input_mobile_sm;
    private TextView tvBirthdate;

    private List<NameInfo> listDivision = new ArrayList<>();
    private List<NameInfo> listDivisionInstitute = new ArrayList<>();
    private List<NameInfo> listDistrict = new ArrayList<>();
    private List<NameInfo> listDistrictSm = new ArrayList<>();
    private List<NameInfo> listDistrictInstitute = new ArrayList<>();
    private List<NameInfo> listUpozila = new ArrayList<>();
    private List<NameInfo> listUpozilaInstitute = new ArrayList<>();
    private List<NameInfo> listUpozilaSm = new ArrayList<>();
    private List<NameInfo> listUnion = new ArrayList<>();
    private List<NameInfo> listUnionInstitute = new ArrayList<>();
    private List<NameInfo> listCity = new ArrayList<>();

    private String name,nameFather,nameFatherBn,mothername,mosqName,mobile,birthdate,mosqname,mosqaddress,wardno,postoffice,village,podobi="",
            division="",divisionInstitute="",divisionId,divisionIdInstitute, city="",cityId="",district="",districtInstitute="",
            districtSm="",districtId="",districtIdInstitute="",districtIdSm="",upojila="",upojilaInstitute="",upojilaId="",upojilaIdInstitute="",union="",unionId="",photo="";

    private AppCompatButton appcomBtn;
    private ImageView imgcam,imgPic;
    Bitmap bmpuser;
    private String imgId;
    private final static int IMAGE_RESULT = 200;
    String userPicPath;
    private String gender;
    private String maritalStatus;
    private String nationalality;
    private String occupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocational_training);
        context = this;
        initUi();

    }

    private void initUi() {
        imgcam = (ImageView)findViewById(R.id.imgcam);
        imgPic = (ImageView)findViewById(R.id.imgPic);

        imgcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgId = "user";
                startActivityForResult(getPickImageChooserIntent(), IMAGE_RESULT);
            }
        });

        input_name_bn = (EditText) findViewById(R.id.input_name_bn);
        input_name_eng = (EditText) findViewById(R.id.input_name_eng);
        input_father_name_bn = (EditText) findViewById(R.id.input_father_name_bn);
        input_father_name_eng = (EditText) findViewById(R.id.input_father_name_eng);
        input_mother_name_bn = (EditText) findViewById(R.id.input_mother_name_bn);
        input_mother_name_eng = (EditText) findViewById(R.id.input_mother_name_eng);
        input_nid = (EditText) findViewById(R.id.input_nid);
        input_email = (EditText) findViewById(R.id.input_email);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_institute = (EditText) findViewById(R.id.input_institute);
        input_lastqualification = (EditText) findViewById(R.id.input_lastqualification);
        input_name_sm = (EditText) findViewById(R.id.input_name_sm);
        input_mobile_sm = (EditText) findViewById(R.id.input_mobile_sm);
        tvBirthdate = (TextView) findViewById(R.id.tvBirthdate);
        appcomBtn = (AppCompatButton) findViewById(R.id.appcomBtn);

        appcomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_bn = input_name_bn.getText().toString();
                String name_eng = input_name_eng.getText().toString();
                String father_name_bn = input_father_name_bn.getText().toString();
                String father_name_eng = input_father_name_eng.getText().toString();
                String mother_name_bn = input_mother_name_bn.getText().toString();
                String mother_name_eng = input_mother_name_eng.getText().toString();
                String nid = input_nid.getText().toString();
                String email = input_email.getText().toString();
                String mobile = input_mobile.getText().toString();
                String institute = input_institute.getText().toString();
                String lastqualification = input_lastqualification.getText().toString();
                String name_sm = input_name_sm.getText().toString();
                String mobile_sm = input_mobile_sm.getText().toString();
                String birthdate = tvBirthdate.getText().toString();

                if(TextUtils.isEmpty(name_bn)){
                    AlertMessage.showMessage(context,"Alert!","আপনার নাম লিখুন বাংলায়");
                    input_name_bn.requestFocus();
                }else if(TextUtils.isEmpty(name_eng)){
                    AlertMessage.showMessage(context,"Alert!","আপনার নাম লিখুন ইংরেজিতে");
                    input_name_eng.requestFocus();
                }else if(TextUtils.isEmpty(father_name_bn)){
                    AlertMessage.showMessage(context,"Alert!","আপনার পিতার নাম লিখুন বাংলায়");
                    input_father_name_bn.requestFocus();
                }else if(TextUtils.isEmpty(father_name_eng)){
                    AlertMessage.showMessage(context,"Alert!","আপনার পিতার নাম লিখুন ইংরেজিতে");
                    input_father_name_eng.requestFocus();
                }else if(TextUtils.isEmpty(mother_name_bn)){
                    AlertMessage.showMessage(context,"Alert!","আপনার মাতার নাম লিখুন বাংলায়");
                    input_mother_name_bn.requestFocus();
                }else if(TextUtils.isEmpty(mother_name_eng)){
                    AlertMessage.showMessage(context,"Alert!","আপনার মাতার নাম লিখুন ইংরেজিতে");
                    input_mother_name_eng.requestFocus();
                }else if(TextUtils.isEmpty(nid)){
                    AlertMessage.showMessage(context,"Alert!","আপনার লিঙ্গ নির্বাচন করুন");
                }else if(TextUtils.isEmpty(birthdate)){
                    AlertMessage.showMessage(context,"Alert!","আপনার জন্ম তারিখ লিখুন");
                }else if(TextUtils.isEmpty(maritalStatus)){
                    AlertMessage.showMessage(context,"Alert!","আপনার বৈবাহিক অবস্থা নির্বাচন করুন");
                }else if(TextUtils.isEmpty(nationalality)){
                    AlertMessage.showMessage(context,"Alert!","আপনার জাতীয়তা নির্বাচন করুন");
                }else if(TextUtils.isEmpty(occupation)){
                    AlertMessage.showMessage(context,"Alert!","আপনার পেশা নির্বাচন করুন");
                }else if(TextUtils.isEmpty(nid)){
                    AlertMessage.showMessage(context,"Alert!","আপনার জাতীয় পরিচয় পত্র নম্বর লিখুন");
                    input_nid.requestFocus();
                }else if(TextUtils.isEmpty(email)){
                    AlertMessage.showMessage(context,"Alert!","ইমেইল এড্রেস লিখুন");
                    input_email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    AlertMessage.showMessage(context,"Alert!","ইমেইল এড্রেস সঠিক নয়");
                    input_email.requestFocus();
                }else if(TextUtils.isEmpty(mobile)){
                    AlertMessage.showMessage(context,"Alert!","আপনার মোবাইল নম্বর লিখুন");
                    input_mobile.requestFocus();
                }

            }
        });


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

        spinnerGender = (Spinner)findViewById(R.id.spinnerGender);
        spinnerMarraige = (Spinner)findViewById(R.id.spinnerMarraige);
        spinnerNatinality = (Spinner)findViewById(R.id.spinnerNatinality);
        spinnerOccuPation = (Spinner)findViewById(R.id.spinnerOccuPation);
        spinnerDivision = (Spinner)findViewById(R.id.spinnerDivision);
        spinnerDistrict = (Spinner)findViewById(R.id.spinnerDistrict);
        spinnerUpzila = (Spinner)findViewById(R.id.spinnerUpzila);
        spinnerUnion = (Spinner)findViewById(R.id.spinnerUnion);
        spinnerCity = (Spinner)findViewById(R.id.spinnerCity);
        spinnerQualification = (Spinner)findViewById(R.id.spinnerQualification);
        spinnerInstituteType = (Spinner)findViewById(R.id.spinnerInstituteType);
        spinnerDivisioInsti = (Spinner)findViewById(R.id.spinnerDivisioInsti);
        spinnerDistrictInsti = (Spinner)findViewById(R.id.spinnerDistrictInsti);
        spinnerInstituteUpjila = (Spinner)findViewById(R.id.spinnerInstituteUpjila);
        spinnerTrade = (Spinner)findViewById(R.id.spinnerTrade);
        spinnerCenter = (Spinner)findViewById(R.id.spinnerCenter);
        spinnerZilaSM = (Spinner)findViewById(R.id.spinnerZilaSM);
        spinnerUpozilaSm = (Spinner)findViewById(R.id.spinnerUpozilaSm);

        //gender spinner
        List<NameInfo> listGender = new ArrayList<>();
        final NameInfo  infosl = new NameInfo();
        infosl.setId("select");
        infosl.setName("নির্বাচন করুন");
        listGender.add(infosl);

        NameInfo  info = new NameInfo();
        info.setId("0");
        info.setName("পুরুষ");
        listGender.add(info);

        NameInfo  info1 = new NameInfo();
        info1.setId("1");
        info1.setName("মহিলা");
        listGender.add(info1);
      CustomAdapter adapterGender = new CustomAdapter(context, listGender);
      spinnerGender.setAdapter(adapterGender);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spinnerGender.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                    gender =  spinnerGender.getSelectedItem().toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Marital status spinner
        List<NameInfo> listMaritual = new ArrayList<>();
        NameInfo  marinfosl = new NameInfo();
        marinfosl.setId("select");
        marinfosl.setName("নির্বাচন করুন");
        listMaritual.add(marinfosl);

        NameInfo  mrinfo = new NameInfo();
        mrinfo.setId("0");
        mrinfo.setName(" বিবাহিত ");
        listMaritual.add(mrinfo);

        NameInfo  mrinfo1 = new NameInfo();
        mrinfo1.setId("1");
        mrinfo1.setName(" অবিবাহিত ");
        listMaritual.add(mrinfo1);
        CustomAdapter adapterMr = new CustomAdapter(context, listMaritual);
        spinnerMarraige.setAdapter(adapterMr);

        //nationality spinner
        List<NameInfo> listNationality = new ArrayList<>();
        NameInfo  nationalityinfosl = new NameInfo();
        nationalityinfosl.setId("select");
        nationalityinfosl.setName("নির্বাচন করুন");
        listNationality.add(nationalityinfosl);

        NameInfo  nationalityinfo = new NameInfo();
        nationalityinfo.setId("0");
        nationalityinfo.setName("  বাংলাদেশী  ");
        listNationality.add(nationalityinfo);
        CustomAdapter adapterNational = new CustomAdapter(context, listNationality);
        spinnerNatinality.setAdapter(adapterNational);


        //occupation spinner
        List<NameInfo> listoccupation = new ArrayList<>();
        NameInfo  occupationinfosl = new NameInfo();
        occupationinfosl.setId("select");
        occupationinfosl.setName("নির্বাচন করুন");
        listoccupation.add(occupationinfosl);

        NameInfo  occupation0 = new NameInfo();
        occupation0.setId("0");
        occupation0.setName(" ছাত্র ");
        listoccupation.add(occupation0);

        NameInfo  occupation1 = new NameInfo();
        occupation1.setId("1");
        occupation1.setName("শিক্ষক");
        listoccupation.add(occupation1);

        NameInfo  occupation2 = new NameInfo();
        occupation2.setId("2");
        occupation2.setName("চাকুরিজীবী");
        listoccupation.add(occupation2);

        NameInfo  occupation3 = new NameInfo();
        occupation3.setId("3");
        occupation3.setName("ব্যবসায়ী");
        listoccupation.add(occupation3);

        NameInfo  occupation4 = new NameInfo();
        occupation4.setId("4");
        occupation4.setName("অন্যান্য");
        listoccupation.add(occupation4);

        CustomAdapter adapteroccupation = new CustomAdapter(context, listoccupation);
        spinnerOccuPation.setAdapter(adapteroccupation);



        //present location spinner data set
        if(AppConstant.allData != null){
            listDivision.clear();
            listDivisionInstitute.clear();
            listDivision.add(0,infosl);
            listDivisionInstitute.add(0,infosl);
            for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_divisions")){
                    for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                        NameInfo divisionData = new NameInfo();
                        divisionData.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                        divisionData.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getDivision_name_bng());
                        listDivision.add(divisionData);
                        listDivisionInstitute.add(divisionData);

                    }
                }
            }

            CustomAdapter adapterDivision = new CustomAdapter(context, listDivision);
            spinnerDivision.setAdapter(adapterDivision);
            spinnerDivisioInsti.setAdapter(adapterDivision);

            spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listDistrict.clear();
                    listDistrict.add(0,infosl);
                    if(!spinnerDivision.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        division = spinnerDivision.getSelectedItem().toString();
                        divisionId = listDivision.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_districts")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_division_id().
                                        equalsIgnoreCase(divisionId)){
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
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listUpozila.clear();
                    listUpozila.add(0,infosl);
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
                    spinnerUpzila.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerUpzila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    listUnion.clear();
                    listUnion.add(0,infosl);
                    if(!spinnerUpzila.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
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

            // institute spinner data
            spinnerDivisioInsti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listDistrictInstitute.clear();
                    listDistrictInstitute.add(0,infosl);
                    if(!spinnerDivisioInsti.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        divisionInstitute = spinnerDivisioInsti.getSelectedItem().toString();
                        divisionIdInstitute = listDivisionInstitute.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_districts")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_division_id().
                                        equalsIgnoreCase(divisionIdInstitute)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getDistrict_name_bng());
                                    listDistrictInstitute.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listDistrictInstitute);
                    spinnerDistrictInsti.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerDistrictInsti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listUpozilaInstitute.clear();
                    listUpozilaInstitute.add(0,infosl);
                    if(!spinnerDistrictInsti.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        districtInstitute = spinnerDistrictInsti.getSelectedItem().toString();
                        districtIdInstitute = listDistrictInstitute.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_thanas")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_district_id().
                                        equalsIgnoreCase(districtIdInstitute)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getThana_name_bng());
                                    listUpozilaInstitute.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUpozilaInstitute);
                    spinnerInstituteUpjila.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerInstituteUpjila.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    listUnionInstitute.clear();
                    listUnionInstitute.add(0,infosl);
                    if(!spinnerInstituteUpjila.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        upojilaInstitute= spinnerDistrict.getSelectedItem().toString();
                        upojilaIdInstitute = listUpozilaInstitute.get(position).getId();
                    }

//                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
//                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_unions")){
//                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
//                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_upazila_id().
//                                        equalsIgnoreCase(upojilaIdInstitute)){
//                                    NameInfo data = new NameInfo();
//                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
//                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getUnion_name_bng());
//                                    listUnionInstitute.add(data);
//                                }
//
//                            }
//                        }
//                    }
//
//                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUnionInstitute);
//                    spinnerUnion.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            // Coordinator spinner data

            listDistrictSm.clear();
            listDistrictSm.add(0,infosl);

            for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_districts")){
                    for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                            NameInfo data = new NameInfo();
                            data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                            data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getDistrict_name_bng());
                            listDistrictSm.add(data);
                    }
                }
            }

            CustomAdapter customAdapterSm = new CustomAdapter(context,listDistrictSm);
            spinnerZilaSM.setAdapter(customAdapterSm);

            spinnerZilaSM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listUpozilaSm.clear();
                    listUpozilaSm.add(0,infosl);
                    if(!spinnerZilaSM.getSelectedItem().toString().equalsIgnoreCase("নির্বাচন করুন")){
                        districtSm = spinnerZilaSM.getSelectedItem().toString();
                        districtIdSm = listDistrictSm.get(position).getId();
                    }

                    for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                        if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_thanas")){
                            for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                                if(AppConstant.allData.getResult().get(i).getValues().get(j).getGeo_district_id().
                                        equalsIgnoreCase(districtIdSm)){
                                    NameInfo data = new NameInfo();
                                    data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                                    data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getThana_name_bng());
                                    listUpozilaSm.add(data);
                                }

                            }
                        }
                    }

                    CustomAdapter adapterDistrict = new CustomAdapter(context, listUpozilaSm);
                    spinnerUpozilaSm.setAdapter(adapterDistrict);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //city spinner data set
            listCity.clear();
            listCity.add(0,infosl);
            for (int i = 0; i <AppConstant.allData.getResult().size() ; i++) {
                if(AppConstant.allData.getResult().get(i).getTable_name().equalsIgnoreCase("geo_city_corporations")){
                    for (int j = 0; j <AppConstant.allData.getResult().get(i).getValues().size() ; j++) {
                            NameInfo data = new NameInfo();
                            data.setId(AppConstant.allData.getResult().get(i).getValues().get(j).getId());
                            data.setName(AppConstant.allData.getResult().get(i).getValues().get(j).getCity_corporation_name_bng());
                            listCity.add(data);

                    }
                }
            }

           CustomAdapter adapterCity = new CustomAdapter(context, listCity);
            spinnerCity.setAdapter(adapterCity);

        }



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


    public Bitmap getResizedBitmap(Bitmap bm, int maxWidth, int maxHeight) {

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
                    //selectedImage =  getResizedBitmap(selectedImage,1024,768);


                    if(imgId.equalsIgnoreCase("user")){
                        selectedImage =  getResizedBitmap(selectedImage,300,300);
                        userPicPath = filePath;
                        bmpuser = selectedImage;
                        imgPic.setImageBitmap(selectedImage);
                        Log.e("userPicPath",""+userPicPath);
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

}