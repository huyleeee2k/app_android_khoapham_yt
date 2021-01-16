package com.lengochuy.dmt.appbandodientuonlline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.activity.Giohangactivity;
import com.lengochuy.dmt.appbandodientuonlline.activity.MainActivity;
import com.lengochuy.dmt.appbandodientuonlline.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList <GioHang> gioHangArrayList;

    public GiohangAdapter(Context context, ArrayList<GioHang> gioHangArrayList) {
        this.context = context;
        this.gioHangArrayList = gioHangArrayList;
    }

    @Override
    public int getCount() {
        return gioHangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return gioHangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        public  TextView txtTenGioHang,txtTenGiaHang;
        public ImageView imgGioHang;
        public Button buttonTru,buttonCong,buttonGiaTri;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_gio_hang,null);
            holder.txtTenGioHang = view.findViewById(R.id.txtTenSpGioHang);
            holder.txtTenGiaHang = view.findViewById(R.id.txtGiaGioHang);
            holder.imgGioHang = view.findViewById(R.id.imgGioHang);
            holder.buttonTru = view.findViewById(R.id.buttonTruGH);
            holder.buttonCong = view.findViewById(R.id.buttonCongGH);
            holder.buttonGiaTri = view.findViewById(R.id.buttonHienThiSoluongGH);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = (GioHang) getItem(i);
        holder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtTenGiaHang.setText(decimalFormat.format(gioHang.getGiasp()) + "Đ");
        Picasso.with(context).load(gioHang.getHinhanhsp()).placeholder(R.drawable.camera).error(R.drawable.error).into(holder.imgGioHang);
        holder.buttonGiaTri.setText(gioHang.getSoluongsp() + "");

        int soluong = Integer.parseInt(holder.buttonGiaTri.getText().toString());
        if (soluong >= 10){
            holder.buttonCong.setVisibility(View.INVISIBLE);
            holder.buttonTru.setVisibility(View.VISIBLE);
        }else if (soluong <= 1){
            holder.buttonTru.setVisibility(View.INVISIBLE);
        }else{
            holder.buttonTru.setVisibility(View.VISIBLE);
            holder.buttonCong.setVisibility(View.VISIBLE);
        }
        holder.buttonCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(holder.buttonGiaTri.getText().toString()) + 1;
                int slht = MainActivity.gioHangArrayList.get(i).getSoluongsp();
                long giaht = MainActivity.gioHangArrayList.get(i).getGiasp(); 
                MainActivity.gioHangArrayList.get(i).setSoluongsp(soluongmoinhat);
                long giamoinhat = (giaht * soluongmoinhat ) / slht;
                MainActivity.gioHangArrayList.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtTenGiaHang.setText(decimalFormat.format(giamoinhat) + "Đ");
                Giohangactivity.eventUltil();
                if (soluongmoinhat > 9){
                    holder.buttonCong.setVisibility(View.INVISIBLE);
                    holder.buttonTru.setVisibility(View.VISIBLE);
                }else{
                    holder.buttonTru.setVisibility(View.VISIBLE);
                    holder.buttonCong.setVisibility(View.VISIBLE);
                }
                holder.buttonGiaTri.setText(String.valueOf(soluongmoinhat));
            }
        });

        holder.buttonTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongmoinhat = Integer.parseInt(holder.buttonGiaTri.getText().toString()) - 1;
                int slht = MainActivity.gioHangArrayList.get(i).getSoluongsp();
                long giaht = MainActivity.gioHangArrayList.get(i).getGiasp();
                MainActivity.gioHangArrayList.get(i).setSoluongsp(soluongmoinhat);
                long giamoinhat = (giaht * soluongmoinhat )/ slht;
                MainActivity.gioHangArrayList.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                holder.txtTenGiaHang.setText(decimalFormat.format(giamoinhat) + "Đ");
                Giohangactivity.eventUltil();
                if (soluongmoinhat < 2){
                    holder.buttonCong.setVisibility(View.VISIBLE);
                    holder.buttonTru.setVisibility(View.INVISIBLE);
                }else{
                    holder.buttonTru.setVisibility(View.VISIBLE);
                    holder.buttonCong.setVisibility(View.VISIBLE);
                }
                holder.buttonGiaTri.setText(String.valueOf(soluongmoinhat));
            }
        });
        return view;
    }
}
