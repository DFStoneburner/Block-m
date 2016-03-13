package paddle.blockm.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import paddle.R;
import paddle.blockm.models.BlockLog;
import paddle.blockm.models.BlockedNumber;

/**
 * Created by Daniel on 9/20/2015.
 */
public class BlockedCallAdapter extends RecyclerView.Adapter<BlockedCallAdapter.BlockLogViewHolder> {

    private List<BlockLog> logs;
    private Drawable callBlockedDrawable;

    public BlockedCallAdapter(Context context) {
        logs = new ArrayList<>();

        callBlockedDrawable = ContextCompat.getDrawable(context, R.drawable.ic_close_96dp);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    @Override
    public BlockLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View blockLogView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_blocked_call, parent, false);

        return new BlockLogViewHolder(blockLogView);
    }

    @Override
    public void onBindViewHolder(final BlockedCallAdapter.BlockLogViewHolder holder, int position) {
        BlockLog blockLog = logs.get(position);
        holder.textTitle.setText(blockLog.getBlockedNumber());
        holder.textDesc.setText(blockLog.getDate().toString());
        holder.callImage.setImageDrawable(callBlockedDrawable);

        if (BlockedNumber.find(BlockedNumber.class, "number = ?", blockLog.getBlockedNumber()).size() > 0) {
            holder.actionToggleBlock.setText(R.string.action_unblock);
            holder.blocked = true;
        } else {
            holder.actionToggleBlock.setText(R.string.action_reblock);
            holder.blocked = false;
        }
        holder.actionToggleBlock.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.blocked) {
                            BlockedNumber.deleteAll(BlockedNumber.class, "number = ?", holder.textTitle.getText().toString());
                        } else {
                            BlockedNumber blockedNumber = new BlockedNumber();
                            blockedNumber.setNumber(holder.textTitle.getText().toString());
                            blockedNumber.save();
                        }
                        BlockedCallAdapter.this.notifyDataSetChanged();
                    }
                });
    }

    public void clear() {
        logs.clear();
        notifyDataSetChanged();
    }

    public void update(List<BlockLog> callLogs) {
        logs.addAll(callLogs);
        notifyDataSetChanged();
    }

    public class BlockLogViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.blockImage)
        ImageView callImage;
        @Bind(R.id.blockTitle)
        TextView textTitle;
        @Bind(R.id.blockDescription)
        TextView textDesc;
        @Bind(R.id.blockActionCall)
        TextView actionCall;
        @Bind(R.id.blockActionUnblock)
        TextView actionToggleBlock;
        boolean blocked = true;

        public BlockLogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
