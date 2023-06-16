package com.example.insectmvp.presenter;

import com.example.insectmvp.contract.InsectContract;
import com.example.insectmvp.model.ImageDataModel;

import java.util.List;

public class GridPresenterImpl implements InsectContract.GridPresenter {

    private InsectContract.GridView gridView;

    private ImageDataModel imageModel;

    public GridPresenterImpl(InsectContract.GridView view) {

         this.gridView=view;
         this.imageModel=new ImageDataModel();

    }


    @Override
    public void loadGirdImages() {


            //获取图片资源list
            List<Integer> imageList = imageModel.getImageResId();
            //
            gridView.showGirdImages(imageList);

    }


}
