package com.lengochuy.dmt.appbandodientuonlline.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.ultil.Server;
import com.lengochuy.dmt.appbandodientuonlline.ultil.checkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
    EditText editTextTenKH, editTextSDT, editTextEmail;
    Button buttonXacNhan, buttonHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        
        init();
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (checkConnection.checkInternet(getApplicationContext())){
            eventButton();
        }else{
            Toast.makeText(Thongtinkhachhang.this, "Bạn chưa kết nối internet",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void eventButton() {
        buttonXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tenKH = editTextTenKH.getText().toString().trim();
                final String SDT   = editTextSDT.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                if (tenKH.length() > 0 && SDT.length() > 0 && email.length() > 0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response1) {
                           // Log.d("madonhang",response);
                            Log.d("kiemtra1", response1);
                            if (!response1.equals("")){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("kiemtra2", response);
                                        if (response.equals("1")){
                                            MainActivity.gioHangArrayList.clear();
                                            Toast.makeText(Thongtinkhachhang.this, "Bạn đã thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Thongtinkhachhang.this,MainActivity.class));
                                            Toast.makeText(Thongtinkhachhang.this, "Mời bạn tiếp tục mua hàng", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Thongtinkhachhang.this, "Dữ liệu giỏ hàng bạn bị lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0 ; i < MainActivity.gioHangArrayList.size() ; i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",response1);
                                                jsonObject.put("masanpham",MainActivity.gioHangArrayList.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.gioHangArrayList.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.gioHangArrayList.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.gioHangArrayList.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap <String,String> hashMap = new HashMap<>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap <String,String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang",tenKH);
                            hashMap.put("sodienthoai",SDT);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(Thongtinkhachhang.this, "Hãy kiểm tra lại dữ liệu",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        editTextTenKH = findViewById(R.id.edittenKhachHang);
        editTextSDT   = findViewById(R.id.editSDT);
        editTextEmail = findViewById(R.id.editemailKH);
        buttonXacNhan = findViewById(R.id.buttonXacNhan);
        buttonHuy     = findViewById(R.id.buttonHuy);
    }
}