package demo.cifer.alimenu.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import demo.cifer.alimenu.adapter.annotation.Adapter;
import demo.cifer.alimenu.utils.Utils;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

public abstract class BaseAdapter<M,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    private List<M> mDataList;

    private Adapter mAdapterAnnotation;

    public BaseAdapter(Context context){
        this.context = context;
        mDataList = new ArrayList<>();
        annotationAdapter();
    }


    private BaseAdapter.OnItemClickListener mListener;
    /**
     * 获取@Adapter注解
     */
    private void annotationAdapter() {
        Class cls = this.getClass();
        if (cls.isAnnotationPresent(Adapter.class)) {
            mAdapterAnnotation = this.getClass().getAnnotation(Adapter.class);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //反射获取ViewHolder
        if (mAdapterAnnotation != null) {
            return reflectViewHolder(parent);
        }
        return createHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final M m = mDataList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.itemClick(m, position);
                }
            }
        });
        bindViewHolder((VH) holder, m, position);
    }

    public Context getContext(){
        return context;
    }

    /**
     * 反射获得ViewHolder
     */
    @SuppressWarnings("unchecked")
    private VH reflectViewHolder(ViewGroup parent) {
        View v = inflate(mAdapterAnnotation.layout(), parent);
        Class<VH> c = (Class<VH>) mAdapterAnnotation.holder();
        VH holder = null;
        try {
            Constructor<VH> con = c.getConstructor(View.class);
            holder = con.newInstance(v);
        } catch (NoSuchMethodException e) {
            System.err.println("检查ViewHolder类及构造函数是否是public");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Log.i("LB", holder.getClass() + "");
        return holder;
    }

    /**
     * 除头部和底部的ViewHolder的获取
     *
     * @param viewType holder的类型
     */
    protected VH createHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * 获取需要viewHolder的view
     *
     * @param layoutId 布局文件
     */
    protected View inflate(int layoutId, ViewGroup group) {
        LayoutInflater inflater = LayoutInflater.from(group.getContext());
        return inflater.inflate(layoutId, group, false);
    }

    @Override
    public int getItemViewType(int position) {
        if(mAdapterAnnotation == null ){
            Random random = new Random(100);
            return random.nextInt();
        }
        return mAdapterAnnotation.itemViews();
    }

    /**
     * 绑定viewHolder的数据
     */
    public abstract void bindViewHolder(VH holder, M m, int position);

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<M> getData() {
        if (Utils.isEmpty(mDataList)) {
            throw new IllegalStateException("mData is empty(数据为空)");
        }
        return mDataList;
    }

    public M get(int position) {
        if (position < 0 || position > (mDataList.size() - 1)) {
            throw new IllegalStateException("position必须大于0,且不能大于mData的个数");
        }
        if (Utils.isEmpty(mDataList)) {
            return null;
        }
        return mDataList.get(position);
    }
    /**
     * 设置list为这个list
     */
    public void set(List<M> data) {
        if (data != null) {
            mDataList = data;
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * list中添加更多的数据
     */
    public void add(List<M> data) {
        if (mDataList == null) {
            return;
        }
        mDataList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p =
                    (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    /**
     * 设置每行点击事件的监听
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * item点击事件
     */
    public interface OnItemClickListener<M> {
        /**
         * @param m item下的实体
         * @param position item所在的位置
         */
        void itemClick(M m, int position);
    }
}
