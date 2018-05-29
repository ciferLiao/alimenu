package demo.cifer.alimenu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import demo.cifer.alimenu.R;
import demo.cifer.alimenu.adapter.annotation.Adapter;
import demo.cifer.alimenu.adapter.base.BaseAdapter;
import demo.cifer.alimenu.model.TabModel;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

@Adapter(layout = R.layout.tab_item, holder = TabItemAdapter.ViewHolder.class,itemViews = 108)
public class TabItemAdapter extends BaseAdapter<TabModel,TabItemAdapter.ViewHolder>{


    public TabItemAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, TabModel model, int position) {
        holder.title.setText(model.getTitle());

        if(model.isSelect()){
            holder.title.setTextColor(ContextCompat.getColor(getContext(),R.color.c_FD1A84));
            holder.line.setVisibility(View.VISIBLE);
        } else {
            holder.line.setVisibility(View.GONE);
            holder.title.setTextColor(ContextCompat.getColor(getContext(),R.color.c_333));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private View line;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            line = itemView.findViewById(R.id.title_bar);
        }
    }
}
