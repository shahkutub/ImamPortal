package com.imamportal;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.imamportal.model.ChatUserModel;
import com.imamportal.model.ChatUserResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatCreateGroupActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private SearchView searchView;

    Context context;
    ChatCreatAdapter mAdapter;
    private LinearLayout linCreateGroup,linNewChat,linGroupName;
    private FrameLayout frmFloatingOk;
    EditText input_group_name;

    private ChatUserResponse responsData;
    SwipyRefreshLayout swiperefresh;
    String currentPage;
    List <ChatUserModel>  chatuserlist = new ArrayList<>();
    private List<Integer> select_members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_search);

        context=this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Create Group");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linCreateGroup = (LinearLayout) findViewById(R.id.linCreateGroup);
        linNewChat = (LinearLayout) findViewById(R.id.linNewChat);
        linGroupName = (LinearLayout) findViewById(R.id.linGroupName);
        frmFloatingOk = (FrameLayout) findViewById(R.id.frmFloatingOk);
        linCreateGroup.setVisibility(View.GONE);
        linNewChat.setVisibility(View.GONE);
        linGroupName.setVisibility(View.VISIBLE);
        //frmFloatingOk.setVisibility(View.VISIBLE);

        input_group_name = (EditText)findViewById(R.id.input_group_name);

        frmFloatingOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String groupname = input_group_name.getText().toString();

                if(TextUtils.isEmpty(groupname)){
                    AlertMessage.showMessage(context,"Alert","গ্রুপের নাম লিখুন");
                }else if(select_members.size()<2){
                    AlertMessage.showMessage(context,"Alert","গ্রুপ সদস্য নির্বাচন করুন");
                }else {

                    Integer[] array = select_members.toArray(new Integer[select_members.size()]);

                    Log.e("array",""+array.length);
                }

                //startActivity(new Intent(context,ChatAppActivity.class));
            }
        });

        recyclerView = findViewById(R.id.recycler_view);

        swiperefresh = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setRefreshing(true);

        search_chat_member("1");


    }


    private void search_chat_member(String page) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.show();
        swiperefresh.setRefreshing(true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ChatUserResponse> userCall = api.search_chat_member("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),"api/search_chat_member?page="+page);
        userCall.enqueue(new Callback<ChatUserResponse>() {
            @Override
            public void onResponse(Call<ChatUserResponse> call, Response<ChatUserResponse> response) {
                pd.dismiss();
                swiperefresh.setRefreshing(false);
                responsData = response.body();

                if(responsData!=null){
                    currentPage = responsData.getCurrent_page();
                    if(responsData.getData()!=null){
                        Log.e("datasize",""+responsData.getData().size());

                        for (ChatUserModel chatUserModel:responsData.getData()) {

                            chatuserlist.add(chatUserModel);

                        }

                        mAdapter = new ChatCreatAdapter(chatuserlist,context);

                        // white background notification bar
                        whiteNotificationBar(recyclerView);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();

                    }

                }else {
                    Toast.makeText(context, "Login token expired, Please login again ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,LoginActivity.class));
                    finish();
                }


            }

            @Override
            public void onFailure(Call<ChatUserResponse> call, Throwable t) {

                pd.dismiss();
            }
        });


    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        if(currentPage!=null){
            int pageNo = Integer.parseInt(currentPage)+1;
            search_chat_member(""+pageNo);
        }
    }


    public class ChatCreatAdapter extends RecyclerView.Adapter<ChatCreatAdapter.MyViewHolder> implements Filterable {


        List<ChatUserModel> contactList = new ArrayList<>();
        List<ChatUserModel> contactListFiltered = new ArrayList<>();
        Context context;




        public ChatCreatAdapter(List<ChatUserModel> userModelList, Context context) {
            this.contactList = userModelList;
            this.contactListFiltered = userModelList;

            this.context = context;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imgPicChat;
            TextView tvNameChat,tvChatDate,tvChatMsg;
            RelativeLayout relFullItem;
            CheckBox checkbox;
            public MyViewHolder(View view) {
                super(view);
                tvNameChat=(TextView) view.findViewById(R.id.tvNameChat);
                tvChatDate=(TextView) view.findViewById(R.id.tvChatDate);
                tvChatMsg=(TextView) view.findViewById(R.id.tvChatMsg);
                imgPicChat=(ImageView) view.findViewById(R.id.imgPicChat);
                relFullItem=(RelativeLayout) view.findViewById(R.id.relFullItem);
                checkbox=(CheckBox) view.findViewById(R.id.checkbox);
            }
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_create_group, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            final ChatUserModel contact = contactListFiltered.get(position);
            holder.tvChatMsg.setText(contact.getContent());
            holder.tvNameChat.setText(contact.getUsername());
            holder.tvChatDate.setText(contact.getDate());

            if(AppConstant.chatActivityName.equalsIgnoreCase("newchat")){
                holder.checkbox.setVisibility(View.GONE);
            }else if (AppConstant.chatActivityName.equalsIgnoreCase("groupchat")){
                holder.checkbox.setVisibility(View.VISIBLE);
            }

            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(b){
                        select_members.add(Integer.parseInt(contact.getId()));

                        Integer[] array = select_members.toArray(new Integer[select_members.size()]);

                        Log.e("array",""+array);

                        frmFloatingOk.setVisibility(View.VISIBLE);
                    }
                }
            });




        }


        @Override
        public int getItemCount()
        {
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
                        List<ChatUserModel> filteredList = new ArrayList<>();
                        for (ChatUserModel row : contactList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (row.getUsername().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
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
                    contactListFiltered = (ArrayList<ChatUserModel>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
//        if (!searchView.isIconified()) {
//            searchView.setIconified(true);
//            return;
//        }else {
//            finish();
//        }

        finish();
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


}
