package demo.cifer.alimenu.utils;

import android.content.Context;

import java.util.List;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

public class Utils {

    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
