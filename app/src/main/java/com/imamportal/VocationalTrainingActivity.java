package com.imamportal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.imamportal.model.AllDataResponse;
import com.imamportal.model.NameInfo;
import com.imamportal.model.SignUpResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VocationalTrainingActivity extends AppCompatActivity {

    Context context;
    private Spinner spinnerGender,spinnerMarraige,spinnerNatinality,spinnerOccuPation,spinnerDivision,spinnerDistrict,spinnerUpzila,
            spinnerUnion,spinnerCity,spinnerQualification,spinnerInstituteType,spinnerDivisioInsti,spinnerDistrictInsti,spinnerInstituteUpjila,
            spinnerTrade,spinnerCenter,spinnerZilaSM,spinnerUpozilaSm;
    private EditText input_name_bn,input_name_eng,input_father_name_bn,input_father_name_eng,input_mother_name_bn,
            input_mother_name_eng, input_nid,input_word,input_email,input_mobile,input_postcode,input_institute,input_lastqualification,input_name_sm,
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
    private List<NameInfo> listQualification = new ArrayList<>();
    private List<NameInfo> listInstituteType = new ArrayList<>();
    private List<NameInfo> listTrainingTrade = new ArrayList<>();
    private List<NameInfo> listTrainingCenter = new ArrayList<>();

    private String name,nameFather,nameFatherBn,mothername,mosqName,mobile,birthdate,mosqname,mosqaddress,postoffice,village,podobi="",
            division="",divisionInstitute="",divisionId,divisionIdInstitute, city="",cityId="",district="",districtInstitute="",
            districtSm="",districtId="",districtIdInstitute="",districtIdSm="",upojila="",upojilaInstitute="",upojilaId="",
            upojilaIdInstitute="",union="",unionId="",photo="",educationQualification,educationQualificationId;

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
    private String postcode;
    private String instituteTypeId;
    private String instituteType;
    private String trade;
    private String trainingCenter;
    private String upojilaIdSm;
    String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocational_training);
        context = this;
