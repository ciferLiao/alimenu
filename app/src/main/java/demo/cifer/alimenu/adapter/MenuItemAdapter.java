package demo.cifer.alimenu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import demo.cifer.alimenu.R;
import demo.cifer.alimenu.adapter.annotation.Adapter;
import demo.cifer.alimenu.adapter.base.BaseAdapter;
import demo.cifer.alimenu.model.MenuItem;
import demo.cifer.alimenu.utils.Utils;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */
@Adapter(layout = R.layout.item_menu_item,holder = MenuItemAdapter.ViewHolder.class,itemViews = 112)
public class MenuItemAdapter extends BaseAdapter<MenuItem,MenuItemAdapter.ViewHolder>{


    public MenuItemAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, final MenuItem model, int position) {
        holder.title.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"model :" + model.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;

        private TextView title;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(itemView.getContext(),50));
            itemView.setLayoutParams(params);
        }
    }
}
