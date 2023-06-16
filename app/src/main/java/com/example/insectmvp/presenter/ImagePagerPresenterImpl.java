package com.example.insectmvp.presenter;

import androidx.fragment.app.FragmentManager;

import com.example.insectmvp.contract.InsectContract;
import com.example.insectmvp.model.ImageDataModel;

import java.util.List;

public class ImagePagerPresenterImpl implements InsectContract.ImagePagerPresenter {

    private InsectContract.ImagePagerView imagePagerView;
    private ImageDataModel imageModel;


    public ImagePagerPresenterImpl() {

        this.imageModel=new ImageDataModel();

    }

    public ImagePagerPresenterImpl(InsectContract.ImagePagerView imagePagerView) {

        this.imagePagerView=imagePagerView;

    }
    @Override
    public List<Integer> getImageRes() {

        return  imageModel.getImageResId();

    }

    @Override
    public FragmentManager onChildFm() {
      return  imagePagerView.getChildFm();
    }
}
