package com.example.jwllls.wanyi.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jwllls.wanyi.R;
import com.example.jwllls.wanyi.adapter.Adapter_buety;
import com.example.jwllls.wanyi.bean.photo;
import com.example.jwllls.wanyi.util.GlideCacheUtil;
import com.example.jwllls.wanyi.util.HttpUtil;
import com.example.jwllls.wanyi.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jwllls on 2016/12/7.
 */

public class Buety extends ToolBarActivity {
    private TextView tv_page;
    private RecyclerView recyclerView;
    private HttpUtil httputil;
    private GlideCacheUtil glideCacheUtil;
    private Adapter_buety adapter;
    private List<photo.buty> p = new ArrayList<>();
    private String jsonStr = "";
    private int page = 1 + (int) (Math.random() * 50);
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    parseJson(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_buety);
        initView();
        tv_page.setText(String.valueOf(page) + "页");
        initToolBar();
        httputil = new HttpUtil(this);
        glideCacheUtil = new GlideCacheUtil();
        adapter = new Adapter_buety(this, p);
        getdata(String.valueOf(page));
        parseJson(jsonStr);

    }

    private void initToolBar() {
        Toolbar toolbar = new Toolbar(this);
        toolbar.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.addView(new EditText(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.getItem(1).setTitle(glideCacheUtil.getCacheSize(this));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings://上一页
                if (page > 1) {
                    getdata(String.valueOf(--page));
                    recyclerView.scrollToPosition(0);
                    tv_page.setText(String.valueOf(page) + "页");
                } else
                    Toast.makeText(this, "已经是第一页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_next://下一页
                if (page < 50) {
                    getdata(String.valueOf(++page));
                    recyclerView.scrollToPosition(0);
                    tv_page.setText(String.valueOf(page) + "页");
                } else
                    Toast.makeText(this, "已经是最后一页", Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_input://输入页数
                final EditText et_page = new EditText(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("请输入页数（小于50页）")
                        .setView(et_page)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("走起", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str = et_page.getText().toString();
                                if (Integer.valueOf(str) > 1000 || Integer.valueOf(str) < 1 || !str.matches("[0-9]+")) {
                                    Toast.makeText(Buety.this, "请输入正确的页数", Toast.LENGTH_SHORT).show();
                                } else {
                                    page = Integer.valueOf(str);
                                    getdata(String.valueOf(page));
                                    recyclerView.scrollToPosition(0);
                                    tv_page.setText(String.valueOf(page) + "页");
                                }
                            }
                        });
                builder.create().show();
                break;
            case R.id.action_clean:
                AlertDialog.Builder b = new AlertDialog.Builder(this)
                        .setTitle("是否清除缓存？")
                        .setMessage("确认将清楚所有图片、文字等数据！")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                glideCacheUtil.clearImageAllCache(Buety.this);
                                ToastUtil.showToast(Buety.this, "缓存已经清除");
                            }
                        });
                b.create().show();
                break;
            case R.id.action_showcache:
                item.setTitle(glideCacheUtil.getCacheSize(Buety.this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void parseJson(String jsonstr) {

        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<photo>() {
        }.getType();
        if (!TextUtils.isEmpty(jsonstr)) {
            photo photos = gson.fromJson(jsonstr, type);
            p.clear();
            p.addAll(photos.getTngou());
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getdata(final String page) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url("http://www.tngou.net/tnfs/api/list?page=" + page + "&rows=10")
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
        }).start();
    }

    private void initView() {
        tv_page = (TextView) findViewById(R.id.list_page);
        recyclerView = (RecyclerView) findViewById(R.id.rec_buety);
        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}
