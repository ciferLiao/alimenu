package demo.cifer.alimenu.adapter.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import demo.cifer.alimenu.adapter.annotation.Adapter;

/**
 * @author CiferLiao
 * @date 2018/5/21
 * @des
 */

public abstract class BaseSingleDelegateAdapter <M,VH extends RecyclerView.ViewHolder> extends DelegateAdapter.Adapter<RecyclerView.ViewHolder>{

    private LayoutHelper layoutHelper;

    private Context context;
    public BaseSingleDelegateAdapter(Context context, LayoutHelper layoutHelper){
        this.layoutHelper = layoutHelper;
        this.context = context;
        annotationAdapter();
    }

    private M mData = null;
    private Adapter mAdapterAnnotation;

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
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    /**
     * 获取注解里的 itemViewType，如果没有的话注解的话，生成随机数
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if(mAdapterAnnotation == null ){
            Random random = new Random(100);
            return random.nextInt();
        }
        return mAdapterAnnotation.itemViews();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //反射获取ViewHolder
        if (mAdapterAnnotation != null) {
            return reflectViewHolder(parent);
        }
        return createHolder(parent,viewType);
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindViewHolder((VH) holder, mData);
    }

    @Override
    public int getItemCount() {
        return 1;
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

    /**
     * 绑定viewHolder的数据
     */
    public abstract void bindViewHolder(VH holder, M m);


    public M get(int position) {
        if (position < 0 || position >= 1) {
            throw new IllegalStateException("position必须大于0,且不能大于mData的个数");
        }
        if(mData != null){
            return mData;
        }
        return null;
    }


    /**
     * 设置list为这个list
     */
    public void set(M data) {
        if (data != null) {
            mData = data;
        }
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

    public void startActivity(Class<?> classes,Bundle bundle){
        Intent intent = new Intent(context, classes);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public void startActivityForResult(Class<?> classes,Bundle bundle,int code){
        Intent intent = new Intent(context, classes);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,code);
    }
}
