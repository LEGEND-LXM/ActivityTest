package com.example.cardview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private Context mContext;

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView)view;
            fruitImage = view.findViewById(R.id.fruitImage);
            fruitName = view.findViewById(R.id.fruitName);
        }
    }

    public FruitAdapter(List<Fruit> fruitList) {
        mFruitList = fruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        Log.d("FruitAdapter1", "1");
        View view = LayoutInflater.from(mContext).inflate(R.layout.fruit_item, parent, false);
        Log.d("FruitAdapter1", "2");
        final ViewHolder holder = new ViewHolder(view);
        Log.d("FruitAdapter1", "3");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FruitAdapter1", "4");
                int position = holder.getAdapterPosition();
                Log.d("FruitAdapter1", "5");
                Fruit fruit = mFruitList.get(position);
                Log.d("FruitAdapter1", "6");
                Intent intent = new Intent(mContext, FruitActivity.class);
                Log.d("FruitAdapter1", "7");
                intent.putExtra(FruitActivity.FRUIT_NAME, fruit.getName());
                Log.d("FruitAdapter1", "8");
                intent.putExtra(FruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                Log.d("FruitAdapter1", "9");
                mContext.startActivity(intent);
                Log.d("FruitAdapter1", "10");
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        //  加载图片，with传入mContext对象，load用来加载图片（参数可以是：URL地址；本地路径；资源id），into将图片设置
        //到具体的ImageView控件中去（Glide会对图片进行压缩，不用过于担心内存溢出）
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
