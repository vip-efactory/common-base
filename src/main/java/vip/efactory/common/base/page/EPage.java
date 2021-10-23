package vip.efactory.common.base.page;

import lombok.Data;
import vip.efactory.common.base.utils.MapUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * EPage,意为ejpa的Page，用途是简化jpa的原始分页对象传输到前端！
 * @author dusuanyun
 */
@Data
public class EPage implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String JPA_PAGE_PACKAGE = "org.springframework.data.domain";
    private static final String MBP_PAGE_PACKAGE = "com.baomidou.mybatisplus.extension.plugins.pagination";

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

    /**
     * @param page 为jpa或者mybatis plus的分页对象.
     */
    public EPage(Object page) {
        // 将对象转换为Map对象
        Map<String, Object> paramMap = MapUtil.objectToMap2(page);
        // 根据包名来探测是jpa的分页对象或者是Mybatis的分页对象
        String packageName = page.getClass().getPackage().getName();
        if (packageName.equalsIgnoreCase(JPA_PAGE_PACKAGE)) {
            // Spring Data JPA的分页对象
            this.pageIndex = (int) paramMap.get("number");
            this.pageSize = (int) paramMap.get("size");
            this.totalPage = (int) paramMap.get("totalPages");
            this.totalCount = (long) paramMap.get("totalElements");
            this.content = (List<?>) paramMap.get("content");
        } else if (packageName.equalsIgnoreCase(MBP_PAGE_PACKAGE)) {
            // Mybatis Plus 的分页对象
            this.pageIndex = (long) paramMap.get("current");
            this.totalCount = (long) paramMap.get("total");
            this.pageSize = (long) paramMap.get("size");
            // 计算总页数
            if (this.pageSize > 0) {
                long pages = this.totalCount / this.pageSize;
                if (this.totalCount % this.pageSize != 0) {
                    pages++;
                }
                this.totalPage = pages;
            }
            this.content = (List<?>) paramMap.get("records");
        } else {
            // 未知的对象按照当前对象属性来处理
            this.pageIndex = (long) paramMap.get("pageIndex");
            this.totalCount = (long) paramMap.get("totalCount");
            this.totalPage = (long) paramMap.get("totalPage");
            this.pageSize = (long) paramMap.get("pageSize");
            this.content = (List<?>) paramMap.get("content");
        }
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

    public static EPage page(Object page) {
        return new EPage(page);
    }

    public static EPage page(long pageIndex, long totalCount, long pageSize, long totalPage, List<?> content) {
        return new EPage(pageIndex, totalCount, pageSize, totalPage, content);
    }
}
