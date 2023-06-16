package com.example.insectmvp.contract;



import android.view.View;

import androidx.fragment.app.FragmentManager;

import java.util.List;

public interface InsectContract {


    interface GridView {

        //显示网格视图
        void showGirdImages(List<Integer> imageList);

        //点击单个image视图
        void ItemClicked(View view, int adapterPosition);


        //图片加载完成后执行方法
        void onImageLoadCompleted(View view, int adapterPosition);


    }


    interface ImagePagerView {

         // 获取子 Fragment 管理器
        FragmentManager getChildFm();


    }

    interface GridPresenter {

        //加载网格视图回调
        void loadGirdImages();


    }

    interface ViewHolderClickPresenter {
        //图片加载完成后执行方法回调
        void onLoadCompleted(View view, int adapterPosition);
    //点击网格视图中的单个视图回调
        void onItemClicked(View view, int adapterPosition);
    }


interface ImagePagerPresenter{

        //获取图片资源list
        List<Integer>  getImageRes();

    // 获取子 Fragment 管理器回调方法
    FragmentManager onChildFm();


}

}
