package com.lengochuy.dmt.appbandodientuonlline.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lengochuy.dmt.appbandodientuonlline.R;
import com.lengochuy.dmt.appbandodientuonlline.activity.ChiTietSanPham;
import com.lengochuy.dmt.appbandodientuonlline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class SanphammoinhatAdapter extends RecyclerView.Adapter<SanphammoinhatAdapter.ItemHolder>
{
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static ArrayList<Sanpham> sanphammoinhatAdapters;

    public SanphammoinhatAdapter(Context context, ArrayList<Sanpham> sanphammoinhatAdapters) {
        SanphammoinhatAdapter.context = context;
        SanphammoinhatAdapter.sanphammoinhatAdapters = sanphammoinhatAdapters;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_san_pham_moinhat,null);
        return new ItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = sanphammoinhatAdapters.get(position);
//        holder.txtTenSp.setText(sanpham.getTenSanPham());
        holder.txtTenSp.setMaxLines(2);
        holder.txtTenSp.setEllipsize(TextUtils.TruncateAt.END);
        holder.txtTenSp.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSanPham.setText("Giá: " + decimalFormat.format(sanpham.getGiaSanPham()) + "Đ");
        Picasso.with(context).load(sanpham.getHinhAnhSanPham()).
                placeholder(R.drawable.camera).error(R.drawable.error).into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return sanphammoinhatAdapters.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txtTenSp,txtGiaSanPham;

        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            imghinhsanpham = itemView.findViewById(R.id.imgSanPham);
            txtGiaSanPham  = itemView.findViewById(R.id.textviewGiaSp);
            txtTenSp       = itemView.findViewById(R.id.textviewTenSp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    intent.putExtra("thongtinsanpham",sanphammoinhatAdapters.get(getLayoutPosition()));
                    //Toast.makeText(view.getContext(), sanphammoinhatAdapters.get(getLayoutPosition()).getTenSanPham(), Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);

                }
            });

        }
    }
}
