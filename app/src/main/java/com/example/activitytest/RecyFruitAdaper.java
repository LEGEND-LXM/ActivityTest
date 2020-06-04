package com.example.activitytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyFruitAdaper extends RecyclerView.Adapter<RecyFruitAdaper.ViewHolder> {

    private List<Fruit> mFruitList;

    static class ViewHolder extends RecyclerView.ViewHolder {  //定义一个内部类，继承RecyclerView.ViewHolder
        View fruitView;  ////子项View全局变量定义，用于设置点击事件
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view) {  //view通常是RecyclerView子项的最外层布局
            super(view);
            fruitView = view;  //子项View赋值
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.fruit_name);
        }
    }

    public RecyFruitAdaper(List<Fruit> fruitList) {  //把要展示的数据源传出去
        mFruitList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  //创建ViewHolder实例
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent
                , false);  //加载布局，第一个参数为布局文件
//        ViewHolder holder = new ViewHolder(view);  //此处创建的实例传递到了ViewHolder构造函数中,点击事件淘汰

        final ViewHolder holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {  //设置子项最外层的点击事件
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked view" +fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.fruitImage.setOnClickListener(new View.OnClickListener() {  //设置子项图片视图的点击事件
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                Toast.makeText(v.getContext(), "you clicked view" +fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {   //用于对 RecyclerView 子项的数据进行赋值，会在每个子项滚动到屏幕中是被调用
        Fruit fruit = mFruitList.get(position);  //获取子项实例
        holder.fruitImage.setImageResource(fruit.getImageId());  //设置数据
        holder.fruitName.setText(fruit.getName());  //设置数据
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();  //获取RecyclerView有多少个子项（长度）
    }
}
