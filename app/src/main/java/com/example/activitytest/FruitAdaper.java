package com.example.activitytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FruitAdaper extends ArrayAdapter<Fruit> {

    private int resourceId;

    public FruitAdaper(Context context, int textViewResourceID
            , List<Fruit> objects) {    //重写构造方法，参数依次为 ：上下文对象、子项ID、数据
        super(context, textViewResourceID, objects);

        resourceId = textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {  //此方法在每个子项被滚动到中屏幕时会被调用
//        return super.getView(position, convertView, parent);
        Fruit fruit = getItem(position);  //获取当前项的Fruit实例
        View view;  //优化运行 1
        ViewHolder viewHolder;  //优化运行 2
        if (convertView == null) {  //优化运行 1
             view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
             viewHolder = new ViewHolder();  //优化运行 2
             viewHolder.fruitImage = view.findViewById(R.id.fruit_image);  //优化运行 2
             viewHolder.fruitName = view.findViewById(R.id.fruit_name);  //优化运行 2
             view.setTag(viewHolder);  //将viewHolder存储在view中
        }
        else {
            view = convertView;  //优化运行 1
            viewHolder = (ViewHolder) view.getTag();  //重新获取viewHolder，优化运行 2

        }

//        ImageView fruitImage = view.findViewById(R.id.fruit_image);  //优化 2 淘汰
//        TextView fruitName = view.findViewById(R.id.fruit_name);  //优化 2 淘汰
//        fruitImage.setImageResource(fruit.getImageId());  //优化 2 淘汰
//        fruitName.setText(fruit.getName());  //优化 2 淘汰
        viewHolder.fruitImage.setImageResource(fruit.getImageId());  //优化运行 2
        viewHolder.fruitName.setText(fruit.getName());  //优化运行 2
        return view;
    }

    class ViewHolder {  //优化运行 2
        ImageView fruitImage;  //优化运行 2
        TextView fruitName;  //优化运行 2
    }
}
