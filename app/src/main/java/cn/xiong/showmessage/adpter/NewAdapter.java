package cn.xiong.showmessage.adpter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.xiong.showmessage.R;
import cn.xiong.showmessage.entity.New;
import cn.xiong.showmessage.utils.GlideCircleTransform;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {

    private List<New> newList;
    private Activity context;


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newImage;
        TextView newName;

        public ViewHolder(View itemView) {
            super(itemView);
            newImage = (ImageView) itemView.findViewById(R.id.new_image);
            newName = (TextView) itemView.findViewById(R.id.new_name);
        }
    }

    public NewAdapter(List<New> newList, Activity context) {
        this.newList = newList;
        this.context = context;
    }

    public void setNewList(List<New> newList) {
        this.newList = newList;
        for (int i = 0;i<newList.size();i++){
            notifyItemChanged(i);
        }
    }

    @Override
    public NewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new,parent,false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewAdapter.ViewHolder holder, int position) {
        New person = newList.get(position);

        Glide
                .with(context)
                .load(person.getHeadImgUrl())
                .centerCrop()
                .error(R.drawable.default_small)
                .transform(new GlideCircleTransform(context))
                .animate(R.anim.little_to_large)
                .into(holder.newImage);

        holder.newName.setText(person.getNickname());

    }

    @Override
    public int getItemCount() {
        return newList.size();
    }
}
