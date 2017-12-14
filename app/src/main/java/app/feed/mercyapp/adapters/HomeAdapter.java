package app.feed.mercyapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import app.feed.mercyapp.About;
import app.feed.mercyapp.AdminActivity;
import app.feed.mercyapp.R;
import app.feed.mercyapp.models.responses.FeedResponse;
import app.feed.mercyapp.ui.SingleFeedActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context ctx;

    protected int lastPosition = -1;
    private List<FeedResponse> list;

    public HomeAdapter(Context ctx, List<FeedResponse> body) {
        this.ctx = ctx;
        this.list = body;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rows, null));
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(ctx, (position > lastPosition) ?
                R.anim.up_from_bottom : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);

        final FeedResponse feed = list.get(position);

        holder.tvTitle.setText(feed.getTittle());
        holder.tvDesc.setText(feed.getFeed());
        holder.tvTime.setText(feed.getDate());
        holder.tvTitle.setText(feed.getTittle());

        new android.os.Handler().post(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(ctx)
                        .load(feed.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                        .centerCrop()
                        .crossFade()
                        .into(holder.ivLogo);
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, SingleFeedActivity.class);
                intent.putExtra("feed", feed);
                ctx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.imageView5)
        ImageView imageView5;
        @BindView(R.id.tv_time)
        TextView tvTime;

        private View mView;
        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mView = itemView;
        }
    }

}
