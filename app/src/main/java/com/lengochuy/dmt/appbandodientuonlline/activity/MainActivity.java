package com.lengochuy.dmt.appbandodientuonlline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.adapter.SanphamAdapter;
import com.lengochuy.dmt.appbandodientuonlline.adapter.SanphammoinhatAdapter;
import com.lengochuy.dmt.appbandodientuonlline.model.GioHang;
import com.lengochuy.dmt.appbandodientuonlline.model.Loaisp;
import com.lengochuy.dmt.appbandodientuonlline.model.Sanpham;
import com.lengochuy.dmt.appbandodientuonlline.ultil.Server;
import com.lengochuy.dmt.appbandodientuonlline.ultil.checkConnection;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbarMHC;
    ViewFlipper viewFlipperMHC;
    RecyclerView recyclerViewMHC;
    NavigationView navigationViewMHC;
    ListView listViewMHC;
    DrawerLayout drawerLayoutMHC;
    ArrayList  <Loaisp> loaispArrayList;
    SanphamAdapter adapter;
    int id = 0;
    String tenLoaiSp = "";
    String hinhAnhSp = "";
    ArrayList <Sanpham> sanphamArrayList;
    SanphammoinhatAdapter sanphammoinhatAdapter;
    public static ArrayList <GioHang> gioHangArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //Kiểm tra kết nối internet: nếu có kết nối thì thực hiện chức năng bên trong
        //Ngược lại thì thông báo ra màn hình không có kết nối internet
        if (checkConnection.checkInternet(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            getDuLieuLoaiSp();
            getDuLieuSpMoiNhat();
            CatchOnItemListView();
        }else{
            Toast.makeText(getApplicationContext(), "Bạn chưa kết nối internet",
                    Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void CatchOnItemListView() {
        listViewMHC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (checkConnection.checkInternet(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Bạn chưa kết nối internet"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        drawerLayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (checkConnection.checkInternet(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienthoaiActivity.class);
                            intent.putExtra("idloaisanpham",loaispArrayList.get(i).getId());
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Bạn chưa kết nối internet"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        drawerLayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (checkConnection.checkInternet(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,Laptopactivity.class);
                            intent.putExtra("idloaisanpham1",loaispArrayList.get(i).getId());
                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Bạn chưa kết nối internet"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        drawerLayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (checkConnection.checkInternet(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienheActivity.class);

                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Bạn chưa kết nối internet"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        drawerLayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (checkConnection.checkInternet(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongtinActivity.class);

                            startActivity(intent);
                        }else{
                            Toast.makeText(MainActivity.this, "Bạn chưa kết nối internet"
                                    , Toast.LENGTH_SHORT).show();
                        }
                        drawerLayoutMHC.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    @Override
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

    //Add dữ liệu vào những sản phẩm mới nhất
    private void getDuLieuSpMoiNhat() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanspmoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int Id;
                    String tensp;
                    String hinhAnhSp;
                    long gia;
                    String motasp;
                    int idSanPham;
                    for (int i = 0 ; i < response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            gia = jsonObject.getLong("giasp");
                            hinhAnhSp = jsonObject.getString("hinhanhsp");
                            motasp = jsonObject.getString("motasp");
                            idSanPham = jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(Id,tensp,gia,hinhAnhSp,motasp,idSanPham));
                            sanphammoinhatAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    //Dùng volley để đọc json từ server
    //Tạo dữ liệu cho adapter
    private void getDuLieuLoaiSp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongDanLoaiSp,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0 ; i < response.length() ; i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaiSp = jsonObject.getString("tenloaisp");
                            hinhAnhSp = jsonObject.getString("hinhanhloaisp");
                            loaispArrayList.add(new Loaisp(id,tenLoaiSp,hinhAnhSp));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    loaispArrayList.add(new Loaisp(3,"Liên hệ",
                            "https://icon-library.com/images/icon-call/icon-call-4.jpg"));
                    loaispArrayList.add(new Loaisp(4,"Thông tin",
                            "https://cdn.iconscout.com/icon/premium/png-256-thumb/information-user-1352362-1236069.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Loi " + error,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    //Custom flipper để load quảng cáo
    private void ActionViewFlipper() {
        ArrayList <String> quangcaolist = new ArrayList<>();
        quangcaolist.add("https://cellphones.com.vn/sforum/wp-content/uploads/2019/05/Honor-20-Pro-lo-anh-quang-cao-1.jpg");
        quangcaolist.add("https://static.dientutieudung.vn/img/2019/04/bg11-1554863190.jpg");
        quangcaolist.add("https://static.dientutieudung.vn/img/2019/11/vivo-s1-pro-3-1574402175.png");
        quangcaolist.add("https://i.ytimg.com/vi/nhV-sVcGHpA/maxresdefault.jpg");
        for (int i = 0 ; i < quangcaolist.size() ; i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(quangcaolist.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMHC.addView(imageView);
        }
        viewFlipperMHC.setFlipInterval(5000);
        viewFlipperMHC.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_rigth);
        viewFlipperMHC.setInAnimation(animation_slide_in);
        viewFlipperMHC.setOutAnimation(animation_slide_out);
    }

    //Tạo thanh navigation bar (menu)
    private void ActionBar() {
        setSupportActionBar(toolbarMHC);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarMHC.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);//ic_menu_sort_by_size
        toolbarMHC.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayoutMHC.openDrawer(GravityCompat.START);
            }
        });
    }

    //Ánh xạ , setAdapter, khời tạo ArrayList
    @SuppressLint("WrongConstant")
    private void init() {
        toolbarMHC          = findViewById(R.id.toolBarManHinhChinh);
        viewFlipperMHC      = findViewById(R.id.viewFlipperManHinhChinh);
        recyclerViewMHC     = findViewById(R.id.recyclerViewManHinhChinh);
        navigationViewMHC   = findViewById(R.id.navigationViewManHinhChinh);
        drawerLayoutMHC     = findViewById(R.id.drawerlayoutManHinhChinh);
        listViewMHC         = findViewById(R.id.listViewManHinhChinh);

        loaispArrayList = new ArrayList<>();
        loaispArrayList.add(0,new Loaisp(0,"Trang chủ","https://icons.iconarchive.com/icons/graphicloads/100-flat/256/home-icon.png"));
        adapter = new SanphamAdapter(loaispArrayList,getApplicationContext());
        listViewMHC.setAdapter(adapter);

        sanphamArrayList = new ArrayList<>();
        sanphammoinhatAdapter = new SanphammoinhatAdapter(getApplicationContext(),sanphamArrayList);
        recyclerViewMHC.setHasFixedSize(true);
        recyclerViewMHC.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        //recyclerViewMHC.setLayoutDirection(View.TEXT_DIRECTION_FIRST_STRONG);
        recyclerViewMHC.setAdapter(sanphammoinhatAdapter);
        if (gioHangArrayList == null){
            gioHangArrayList = new ArrayList<>();
        }
    }
}