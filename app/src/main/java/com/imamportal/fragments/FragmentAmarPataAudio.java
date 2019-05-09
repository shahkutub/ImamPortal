package com.imamportal.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.imamportal.R;
import com.imamportal.model.AmarpataContentResponse;
import com.imamportal.model.Catagories;
import com.imamportal.model.ContentData;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragmentAmarPataAudio extends Fragment {

    Context context;
    private Spinner spinnerCatagoryAmamrPata,spinnerObosta;
    private String category_id;
    private AmarpataContentResponse amarpataContentResponse = new AmarpataContentResponse();
    private LinearLayout linBivag;
    private RecyclerView recycler_viewAmarpata;
    private ContactsAdapter mAdapter;
    private AppCompatButton appcomBtnSelect;
    private EditText input_title;

    private List<ContentData> mainInfoList = new ArrayList<>();

    private List<ContentData> contactList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.amarpata_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initUi();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initUi() {

        linBivag = (LinearLayout) getView().findViewById(R.id.linBivag);
        //linBivag.setVisibility(View.GONE);

        spinnerCatagoryAmamrPata = (Spinner)getView().findViewById(R.id.spinnerCatagoryAmamrPata);
        spinnerObosta = (Spinner)getView().findViewById(R.id.spinnerObosta);
        recycler_viewAmarpata = (RecyclerView) getView().findViewById(R.id.recycler_viewAmarpata);
        appcomBtnSelect = (AppCompatButton)getView().findViewById(R.id.appcomBtnSelect);

        input_title = (EditText)getView().findViewById(R.id.input_title);
        appcomBtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(input_title.getText().toString())){
                    mAdapter.getFilter().filter(input_title.getText().toString());
                }

            }
        });


        mAdapter = new ContactsAdapter(context, contactList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recycler_viewAmarpata.setLayoutManager(mLayoutManager);
        recycler_viewAmarpata.setItemAnimator(new DefaultItemAnimator());
        recycler_viewAmarpata.setAdapter(mAdapter);


        List<Catagories> listObosta = new ArrayList<>();


        Catagories obostha = new Catagories();
        obostha.setName_bn("নির্বাচন করুন");

        Catagories obosthaProkash = new Catagories();
        obosthaProkash.setName_bn("প্রকাশিত");

        Catagories obosthaOProkash = new Catagories();
        obosthaOProkash.setName_bn("অপ্রকাশিত");

        listObosta.add(0,obostha);
        listObosta.add(1,obosthaProkash);
        listObosta.add(2,obosthaOProkash);


        CustomAdapter adapterGender = new CustomAdapter(context, listObosta);
        spinnerObosta.setAdapter(adapterGender);

        spinnerObosta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    mAdapter.getFilter().filter("0");
                }else if(position==2){
                    mAdapter.getFilter().filter("1");
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerCatagoryAmamrPata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0){
                    category_id = amarpataContentResponse.getMultimedia_categories().get(position).getId();
                    mAdapter.getFilter().filter(category_id);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getContents();
    }

    public class CustomAdapter  extends BaseAdapter implements SpinnerAdapter {

        List<Catagories> company;
        Context context;


        public CustomAdapter(Context context, List<Catagories> company) {
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
            textView.setText(company.get(position).getName_bn());
            return textView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            View view;
            view =  View.inflate(context, R.layout.company_dropdown, null);
            final TextView textView = (TextView) view.findViewById(R.id.dropdown);
            textView.setText(company.get(position).getName_bn());

            return view;
        }
    }


    private void getContents() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + PersistData.getStringData(context,AppConstant.loginToken))
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<AmarpataContentResponse> userCall = api.mypage_Audio();
        userCall.enqueue(new Callback<AmarpataContentResponse>() {
            @Override
            public void onResponse(Call<AmarpataContentResponse> call, Response<AmarpataContentResponse> response) {
                pd.dismiss();

                amarpataContentResponse = response.body();

                if(amarpataContentResponse!=null){

                    Catagories catagories = new Catagories();
                    catagories.setName_bn("নির্বাচন করুন");
                    amarpataContentResponse.getMultimedia_categories().add(0,catagories);

                    CustomAdapter adapterGender = new CustomAdapter(context, amarpataContentResponse.getMultimedia_categories());
                    spinnerCatagoryAmamrPata.setAdapter(adapterGender);


                    //contactList = amarpataContentResponse.getAudios().getData();


                    mainInfoList = amarpataContentResponse.getAudios().getData();
                    contactList.clear();
                    contactList.addAll(mainInfoList);

                    // refreshing recycler view
                    mAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<AmarpataContentResponse> call, Throwable t) {
                pd.dismiss();
            }
        });


    }


    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder>
            implements Filterable {
        private Context context;
        private List<ContentData> contactList;
        private List<ContentData> contactListFiltered;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle,tvDescription,tvDate;
            public MyViewHolder(View view) {
                super(view);
                tvTitle=(TextView) view.findViewById(R.id.tvTitle);
                tvDescription=(TextView) view.findViewById(R.id.tvDescription);
                tvDate=(TextView) view.findViewById(R.id.tvDate);

            }
        }


        public ContactsAdapter(Context context, List<ContentData> contactList) {
            this.context = context;
            this.contactList = contactList;
            this.contactListFiltered = contactList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_amarpata, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final ContentData data = contactListFiltered.get(position);
            holder.tvDate.setText(data.getCreated_at());
            if(data.getDescription()!=null){
                holder.tvDescription.setText(data.getDescription());
            }
            holder.tvTitle.setText(data.getTitle());

        }

        @Override
        public int getItemCount() {

            return contactListFiltered.size();
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        contactListFiltered = contactList;
                    } else {
                        List<ContentData> filteredList = new ArrayList<>();
                        for (ContentData row : contactList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if(charSequence!=null){
                                if(row.getTitle()!=null){
                                    if (row.getTitle().contains(charSequence)) {
                                        filteredList.add(row);
                                    }
                                }

                                if(row.getCategory_id()!=null){
                                    if (row.getCategory_id().contains(charSequence)) {
                                        filteredList.add(row);
                                    }
                                }

                                if(row.getStatus()!=null){
                                    if (row.getStatus().contains(charSequence)) {
                                        filteredList.add(row);
                                    }
                                }
                            }

                        }

                        contactListFiltered = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = contactListFiltered;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    contactListFiltered = (ArrayList<ContentData>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

    }
}
