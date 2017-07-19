package cn.xiong.showmessage.adpter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.xiong.showmessage.R;
import cn.xiong.showmessage.entity.General;
import cn.xiong.showmessage.utils.GlideCircleTransform;

/**
 * Created by Administrator on 2017/7/18.
 */

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {

    private Activity context;
    private List<General> generalList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView generalHead;
        TextView generalTime,generalBehave,generalSpaceName;
        View topDash;

        public ViewHolder(View itemView) {
            super(itemView);
            generalHead = (ImageView) itemView.findViewById(R.id.general_head);
            generalBehave = (TextView) itemView.findViewById(R.id.general_behave);
            generalSpaceName = (TextView) itemView.findViewById(R.id.general_space_name);
            generalTime = (TextView) itemView.findViewById(R.id.general_time);
            topDash = itemView.findViewById(R.id.top_dash);
        }
    }

    public GeneralAdapter(Activity context, List<General> generalList) {
        this.context = context;
        this.generalList = generalList;
    }

    public void setGeneralList(List<General> generalList) {
        this.generalList = generalList;
        for (int i = 0;i < generalList.size();i++){
            notifyItemChanged(i);
        }
    }

    @Override
    public GeneralAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left,parent,false);

        final ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GeneralAdapter.ViewHolder holder, int position) {

        General general = generalList.get(position);
        holder.generalTime.setText(general.getBehaviorTime());
        holder.generalSpaceName.setText(general.getSpaceName());
        holder.generalBehave.setText(general.getNickname()+general.getBehavior()+general.getBehaviorRelationName());
        Glide
                .with(context)
                .load(general.getHeadImgUrl())
                .centerCrop()
                .error(R.mipmap.ic_launcher)
                .transform(new GlideCircleTransform(context))
                .animate(R.anim.little_to_large)
                .into(holder.generalHead);

        if(position == 0){
            holder.topDash.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return generalList.size();
    }
}
