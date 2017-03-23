package com.example.jwllls.wanyi.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.jwllls.wanyi.R;
import com.example.jwllls.wanyi.adapter.MyAdapter;
import com.example.jwllls.wanyi.bean.News;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jwllls on 2016/12/14.
 */

public class TabLayoutFragment extends Fragment {
    public static String TABLAYOUT_FRAGMENT = "tab_fragment";
    private int type;
    private Context context;
    private TextView title, realtype, time;
    private ImageView img;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout layout;
    private List<News.ResultBean.DataBean> dataBeen = new ArrayList<News.ResultBean.DataBean>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    System.out.println("haha=" + msg.obj.toString());
                    praseData(msg.obj.toString());
                    break;
            }
        }
    };

    public static TabLayoutFragment newInstance(int type) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TABLAYOUT_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TABLAYOUT_FRAGMENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frg_toutiao, container, false);
        layout = (MaterialRefreshLayout) view.findViewById(R.id.refreshlayout);
        initView(view);
        return view;
    }

    protected void initView(View view) {
        adapter = new MyAdapter(getActivity(), dataBeen);

        layout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                        public void run() {
                        switch (type)
                        {
                            case 1:
                                getNewsData("top");
                                break;
                            case 2:
                                getNewsData("shehui");
                                break;
                            case 3:
                                getNewsData("guonei");
                                break;
                            case 4:
                                getNewsData("guoji");
                                break;
                            case 5:
                                getNewsData("yule");
                                break;
                            case 6:
                                getNewsData("tiyu");
                                break;
                            case 7:
                                getNewsData("junshi");
                                break;
                            case 8:
                                getNewsData("keji");
                                break;
                            case 9:
                                getNewsData("caijing");
                                break;
                            case 10:
                                getNewsData("shishang");
                                break;
                        }
                        adapter.notifyDataSetChanged(); //通知适配器
                        recyclerView.scrollToPosition(0);//返回顶部
                        layout.finishRefresh(); //完成刷新
                    }
                }, 1000);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        switch (type) {
            case 1:
                getNewsData("top");
                adapter.notifyDataSetChanged();
                break;
            case 2:
                getNewsData("shehui");
                adapter.notifyDataSetChanged();
                break;
            case 3:
                getNewsData("guonei");
                adapter.notifyDataSetChanged();
                break;
            case 4:
                getNewsData("guoji");
                adapter.notifyDataSetChanged();
                break;
            case 5:
                getNewsData("yule");
                adapter.notifyDataSetChanged();
                break;
            case 6:
                getNewsData("tiyu");
                adapter.notifyDataSetChanged();
                break;
            case 7:
                getNewsData("junshi");
                adapter.notifyDataSetChanged();
                break;
            case 8:
                getNewsData("keji");
                adapter.notifyDataSetChanged();
                break;
            case 9:
                getNewsData("caijing");
                adapter.notifyDataSetChanged();
                break;
            case 10:
                getNewsData("shishang");
                adapter.notifyDataSetChanged();
                break;
            default:
                getNewsData("top");
                adapter.notifyDataSetChanged();
                break;
        }
    }


    public void praseData(String jsonData) {
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<News>() {
        }.getType();

        if (!TextUtils.isEmpty(jsonData)) {
            News news = gson.fromJson(jsonData, type);
            dataBeen.clear();
            dataBeen.addAll(news.getResult().getData());
            adapter.notifyDataSetChanged();
        }

    }


    public void getNewsData(String t) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/toutiao/index?key=f91e041225cb343f3967f4395624f5ed&type=" + t + "")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string().toString();
                Message message = new Message();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);
            }
        });

    }

}
