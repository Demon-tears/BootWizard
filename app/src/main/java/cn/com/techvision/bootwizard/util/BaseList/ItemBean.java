package cn.com.techvision.bootwizard.util.BaseList;

public class ItemBean {

    public int itemImageResId;//图像资源ID
    public String itemTitle;//标题
    public String itemContent;//内容

    public ItemBean(int itemImageResId, String itemTitle, String itemContent) {
        this.itemImageResId = itemImageResId;
        this.itemTitle = itemTitle;
        this.itemContent = itemContent;
    }

    public ItemBean() {
    }

    public int getItemImageResId() {
        return itemImageResId;
    }

    public void setItemImageResId(int itemImageResId) {
        this.itemImageResId = itemImageResId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }
}
