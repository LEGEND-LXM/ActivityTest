package com.example.table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_content_frag, container, false);  //加载布局
        return view;
    }

    public void refresh(String newsTitle, String newsContent) {
        View visibilityLayout = getView().findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = getView().findViewById(R.id.news_title);
        TextView newsContentText = getView().findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);  //刷新新闻标题
        newsContentText.setText(newsContent);  //刷新新闻内容
    }
}
