package paddle.blockm.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import paddle.R;
import paddle.blockm.models.BlockedNumber;

/**
 * Created by Daniel on 9/20/2015.
 */
public class BlacklistAdapter extends RecyclerView.Adapter<BlacklistAdapter.BlockLogViewHolder> {

    private List<BlockedNumber> blacklist;

    public BlacklistAdapter(Context context) {
        blacklist = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return blacklist.size();
    }

    @Override
    public BlockLogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View blockLogView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_blocked_number, parent, false);

        return new BlockLogViewHolder(blockLogView);
    }

    @Override
    public void onBindViewHolder(final BlacklistAdapter.BlockLogViewHolder holder, int position) {
        BlockedNumber blockedNumber = blacklist.get(position);
        holder.textTitle.setText(blockedNumber.getNumber());
    }

    public void clear() {
        blacklist.clear();
        notifyDataSetChanged();
    }

    public void update() {
        this.blacklist.addAll(BlockedNumber.listAll(BlockedNumber.class));
        notifyDataSetChanged();
    }

    public class BlockLogViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.list_title)
        TextView textTitle;

        public BlockLogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
