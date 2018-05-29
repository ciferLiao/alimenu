package demo.cifer.alimenu.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.LayoutHelper;

import java.util.List;

import demo.cifer.alimenu.R;
import demo.cifer.alimenu.adapter.annotation.Adapter;
import demo.cifer.alimenu.adapter.base.BaseAdapter;
import demo.cifer.alimenu.adapter.base.BaseSingleDelegateAdapter;
import demo.cifer.alimenu.model.TabModel;
import demo.cifer.alimenu.utils.Utils;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

@Adapter(layout = R.layout.item_tab,holder = TabAdapter.ViewHolder.class,itemViews = 109)
public class TabAdapter extends BaseSingleDelegateAdapter<List<TabModel>,TabAdapter.ViewHolder>{

    private OnItemChildClickListener mChildListener;
    private int selectPostion;
    private int lastPositon;

    public TabAdapter(Context context, LayoutHelper layoutHelper) {
        super(context, layoutHelper);
        lastPositon = 0;
    }

    private TabItemAdapter tabItemAdapter = new TabItemAdapter(getContext());

    @Override
    public void bindViewHolder(ViewHolder holder, final List<TabModel> tabModels) {
        if (tabModels != null || tabModels.size() > 0) {
            if(tabItemAdapter.getItemCount() != 0) {
                holder.recyclerView.scrollToPosition(selectPostion);
            } else {
                tabItemAdapter.set(tabModels);
                holder.recyclerView.setAdapter(tabItemAdapter);
            }
            tabItemAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void itemClick(Object o, int position) {
                    mChildListener.itemClick(tabModels.get(position),position);
                }
            });
        }
    }

    public int getLastPositon(){
        return lastPositon;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.actionbar);
            LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL,false);
            recyclerView.setLayoutManager(manager);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(itemView.getContext(),44));
            itemView.setLayoutParams(params);
        }
    }

    public void updateDatas(int position){
        selectPostion = position;
        List<TabModel> datas = get(0);
        TabModel last = datas.get(lastPositon);
        last.setSelect(false);
        datas.set(lastPositon,last);

        TabModel select = datas.get(selectPostion);
        select.setSelect(true);
        datas.set(selectPostion,select);
        tabItemAdapter.notifyItemChanged(lastPositon,last);
        tabItemAdapter.notifyItemChanged(selectPostion,select);
        lastPositon = selectPostion;
        notifyDataSetChanged();
    }

    public void setOnItemChildClickListener(OnItemChildClickListener listener){
        this.mChildListener = listener;
    }

    public interface OnItemChildClickListener<M>{
        void itemClick(M m,int position);
    }
}
