package com.example.insectmvp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.insectmvp.MainActivity;
import com.example.insectmvp.contract.InsectContract;
import com.example.insectmvp.presenter.GridPresenterImpl;
import com.example.insectmvp.R;
import com.example.insectmvp.adapter.GridAdapter;
import com.example.insectmvp.presenter.ViewHolderClickPresenterImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class GridFragment extends Fragment implements InsectContract.GridView {

    private RecyclerView recyclerView;

    private GridAdapter gridAdapter;

    private InsectContract.GridPresenter presenter;


    private InsectContract.ViewHolderClickPresenter viewHolderPresenter;

    public GridFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_grid, container, false);

        viewHolderPresenter=new ViewHolderClickPresenterImpl(this);

        gridAdapter=new GridAdapter(viewHolderPresenter);

        recyclerView.setAdapter( gridAdapter);

        presenter = new GridPresenterImpl(this);

        //调用GridPresenter的loadGridImages方法，加载网格视图
        presenter.loadGirdImages();
        prepareTransitions();
        postponeEnterTransition();

        return recyclerView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
    }


    @Override
    public void showGirdImages(List<Integer> imageList) {

            //将imageList资源数据传递给GridAdapter
            gridAdapter.setImageList(imageList);
            gridAdapter.notifyDataSetChanged();

    }

    @Override
    public void ItemClicked(View view, int adapterPosition) {

        // Update the position.
        MainActivity.currentPosition = adapterPosition;

        // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
        // instead of fading out with the rest to prevent an overlapping animation of fade and move).
        ((TransitionSet) this.getExitTransition()).excludeTarget(view, true);

        ImageView transitioningView = view.findViewById(R.id.card_image);
        this.getFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true) // Optimize for shared element transition
                .addSharedElement(transitioningView, transitioningView.getTransitionName())
                .replace(R.id.fragment_container, new ImagePagerFragment(), ImagePagerFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onImageLoadCompleted(View view, int adapterPosition) {
        AtomicBoolean enterTransitionStarted=new AtomicBoolean();

        if (MainActivity.currentPosition != adapterPosition) {
            return;
        }
        if (enterTransitionStarted.getAndSet(true)) {
            return;
        }
        this.startPostponedEnterTransition();
    }


    private void scrollToPosition() {
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                recyclerView.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(MainActivity.currentPosition);

                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    recyclerView.post(() -> layoutManager.scrollToPosition(MainActivity.currentPosition));
                }
            }
        });
    }


    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));


        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the ViewHolder for the clicked position.
                        RecyclerView.ViewHolder selectedViewHolder = recyclerView
                                .findViewHolderForAdapterPosition(MainActivity.currentPosition);
                        if (selectedViewHolder == null) {
                            return;
                        }
                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
                    }
                });
    }

}

