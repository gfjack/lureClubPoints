package com.lureclub.points.entity.common;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * 分页响应VO
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "分页信息")
public class PageVo<T> {

    @Schema(description = "数据列表")
    private List<T> content;

    @Schema(description = "当前页码（从0开始）", example = "0")
    private Integer pageNumber;

    @Schema(description = "每页大小", example = "10")
    private Integer pageSize;

    @Schema(description = "总页数", example = "5")
    private Integer totalPages;

    @Schema(description = "总记录数", example = "50")
    private Long totalElements;

    @Schema(description = "是否是第一页")
    private Boolean first;

    @Schema(description = "是否是最后一页")
    private Boolean last;

    // 构造函数
    public PageVo() {}

    /**
     * 从Spring Data Page对象创建PageVo
     */
    public static <T> PageVo<T> of(Page<T> page) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setContent(page.getContent());
        pageVo.setPageNumber(page.getNumber());
        pageVo.setPageSize(page.getSize());
        pageVo.setTotalPages(page.getTotalPages());
        pageVo.setTotalElements(page.getTotalElements());
        pageVo.setFirst(page.isFirst());
        pageVo.setLast(page.isLast());
        return pageVo;
    }

    /**
     * 手动创建PageVo
     */
    public static <T> PageVo<T> of(List<T> content, int pageNumber, int pageSize, long totalElements) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setContent(content);
        pageVo.setPageNumber(pageNumber);
        pageVo.setPageSize(pageSize);
        pageVo.setTotalElements(totalElements);
        pageVo.setTotalPages((int) Math.ceil((double) totalElements / pageSize));
        pageVo.setFirst(pageNumber == 0);
        pageVo.setLast(pageNumber >= pageVo.getTotalPages() - 1);
        return pageVo;
    }

    // Getter和Setter方法
    public List<T> getContent() { return content; }
    public void setContent(List<T> content) { this.content = content; }

    public Integer getPageNumber() { return pageNumber; }
    public void setPageNumber(Integer pageNumber) { this.pageNumber = pageNumber; }

    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Long getTotalElements() { return totalElements; }
    public void setTotalElements(Long totalElements) { this.totalElements = totalElements; }

    public Boolean getFirst() { return first; }
    public void setFirst(Boolean first) { this.first = first; }

    public Boolean getLast() { return last; }
    public void setLast(Boolean last) { this.last = last; }
}