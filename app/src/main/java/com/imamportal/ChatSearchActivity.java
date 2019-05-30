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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.imamportal.Adapter.ChatCreatAdapter;
import com.imamportal.Adapter.ChatUsetListAdapter;
import com.imamportal.model.ChatUserModel;
import com.imamportal.model.ChatUserResponse;
import com.imamportal.utils.AlertMessage;
import com.imamportal.utils.Api;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.NetInfo;
import com.imamportal.utils.PersistData;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatSearchActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private SearchView searchView;

    Context context;
    ChatUsetListAdapter mAdapter;
    private LinearLayout linCreateGroup,linNewChat;

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
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swiperefresh = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        swiperefresh.setOnRefreshListener(this);
        swiperefresh.setRefreshing(true);

        recyclerView = findViewById(R.id.recycler_view);
        linCreateGroup = (LinearLayout) findViewById(R.id.linCreateGroup);
        linNewChat = (LinearLayout) findViewById(R.id.linNewChat);
        linCreateGroup.setVisibility(View.VISIBLE);
        linNewChat.setVisibility(View.VISIBLE);



        linCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,ChatCreateGroupActivity.class));
                AppConstant.chatActivityName="groupchat";
            }
        });
        linNewChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(context,ChatNewChatActivity.class));
                        AppConstant.chatActivityName="newchat";
                    }
                });


        message_conversations("1");
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


    private void message_conversations(String page) {

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
        Call<ChatUserResponse> userCall = api.message_conversations("Bearer "+ PersistData.getStringData(context, AppConstant.loginToken),"api/message_conversations?page="+page);
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

                            Log.e("userid",""+chatUserModel.getUser_id());

                            chatuserlist.add(chatUserModel);

                        }

                        mAdapter = new ChatUsetListAdapter(chatuserlist,context);

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
            message_conversations(""+pageNo);
        }
    }
}
