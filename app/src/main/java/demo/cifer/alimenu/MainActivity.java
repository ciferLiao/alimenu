package demo.cifer.alimenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;

import java.util.ArrayList;
import java.util.List;

import demo.cifer.alimenu.adapter.MenuAdapter;
import demo.cifer.alimenu.adapter.TabAdapter;
import demo.cifer.alimenu.model.MenuItem;
import demo.cifer.alimenu.model.MenuModel;
import demo.cifer.alimenu.model.TabModel;


/**
 * 辅助插件
 * https://github.com/alibaba/vlayout
 */

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    DelegateAdapter delegateAdapter;

    private TabAdapter tabAdapter;

    private MenuAdapter menuAdapter;


    private int size = 1;
    private int lastPosition = 0 ;//tab last select

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

        viewPool.setMaxRecycledViews(0, 20);

        recyclerView.setRecycledViewPool(viewPool);

        delegateAdapter = new DelegateAdapter(layoutManager,false);

        recyclerView.setAdapter(delegateAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if(layoutManager instanceof LinearLayoutManager){
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    int firstPosion = linearLayoutManager.findFirstVisibleItemPosition();
                    int postion = firstPosion -size;
                    if(postion >= 0){
                        if(postion != tabAdapter.getLastPositon()) {
                            if(recyclerView.getScrollState() != 0) {
                                tabAdapter.updateDatas(postion);
                            }
                        }
                    }
                }
            }
        });

        initDatas();
    }


    private void initDatas(){
        List<TabModel> tabModels = new ArrayList<>();
        for(int i = 0 ; i < 8 ; i++){
            TabModel model = new TabModel();
            model.setTitle("tabModel" + i);
            if(i == 0){
                model.setSelect(true);
            } else {
                model.setSelect(false);
            }
            tabModels.add(model);
        }

        tabAdapter = new TabAdapter(this,new StickyLayoutHelper());

        tabAdapter.setOnItemChildClickListener(new TabAdapter.OnItemChildClickListener() {
            @Override
            public void itemClick(Object o, int position) {
                tabAdapter.updateDatas(position);
                if(position >= lastPosition) {
                    if (position == 0) {
                        recyclerView.scrollToPosition(size + position);
                    } else {
                        recyclerView.scrollToPosition(size + 1 + position);
                    }
                } else {
                    if (position <= 1) {
                        recyclerView.scrollToPosition(size + position);
                    } else {
                        recyclerView.scrollToPosition(size -1 + position);
                        recyclerView.scrollToPosition(size + 1 + position);
                    }
                }
                lastPosition = position;
            }
        });

        tabAdapter.set(tabModels);
        List<MenuModel> menuModels = new ArrayList<>();
        for(int i = 0 ; i < 8 ; i++){
            MenuModel model = new MenuModel();
            List<MenuItem> array = new ArrayList<>();
            for(int j = 0 ; j < 10 ; j++){
                MenuItem menuItem = new MenuItem();
                menuItem.setTitle("menuItem" + j);
                menuItem.setUrl("model :" + i + "    item" + j);
                array.add(menuItem);
            }
            model.setMenuItems(array);
            model.setTitle("menuModel : " + i);

            menuModels.add(model);
        }

        menuAdapter = new MenuAdapter(this,new LinearLayoutHelper());
        menuAdapter.set(menuModels);


        delegateAdapter.addAdapter(tabAdapter);
        delegateAdapter.addAdapter(menuAdapter);
    }
}
