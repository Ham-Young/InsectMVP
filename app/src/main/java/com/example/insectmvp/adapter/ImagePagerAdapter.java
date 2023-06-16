/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.insectmvp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.insectmvp.contract.InsectContract;
import com.example.insectmvp.fragment.ImageFragment;
import com.example.insectmvp.presenter.ImagePagerPresenterImpl;

import java.util.List;


public class ImagePagerAdapter extends FragmentStatePagerAdapter {

  private InsectContract.ImagePagerPresenter  presenter;


  private List<Integer> imageResList;


  public ImagePagerAdapter(InsectContract.ImagePagerPresenter presenter) {


    // Note: Initialize with the child fragment manager.
    super(presenter.onChildFm(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
  }

  @Override
  public int getCount() {

    presenter=new ImagePagerPresenterImpl();


    imageResList=presenter.getImageRes();

    return imageResList.size();

  }

  @NonNull
  @Override
  public Fragment getItem(int position) {

    return ImageFragment.newInstance(presenter.getImageRes().get(position));
  }
}
