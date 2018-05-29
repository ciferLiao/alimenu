package demo.cifer.alimenu.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;

import demo.cifer.alimenu.R;
import demo.cifer.alimenu.adapter.annotation.Adapter;
import demo.cifer.alimenu.adapter.base.BaseDelegateAdapter;
import demo.cifer.alimenu.model.MenuModel;
import demo.cifer.alimenu.utils.FullyGridLayoutManager;
import demo.cifer.alimenu.utils.Utils;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

@Adapter(layout = R.layout.item_menu ,holder = MenuAdapter.ViewHolder.class,itemViews = 111)
public class MenuAdapter extends BaseDelegateAdapter<MenuModel,MenuAdapter.ViewHolder>{


    public MenuAdapter(Context context, LayoutHelper layoutHelper) {
        super(context, layoutHelper);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, MenuModel menuModel, int position) {
        holder.title.setText(menuModel.getTitle());
        if(holder.menuItemAdapter == null){
            holder.menuItemAdapter = new MenuItemAdapter(getContext());

            if(holder.menuBar.getAdapter() == null){
                holder.menuBar.setAdapter(holder.menuItemAdapter);
            }
        }
        holder.menuItemAdapter.set(menuModel.getMenuItems());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView menuBar;
        private TextView title;
        private MenuItemAdapter menuItemAdapter;

        public ViewHolder(View itemView) {
            super(itemView);

            menuBar = (RecyclerView) itemView.findViewById(R.id.menu_list);
            title = (TextView) itemView.findViewById(R.id.title);

            menuBar.setLayoutManager(new FullyGridLayoutManager(itemView.getContext(),2));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            itemView.setLayoutParams(params);
        }
    }
}