//        if(AppConstant.allData!=null){
//            initUi();
//        }else {
//            getAlldata();
//        }

        getAlldata();

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
        Call<AllDataResponse> userCall = api.get_all_data();
        userCall.enqueue(new Callback<AllDataResponse>() {
            @Override
            public void onResponse(Call<AllDataResponse> call, Response<AllDataResponse> response) {
                pd.dismiss();

                AllDataResponse  allData = response.body();

                if(allData!=null){

                    AppConstant.allData = allData;
                    Log.e("allData",""+allData.getResult().size());
                    initUi();
                }

            }

            @Override
            public void onFailure(Call<AllDataResponse> call, Throwable t) {


                pd.dismiss();
            }
        });


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
        input_word = (EditText) findViewById(R.id.input_word);
        input_email = (EditText) findViewById(R.id.input_email);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_postcode = (EditText) findViewById(R.id.input_postcode);
        input_institute = (EditText) findViewById(R.id.input_institute);
        input_lastqualification = (EditText) findViewById(R.id.input_lastqualification);
        input_name_sm = (EditText) findViewById(R.id.input_name_sm);
        input_mobile_sm = (EditText) findViewById(R.id.input_mobile_sm);
        tvBirthdate = (TextView) findViewById(R.id.tvBirthdate);
        appcomBtn = (AppCompatButton) findViewById(R.id.appcomBtn);


        appcomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "{\n" +
                        "\t\"name_bn\": \"Sadi \",\n" +
                        "\t\"name_en\": \"Sksk\",\n" +
                        "\t\"father_name_bn\": \"Hssj\",\n" +
                        "\t\"father_name_en\": \"Jzjs\",\n" +
                        "\t\"mother_name_bn\": \"Jssk\",\n" +
                        "\t\"mother_name_en\": \"Skskek\",\n" +
                        "\t\"gender\": \"পুরুষ\",\n" +
                        "\t\"dob\": \"11-03-2019\",\n" +
                        "\t\"maritial_status\": \" বিবাহিত \",\n" +
                        "\t\"nationality\": \"  বাংলাদেশী  \",\n" +
                        "\t\"profession\": \"শিক্ষক\",\n" +
                        "\t\"nid\": \"124556\",\n" +
                        "\t\"email\": \"robin@gmail.com\",\n" +
                        "\t\"mobile_number\": \"13797997\",\n" +
                        "\t\"division_id\": \"3\",\n" +
                        "\t\"district_id\": \"20\",\n" +
                        "\t\"upazila_id\": \"213\",\n" +
                        "\t\"union_id\": \"3972\",\n" +
                        "\t\"word_no\": \"23678\",\n" +
                        "\t\"city_corporation_id\": \"2\",\n" +
                        "\t\"educational_qualification\": \"কুমিল্লা সিটি কর্পোরেশন\",\n" +
                        "\t\"organization_name\": \"Hzjsk\",\n" +
                        "\t\"organization_type\": \"test1\",\n" +
                        "\t\"edu_division_id\": \"6\",\n" +
                        "\t\"edu_district_id\": \"57\",\n" +
                        "\t\"edu_upazila_id\": \"413\",\n" +
                        "\t\"educational_qualification_details\": \"Kamil\",\n" +
                        "\t\"training_trade\": \"test1\",\n" +
                        "\t\"training_center\": \"test1\",\n" +
                        "\t\"coordinator_name\": \"Hsn\",\n" +
                        "\t\"coordinator_mobile\": \"16767\",\n" +
                        "\t\"adjustment_district_id\": \"9\",\n" +
                        "\t\"adjustment_upazila_id\": \"63\"\n" +
                        "}";
                if(!TextUtils.isEmpty(filePath)){
                    validation();
                }else {
                    AlertMessage.showMessage(context,"Alert","Photo not captured");
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
        final List<NameInfo> listGender = new ArrayList<>();
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
                if(position>0){
                    gender =  listGender.get(position).getName();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Marital status spinner
        final List<NameInfo> listMaritual = new ArrayList<>();
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
        spinnerMarraige.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    maritalStatus = listMaritual.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //nationality spinner
        final List<NameInfo> listNationality = new ArrayList<>();
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
        spinnerNatinality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    nationalality = listNationality.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //occupation spinner
        final List<NameInfo> listoccupation = new ArrayList<>();
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
        spinnerOccuPation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    occupation = listoccupation.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

            spinnerUnion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position>0){
                        unionId = listUnion.get(position).getId();
                    }
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

            spinnerUpozilaSm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position>0){
                        upojilaIdSm = listUpozilaSm.get(position).getId();
                    }

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
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    cityId = listCity.get(position).getId();
                    city = listCity.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        listQualification.clear();
        listQualification.add(0,infosl);

        String[] qualifi = {"SSC","HSC","ALIM","FAZIL"};
        for (int i = 0; i <qualifi.length ; i++) {
            NameInfo inf = new NameInfo();
            inf.setName(qualifi[i]);
            listQualification.add(inf);
        }

        CustomAdapter adapterQualification = new CustomAdapter(context, listQualification);
        spinnerQualification.setAdapter(adapterQualification);

        spinnerQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    educationQualificationId = listCity.get(position).getId();
                    educationQualification = listCity.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listInstituteType.clear();
        listInstituteType.add(0,infosl);

        String[] instype = {"test","test1","test2","test3"};
        for (int i = 0; i <instype.length ; i++) {
            NameInfo inf = new NameInfo();
            inf.setName(instype[i]);
            listInstituteType.add(inf);
        }
        CustomAdapter adapterInstituteType = new CustomAdapter(context, listInstituteType);
        spinnerInstituteType.setAdapter(adapterInstituteType);

        spinnerInstituteType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    instituteTypeId = listInstituteType.get(position).getId();
                    instituteType = listInstituteType.get(position).getName();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        listTrainingTrade.clear();
        listTrainingTrade.add(0,infosl);
        for (int i = 0; i <instype.length ; i++) {
            NameInfo inf = new NameInfo();
            inf.setName(instype[i]);
            listTrainingTrade.add(inf);
        }
        CustomAdapter adapterTrainingTrade = new CustomAdapter(context, listTrainingTrade);
        spinnerTrade.setAdapter(adapterTrainingTrade);

        spinnerTrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    trade = listTrainingTrade.get(position).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        listTrainingCenter.clear();
        listTrainingCenter.add(0,infosl);
        for (int i = 0; i <instype.length ; i++) {
            NameInfo inf = new NameInfo();
            inf.setName(instype[i]);
            listTrainingCenter.add(inf);
        }
        CustomAdapter adapterTrainingCenter = new CustomAdapter(context, listTrainingCenter);
        spinnerCenter.setAdapter(adapterTrainingCenter);
        spinnerCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    trainingCenter = listTrainingCenter.get(position).getName();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void validation() {

        String name_bn = input_name_bn.getText().toString();
        String name_eng = input_name_eng.getText().toString();
        String father_name_bn = input_father_name_bn.getText().toString();
        String father_name_eng = input_father_name_eng.getText().toString();
        String mother_name_bn = input_mother_name_bn.getText().toString();
        String mother_name_eng = input_mother_name_eng.getText().toString();
        String nid = input_nid.getText().toString();
        String email = input_email.getText().toString();
        String mobile = input_mobile.getText().toString();
        String postcode = input_postcode.getText().toString();
        String institute = input_institute.getText().toString();
        String lastEduQualification = input_lastqualification.getText().toString();
        String name_sm = input_name_sm.getText().toString();
        String mobile_sm = input_mobile_sm.getText().toString();
        String birthdate = tvBirthdate.getText().toString();
        String wardno = input_word.getText().toString();

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
        }else if(TextUtils.isEmpty(gender)){
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
        }else if(TextUtils.isEmpty(divisionId)){
            AlertMessage.showMessage(context,"Alert!","আপনার বিভাগ নির্বাচন করুন");
        }else if(TextUtils.isEmpty(districtId)){
            AlertMessage.showMessage(context,"Alert!","আপনার জেলা নির্বাচন করুন");
        }else if(TextUtils.isEmpty(upojilaId)){
            AlertMessage.showMessage(context,"Alert!","আপনার উপজেলা নির্বাচন করুন");
        }else if(TextUtils.isEmpty(unionId)){
            AlertMessage.showMessage(context,"Alert!","আপনার ইউনিয়ন নির্বাচন করুন");
        }else if(TextUtils.isEmpty(postcode)){
            AlertMessage.showMessage(context,"Alert!","আপনার পোস্টকোড লিখুন");
        }else if(TextUtils.isEmpty(cityId)){
            AlertMessage.showMessage(context,"Alert!","আপনার সিটি নির্বাচন করুন");
        }else if(TextUtils.isEmpty(wardno)){
            AlertMessage.showMessage(context,"Alert!","আপনার ওয়ার্ড লিখুন");
        }else if(TextUtils.isEmpty(educationQualification)){
            AlertMessage.showMessage(context,"Alert!","আপনার শিক্ষাগত যোগ্যতা নির্বাচন করুন");
        }else if(TextUtils.isEmpty(institute)){
            AlertMessage.showMessage(context,"Alert!","আপনার শিক্ষা প্রতিষ্ঠানের নাম লিখুন");
        }else if(TextUtils.isEmpty(instituteType)){
            AlertMessage.showMessage(context,"Alert!","আপনার শিক্ষা প্রতিষ্ঠানের ধরণ নির্বাচন করুন");
        }else if(TextUtils.isEmpty(divisionIdInstitute)){
            AlertMessage.showMessage(context,"Alert!","আপনার শিক্ষা প্রতিষ্ঠানের বিভাগ নির্বাচন করুন");
        }else if(TextUtils.isEmpty(districtIdInstitute)){
            AlertMessage.showMessage(context,"Alert!","আপনার শিক্ষা প্রতিষ্ঠানের জেলা নির্বাচন করুন");
        }else if(TextUtils.isEmpty(upojilaIdInstitute)){
            AlertMessage.showMessage(context,"Alert!","আপনার শিক্ষা প্রতিষ্ঠানের উপজেলা নির্বাচন করুন");
        }else if(TextUtils.isEmpty(lastEduQualification)){
            AlertMessage.showMessage(context,"Alert!","আপনার সর্বশেষ শিক্ষাগত যোগ্যতা লিখুন");
            input_lastqualification.requestFocus();
        }else if(TextUtils.isEmpty(trade)){
            AlertMessage.showMessage(context,"Alert!","প্রশিক্ষণ ট্রেড নির্বাচন করুন");
        }else if(TextUtils.isEmpty(trade)){
            AlertMessage.showMessage(context,"Alert!","প্রশিক্ষণ সেন্টার নির্বাচন করুন");
        }else if(TextUtils.isEmpty(name_sm)){
            AlertMessage.showMessage(context,"Alert!","সমন্বয়কারীর নাম লিখুন");
            input_name_sm.requestFocus();
        }else if(TextUtils.isEmpty(mobile_sm)){
            AlertMessage.showMessage(context,"Alert!","সমন্বয়কারীর মোবাইল নম্বর লিখুন");
            input_mobile_sm.requestFocus();
        }else if(TextUtils.isEmpty(districtIdSm)){
            AlertMessage.showMessage(context,"Alert!","সমন্বয়কারীর জেলা নির্বাচন করুন");
        }else if(TextUtils.isEmpty(upojilaIdSm)){
            AlertMessage.showMessage(context,"Alert!","সমন্বয়কারীর উপজেলা নির্বাচন করুন");
        }else {

            JSONObject data = new JSONObject();
            try {
                data.put("name_bn",name_bn);
                data.put("name_en",name_eng);
                data.put("father_name_bn",father_name_bn);
                data.put("father_name_en",father_name_eng);
                data.put("mother_name_bn",mother_name_bn);
                data.put("mother_name_en",mother_name_eng);
                data.put("gender",gender);
                data.put("dob",birthdate);
                data.put("maritial_status",maritalStatus);
                data.put("nationality",nationalality);
                data.put("profession",occupation);
                data.put("nid",nid);
                data.put("email",email);
                data.put("mobile_number",mobile);
                data.put("division_id",divisionId);
                data.put("district_id",districtId);
                data.put("upazila_id",upojilaId);
                data.put("union_id",unionId);
                data.put("word_no",wardno);
                data.put("city_corporation_id",cityId);
                data.put("educational_qualification",educationQualification);
                data.put("organization_name",institute);
                data.put("organization_type",instituteType);
                data.put("edu_division_id",divisionIdInstitute);
                data.put("edu_district_id",districtIdInstitute);
                data.put("edu_upazila_id",upojilaIdInstitute);
                data.put("educational_qualification_details",lastEduQualification);
                data.put("training_trade",trade);
                data.put("training_center",trainingCenter);
                data.put("coordinator_name",name_sm);
                data.put("coordinator_mobile",mobile_sm);
                data.put("adjustment_district_id",districtIdSm);
                data.put("adjustment_upazila_id",upojilaIdSm);


                Log.e("data",data.toString());
                uploadData(data.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

                filePath = getImageFilePath(data);
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


    private void uploadData(String data) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        File file = new File(filePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filePath);

        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);


        Call<SignUpResponse> userCall = api.addtechnicaltraining( multipartBody,""+data);
        userCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                pd.dismiss();
                SignUpResponse responsData = response.body();

                if(responsData!=null){
                    if(responsData.getStatus().equalsIgnoreCase("success")){
                        Toast.makeText(context, ""+responsData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
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
}
