package com.lengochuy.dmt.appbandodientuonlline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * ListviewAdapter : custom listview navigasion bar
 */

public class SanphamAdapter extends BaseAdapter {
    ArrayList <Loaisp> loaispArrayList;
    Context context;

    public SanphamAdapter(ArrayList<Loaisp> loaispArrayList, Context context) {
        this.loaispArrayList = loaispArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaispArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaispArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder{
        TextView txtTenLoaiSp;
        ImageView imgLoaiSp;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_sanpham,null);
            holder.txtTenLoaiSp = view.findViewById(R.id.textViewHinh);
            holder.imgLoaiSp    = view.findViewById(R.id.imageViewHinh);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Loaisp loaisp = loaispArrayList.get(i);

        holder.txtTenLoaiSp.setText(loaisp.getTenLoaiSp());
        Picasso.with(context).load(loaispArrayList.get(i).getHinhAnhLoaiSp()).
                placeholder(R.drawable.camera).error(R.drawable.error).into(holder.imgLoaiSp);
        return view;
    }
}
