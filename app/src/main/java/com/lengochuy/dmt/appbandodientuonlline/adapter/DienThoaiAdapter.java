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

public class DienThoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList <Sanpham> arrayListDienThoai;

    public DienThoaiAdapter(Context context, ArrayList<Sanpham> arrayListDienThoai) {
        this.context = context;
        this.arrayListDienThoai = arrayListDienThoai;
    }

    @Override
    public int getCount() {
        return arrayListDienThoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListDienThoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public static class ViewHolder{
        TextView txtDienThoai,txtGiaDienThoai,txtMotaDienThoai;
        ImageView imgDienThoai;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_dien_thoai,null);
            viewHolder.txtDienThoai = view.findViewById(R.id.textViewDienThoai);
            viewHolder.txtGiaDienThoai = view.findViewById(R.id.textviewGiaDienThoai);
            viewHolder.txtMotaDienThoai = view.findViewById(R.id.textViewMotaDienThoai);
            viewHolder.imgDienThoai = view.findViewById(R.id.imgDienThoai);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txtDienThoai.setText(sanpham.getTenSanPham());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        viewHolder.txtGiaDienThoai.setText("Giá: " + decimalFormat.format(sanpham.getGiaSanPham() + "Đ"));
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDienThoai.setText("Giá: " + decimalFormat.format(sanpham.getGiaSanPham()) + "Đ");

        viewHolder.txtMotaDienThoai.setMaxLines(2);
        viewHolder.txtMotaDienThoai.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaDienThoai.setText(sanpham.getMoTaSanPham());

        Picasso.with(context).load(sanpham.getHinhAnhSanPham()).placeholder(R.drawable.camera).error(R.drawable.error).into(viewHolder.imgDienThoai);
        return view;
    }
}
