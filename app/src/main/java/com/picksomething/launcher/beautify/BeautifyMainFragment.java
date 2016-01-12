package com.picksomething.launcher.beautify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.picksomething.launcher.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BeautifyMainFragment extends Fragment {

    public static final String PAGES_TYPE = "pages_type";
    public static final int TYPE_WALLPAPER = 0;
    public static final int TYPE_THEME = 1;
    public static final int TYPE_FONT = 2;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.text_view)
    TextView textView;

    private int mPageType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageType = getArguments().getInt(PAGES_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_beautify_fragment_list, container, false);
        ButterKnife.bind(this, view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setAdapter(mAdapter);
        textView.setText("第" + mPageType + "页");
        return view;
    }
}
