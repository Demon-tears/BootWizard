package cn.com.techvision.bootwizard.util.BaseList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.techvision.bootwizard.R;

public class BaseAdapter extends RecyclerView.Adapter<BaseAdapter.MyViewHolder>{

    private List<ItemBean> list;
    private int TYPE ;

    Context context;

    public static int SHOW_ONLY_TEXT = 1;
    public static int SHOW_TEXT_AND_IMAGE = 2;

    public BaseAdapter(List<ItemBean> list , Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View itemView = View.inflate(context,R.layout.list_item, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        ItemBean itemBean =  list.get(position);
        viewHolder.imageView.setImageResource(itemBean.itemImageResId);
        viewHolder.title.setText(itemBean.itemTitle);
        viewHolder.content.setText(itemBean.itemContent);

        if (TYPE == SHOW_ONLY_TEXT){
            viewHolder.imageView.setVisibility(View.GONE);
        }else if (TYPE == SHOW_TEXT_AND_IMAGE){
            viewHolder.imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_image);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view,getLayoutPosition());//真正获取item监听的的地方
                }
            });
        }
    }


    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    //BaseAdapter的监听
    public interface OnItemAdapterClickListener{
        public void onItemClick(View view ,int position);
    }

    private OnItemAdapterClickListener onItemClickListener;

    public void setOnItemAdapterClickListener(OnItemAdapterClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

}
