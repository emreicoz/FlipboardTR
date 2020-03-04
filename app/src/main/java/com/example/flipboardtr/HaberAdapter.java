package com.example.flipboardtr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HaberAdapter extends RecyclerView.Adapter<HaberAdapter.HaberViewHolder> {
    private Context mContext;
    private ArrayList<Haber> mhaberList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    public HaberAdapter(Context context,ArrayList<Haber> haberList){
        mContext=context;
        mhaberList=haberList;

    }

    @NonNull
    @Override
    public HaberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.haber_item,parent,false);
        return new HaberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HaberViewHolder holder, int position) {
        Haber currentItem = mhaberList.get(position);

        String imageUrl = currentItem.getUrlToImage();
        String haberBaslik = currentItem.getTitlee();
        String haberKaynak = currentItem.getAuthor();
        String haberIcerik = currentItem.getDescription();

        holder.mTextViewBaslik.setText(haberBaslik);
        holder.mTextViewKaynak.setText(haberKaynak);
        holder.mTextViewIcerik.setText(haberIcerik);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mhaberList.size();
    }

    public class HaberViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewBaslik;
        public TextView mTextViewKaynak;
        public TextView mTextViewIcerik;


        public HaberViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewBaslik = itemView.findViewById(R.id.text_view_baslik);
            mTextViewKaynak = itemView.findViewById(R.id.text_view_kaynak);
            mTextViewIcerik = itemView.findViewById(R.id.text_view_icerik);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.OnItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
