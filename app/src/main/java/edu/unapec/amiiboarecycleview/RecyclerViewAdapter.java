package edu.unapec.amiiboarecycleview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import edu.unapec.amiiboarecycleview.dtos.AmiiboDto;
import edu.unapec.amiiboarecycleview.dtos.AmiiboListDto;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;
    private List<AmiiboDto> amiiboListDto;

    public RecyclerViewAdapter(Context context, AmiiboListDto ami) {
        ami.getAmiibos().forEach(x -> {
            mImageNames.add(x.getName());
            mImages.add((x.getImage()));
            amiiboListDto = ami.getAmiibos();
        });

        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        AmiiboDto currentAmiibo = amiiboListDto.get(position);
        Glide.with(mContext)
                .load(currentAmiibo.getImage())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.fitCenterTransform())
                .into(holder.image);

        holder.imageName.setText(currentAmiibo.getName());

        holder.parentLayout.setOnClickListener(view -> {
            Log.d(TAG, "onClick: clicked on: " +currentAmiibo.getName());

            Toast.makeText(mContext, currentAmiibo.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, ItemDetailActivity.class);

            intent.putExtra("currentAmiibo", currentAmiibo);
            intent.putExtra("image_url", mImages.get(position));
            intent.putExtra("image_name", mImageNames.get(position));
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}