package cn.com.techvision.bootwizard.util.BaseList;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

import cn.com.techvision.bootwizard.R;

public class BaseFragment extends Fragment implements BaseAdapter.OnItemAdapterClickListener{

    private List<ItemBean> list;
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager ;
    private BaseAdapter baseAdapter;
    private Button preBtn,nextBtn;
    private int Display_Count = 8;
    private int Current_Position = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        recyclerView = view.findViewById(R.id.recycler_base);
        preBtn = view.findViewById(R.id.pre_btn);
        nextBtn = view.findViewById(R.id.next_btn);
        initFragmentDate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void initFragmentDate(){

        if (list == null){
            list = new ArrayList<>();
        }

        baseAdapter = new BaseAdapter(list,getActivity());
        baseAdapter.setOnItemAdapterClickListener(this);
        recyclerView.setAdapter(baseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL){

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(0, 0, 0, 2);
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()){

            @Override
            public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
                return 0;// 禁止滑动
                //return super.scrollVerticallyBy(dy, recycler, state);
            }

            @Override
            public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {

                int count = state.getItemCount();
                if (count > 0) {
                    View view = recycler.getViewForPosition(0);
                    if (view != null) {
                        measureChild(view, widthSpec, heightSpec);
                        int measuredHeight = view.getMeasuredHeight();
                        setMeasuredDimension(widthSpec,(measuredHeight+2)*Display_Count);
                        return;
                    }
                }
                super.onMeasure(recycler, state, widthSpec, heightSpec);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        preBtn.setOnClickListener(BtnOnclick);
        nextBtn.setOnClickListener(BtnOnclick);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public List<ItemBean> getList() {
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

    public void refreshAdapter() {
        baseAdapter.notifyDataSetChanged();
    }

    //BaseAdapter的回调
    @Override
    public void onItemClick(View view, int position) {
        onItemFragmentClickListener.onItemClick(view,position);//BaseFragment的监听
    }

    //BaseFragment的监听
    public interface OnItemFragmentClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemFragmentClickListener onItemFragmentClickListener;

    public void setOnItemFragmentClickListener(OnItemFragmentClickListener onItemFragmentClickListener){
        this.onItemFragmentClickListener = onItemFragmentClickListener;
    }

    View.OnClickListener BtnOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.pre_btn:
                    Log.d("lrh", "pre_btn " +Current_Position );
                    Current_Position -= Display_Count;
                    if (Current_Position < 0){
                        Current_Position = 0;
                    }
                    Log.d("lrh", "pre_btn  scrollToPosition" +Current_Position
                            + " | "+list.get(Current_Position).getItemTitle());
                    recyclerView.scrollToPosition(Current_Position);
                    mLayoutManager.scrollToPositionWithOffset(Current_Position,0);
                    break;
                case R.id.next_btn:
                    Log.d("lrh", "Current_Position " +Current_Position );
                    Current_Position += Display_Count;
                    if (Current_Position > list.size()-1){
                        Current_Position =list.size()-1;
                    }
                    Log.d("lrh", "Current_Position scrollToPosition " +Current_Position
                            + " | "+list.get(Current_Position).getItemTitle() );
                    recyclerView.scrollToPosition(Current_Position);
                    mLayoutManager.scrollToPositionWithOffset(Current_Position,0);
                    break;
            }
        }
    };
}
