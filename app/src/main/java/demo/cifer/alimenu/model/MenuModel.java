package demo.cifer.alimenu.model;

import java.util.List;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

public class MenuModel {

    private List<MenuItem> menuItems;

    private String title;

    private String id;

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
