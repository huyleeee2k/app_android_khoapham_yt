package com.lengochuy.dmt.appbandodientuonlline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.adapter.LaptopAdapter;
import com.lengochuy.dmt.appbandodientuonlline.model.Sanpham;
import com.lengochuy.dmt.appbandodientuonlline.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Laptopactivity extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    LaptopAdapter adapter;
    ArrayList<Sanpham> manglt;
    View footerView;
    int idlt;
    boolean isLoading = false;
    mHandler mHandler;
    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptopactivity);

        init();
        getIdLoaiSp();
        actionToolbar();
        getData();
        LoadMoreData();
    }

    private void LoadMoreData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",manglt.get(i));
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && !isLoading && !limitData){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    //Lấy dữ liệu từ csdl
    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdanlaptop + "1";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id ;
                String tendt;
                long gia;
                String hinhanhdt;
                String mota;
                int idspdt;
                if (response != null && response.length() != 2){
                    listView.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject =  jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt = jsonObject.getString("tensp");
                            gia = jsonObject.getLong("giasp");
                            hinhanhdt = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("motasp");
                            idspdt = jsonObject.getInt("idsanpham");
                            manglt.add(new Sanpham(id,tendt,gia,hinhanhdt,mota,idspdt));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitData = true;
                    listView.removeFooterView(footerView);
                    Toast.makeText(Laptopactivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> param = new HashMap<>();
                param.put("idsanpham",String.valueOf(idlt));
                return param;
            }
        };
        queue.add(stringRequest);
    }
    //Tạo nút quay về ở thanh tool bar
    private void actionToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menugiohang) {
            Intent intent = new Intent(getApplicationContext(), Giohangactivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    //Lấy id truyền qua từ màn hình mainactivity
    private void getIdLoaiSp() {
        idlt = getIntent().getIntExtra("idloaisanpham1",-1);
    }
    //ánh xạ
    @SuppressLint("InflateParams")
    private void init() {
        toolbar = findViewById(R.id.toolbarLaptop);
        listView = findViewById(R.id.listViewLaptop);
        manglt = new ArrayList<>();
        adapter = new LaptopAdapter(getApplicationContext(),manglt);
        listView.setAdapter(adapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }

    @SuppressLint("HandlerLeak")
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footerView);
                    break;
                case 1:
                    getData();
                    isLoading = false;
                    break;
            }

            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}