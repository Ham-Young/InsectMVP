package com.example.insectmvp.presenter;

import android.view.View;

import com.example.insectmvp.contract.InsectContract;

import java.util.concurrent.atomic.AtomicBoolean;

public class ViewHolderClickPresenterImpl implements InsectContract.ViewHolderClickPresenter {

    private InsectContract.GridView gridView;

    public ViewHolderClickPresenterImpl(InsectContract.GridView view) {

        this.gridView=view;

    }


    @Override
    public void onLoadCompleted(View view, int adapterPosition) {

        gridView.onImageLoadCompleted(view, adapterPosition);
    }

    @Override
    public void onItemClicked(View view, int adapterPosition) {

              gridView.ItemClicked(view, adapterPosition);
    }
}
