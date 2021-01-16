package com.lengochuy.dmt.appbandodientuonlline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.adapter.GiohangAdapter;

import java.text.DecimalFormat;
import java.util.Objects;

public class Giohangactivity extends AppCompatActivity {
    ListView listView;
    TextView textViewthongBao;
    @SuppressLint("StaticFieldLeak")
    static TextView textViewTongTien;
    Button buttonThanhToan,buttonTiepTucMua;
    Toolbar toolbar;
    GiohangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohangactivity);
        
        init();
        actionToolBar();
        checkData();
        eventUltil();
        catchOnItemListView();
        eventButton();
    }

    private void eventButton() {
        buttonTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        buttonThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.gioHangArrayList.size() > 0){
                    Intent intent = new Intent(getApplicationContext(),Thongtinkhachhang.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Giỏ hàng chưa có sản phẩm để thanh toán",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void catchOnItemListView() {
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
               final AlertDialog.Builder alert = new AlertDialog.Builder(Giohangactivity.this);
               alert.setTitle("Xác nhận xóa sản phẩm");
               alert.setMessage("Bạn có chắc chắn xóa sản phẩm này");
               alert.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       if (MainActivity.gioHangArrayList.size() <= 0){
                           textViewthongBao.setVisibility(View.VISIBLE);
                       }else{
                           MainActivity.gioHangArrayList.remove(position);
                           adapter.notifyDataSetChanged();
                           eventUltil();
                           if (MainActivity.gioHangArrayList.size() <= 0){
                               textViewthongBao.setVisibility(View.VISIBLE);
                           }else{
                               textViewthongBao.setVisibility(View.INVISIBLE);
                               adapter.notifyDataSetChanged();
                               eventUltil();
                           }
                       }
                   }
               });

               alert.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               alert.show();

               return false;
           }
       });
    }

    @SuppressLint("SetTextI18n")
    public static void eventUltil() {
        long tongtien = 0;
        for (int i = 0 ; i < MainActivity.gioHangArrayList.size() ; i++){
            tongtien += MainActivity.gioHangArrayList.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewTongTien.setText(decimalFormat.format(tongtien) + "Đ");
    }

    private void checkData() {
        if (MainActivity.gioHangArrayList.isEmpty()){
            adapter.notifyDataSetChanged();
            textViewthongBao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else{
            adapter.notifyDataSetChanged();
            textViewthongBao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        listView = findViewById(R.id.listViewDoHang);
        textViewthongBao = findViewById(R.id.textViewThongbaoGH);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        buttonThanhToan = findViewById(R.id.buttonThanhToan);
        buttonTiepTucMua = findViewById(R.id.buttonTiepTucMua);
        toolbar = findViewById(R.id.toolBarGioHang);
        adapter = new GiohangAdapter(getApplicationContext(),MainActivity.gioHangArrayList);
        listView.setAdapter(adapter);
    }
}