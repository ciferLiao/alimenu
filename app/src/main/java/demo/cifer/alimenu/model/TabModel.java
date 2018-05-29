package demo.cifer.alimenu.model;

/**
 * @author CiferLiao
 * @date 2018/5/29
 * @des
 */

public class TabModel {

    private String title;
    private int id;
    private boolean isSelect;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
