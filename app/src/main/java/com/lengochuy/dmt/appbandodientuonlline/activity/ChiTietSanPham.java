package com.lengochuy.dmt.appbandodientuonlline.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.model.GioHang;
import com.lengochuy.dmt.appbandodientuonlline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Objects;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarChiTiet;
    ImageView imgChiTiet;
    TextView txtTenSp,txtGiaSp,txtMota;
    Spinner spinner;
    Button button;
    int id;
    String tenchitiet,hinhanh,mota;
    long giasp;
    int idsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        init();
        actionBar();
        getInformation();
        catchEventSpinner();
        eventButton();
    }

    private void eventButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.gioHangArrayList.size() > 0){
                    int sl =  Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean kt = false;
                    for (int i = 0 ; i < MainActivity.gioHangArrayList.size() ; i++){
                        if (MainActivity.gioHangArrayList.get(i).getIdsp() == id){
                            MainActivity.gioHangArrayList.get(i).setSoluongsp(MainActivity.gioHangArrayList.get(i).soluongsp + sl);
                            if (MainActivity.gioHangArrayList.get(i).getSoluongsp() >= 10){
                                MainActivity.gioHangArrayList.get(i).setSoluongsp(10);
                            }
                            MainActivity.gioHangArrayList.get(i).setGiasp(MainActivity.gioHangArrayList.get(i).getSoluongsp() * giasp);
                            kt = true;
                        }
                    }
                    if (!kt){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giasp;
                        MainActivity.gioHangArrayList.add(new GioHang(id,tenchitiet,giamoi,hinhanh,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * giasp;
                    MainActivity.gioHangArrayList.add(new GioHang(id,tenchitiet,giamoi,hinhanh,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),Giohangactivity.class);
                startActivity(intent);
            }
        });
    }

    private void catchEventSpinner() {
        Integer [] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter <Integer> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void getInformation() {

        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        assert sanpham != null;
        id              = sanpham.getId();
        tenchitiet      = sanpham.getTenSanPham();
        hinhanh         = sanpham.getHinhAnhSanPham();
        mota            = sanpham.getMoTaSanPham();
        giasp           = sanpham.getGiaSanPham();
        idsp            = sanpham.getIdSanPham();
        txtTenSp.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSp.setText("Giá: " + decimalFormat.format(giasp) + " Đ");
        txtMota.setText(mota);
        Picasso.with(getApplicationContext()).load(hinhanh).placeholder(R.drawable.camera).error(R.drawable.error).into(imgChiTiet);
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

    private void actionBar() {
        setSupportActionBar(toolbarChiTiet);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        toolbarChiTiet = findViewById(R.id.toolbarChitietsanpham);
        imgChiTiet     = findViewById(R.id.imgChiTietSanPham);
        txtTenSp       = findViewById(R.id.txtTenSpCTSP);
        txtGiaSp       = findViewById(R.id.txtGiaSpCTSP);
        txtMota        = findViewById(R.id.txtMoTaCTSP);
        spinner        = findViewById(R.id.spinnerCTSP);
        button         = findViewById(R.id.buttonDatmua);
    }
}