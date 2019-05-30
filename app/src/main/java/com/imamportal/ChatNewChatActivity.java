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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.imamportal.Adapter.ChatCreatAdapter;
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

public class ChatNewChatActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private SearchView searchView;

    Context context;
    ChatCreatAdapter mAdapter;
    private LinearLayout linCreateGroup,linNewChat,linGroupName;
    private FrameLayout frmFloatingOk;
    private ChatUserResponse responsData;
    SwipyRefreshLayout swiperefresh;
    String currentPage;
    List <ChatUserModel>  chatuserlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_search);

        context=this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Chat");
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
        linGroupName.setVisibility(View.GONE);
        frmFloatingOk.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recycler_view);
        swiperefresh = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setRefreshing(true);


//        ChatUserModel chatUserModel = new ChatUserModel();
//
//        chatUserModel.setContent("Hi whats up");
//        chatUserModel.setUsername("Alom ali");
//        chatUserModel.setDate("21,10,2018");
//
//        List <ChatUserModel>  chatulist = new ArrayList<>();
//
////        for (int i = 0; i < 15; i++) {
////            chatUserModel.setUsername("Alom ali");
////            chatulist.add(chatUserModel);
////
////        }
//
//        ChatUserModel chatUserModel1 = new ChatUserModel();
//
//        chatUserModel1.setContent("Hi whats up");
//        chatUserModel1.setUsername("azim");
//        chatUserModel1.setDate("21,10,2018");
//        chatulist.add(chatUserModel1);
//
//        ChatUserModel chatUserModel2 = new ChatUserModel();
//
//        chatUserModel2.setContent("Hi whats up");
//        chatUserModel2.setUsername("Halim");
//        chatUserModel2.setDate("21,10,2018");
//        chatulist.add(chatUserModel2);
//
//        ChatUserModel chatUserModel3 = new ChatUserModel();
//
//        chatUserModel3.setContent("Hi whats up");
//        chatUserModel3.setUsername("Joni");
//        chatUserModel3.setDate("21,10,2018");
//        chatulist.add(chatUserModel3);
//
//        ChatUserModel chatUserModel4 = new ChatUserModel();
//
//        chatUserModel4.setContent("Hi whats up");
//        chatUserModel4.setUsername("Moni");
//        chatUserModel4.setDate("21,10,2018");
//        chatulist.add(chatUserModel4);
//
//
//        ChatUserModel chatUserModel5 = new ChatUserModel();
//
//        chatUserModel5.setContent("Hi whats up");
//        chatUserModel5.setUsername("Kalm");
//        chatUserModel5.setDate("21,10,2018");
//        chatulist.add(chatUserModel5);
//
//        ChatUserModel chatUserModel6 = new ChatUserModel();
//
//        chatUserModel6.setContent("Hi whats up");
//        chatUserModel6.setUsername("Amin");
//        chatUserModel6.setDate("21,10,2018");
//        chatulist.add(chatUserModel6);
//
//
//        ChatUserModel chatUserModel7 = new ChatUserModel();
//
//        chatUserModel7.setContent("Hi whats up");
//        chatUserModel7.setUsername("Sony");
//        chatUserModel7.setDate("21,10,2018");
//        chatulist.add(chatUserModel7);
//
//        ChatUserModel chatUserModel8 = new ChatUserModel();
//
//        chatUserModel8.setContent("Hi whats up");
//        chatUserModel8.setUsername("Any");
//        chatUserModel8.setDate("21,10,2018");
//        chatulist.add(chatUserModel8);


        search_chat_member("1");

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

                        mAdapter = new ChatCreatAdapter(chatuserlist,frmFloatingOk,context);

                        // white background notification bar
                        whiteNotificationBar(recyclerView);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
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
}
