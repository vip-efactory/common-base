package vip.efactory.common.base.page;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * EPage,意为ejpa的Page，用途是简化jpa的原始分页对象传输到前端！
 */
@Data
public class EPage implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 页面索引，即，当前页
     */
    private long pageIndex;
    /**
     * 记录总数
     */
    private long totalCount;
    /**
     * 每页元素数
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 记录集合
     */
    private List<?> content;

    public EPage() {
    }

    public EPage(Page<?> page) {
        this.pageIndex = page.getNumber();
        this.content = page.getContent();
        this.totalCount = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.pageSize = page.getSize();
    }

    public EPage(long pageIndex, long totalCount, long pageSize, long totalPage, List<?> content) {
        this.pageIndex = pageIndex;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.content = content;
    }

    public static EPage page() {
        return new EPage();
    }

    public static EPage page(Page<?> page) {
        return new EPage(page);
    }

    public static EPage page(long pageIndex, long totalCount, long pageSize, long totalPage, List<?> content) {
        return new EPage(pageIndex, totalCount, pageSize, totalPage, content);
    }

}
