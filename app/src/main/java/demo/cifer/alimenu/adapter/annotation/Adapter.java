package demo.cifer.alimenu.adapter.annotation;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author CiferLiao
 * @date 2018/5/18
 * @des RecyclerView.Adapter的layoutId,ViewHolder,
 * itemViews 可不填写
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Adapter {
    @LayoutRes int layout();

    Class<? extends RecyclerView.ViewHolder> holder();

    int itemViews();
}
