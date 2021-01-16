package com.lengochuy.dmt.appbandodientuonlline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList <Sanpham> arrayListLaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arrayListDienThoai) {
        this.context = context;
        this.arrayListLaptop = arrayListDienThoai;
    }

    @Override
    public int getCount() {
        return arrayListLaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListLaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        TextView txtLaptop,txtGiaLaptop,txtMotaLaptop;
        ImageView imgLaptop;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dien_thoai,null);
            viewHolder.txtLaptop = view.findViewById(R.id.textViewDienThoai);
            viewHolder.txtGiaLaptop = view.findViewById(R.id.textviewGiaDienThoai);
            viewHolder.txtMotaLaptop = view.findViewById(R.id.textViewMotaDienThoai);
            viewHolder.imgLaptop = view.findViewById(R.id.imgDienThoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txtLaptop.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaLaptop.setText("Giá: " + decimalFormat.format(sanpham.getGiaSanPham()) + "Đ");

        viewHolder.txtMotaLaptop.setMaxLines(2);
        viewHolder.txtMotaLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaLaptop.setText(sanpham.getMoTaSanPham());

        Picasso.with(context).load(sanpham.getHinhAnhSanPham()).placeholder(R.drawable.camera).error(R.drawable.error).into(viewHolder.imgLaptop);
        return view;
    }
}
