package demo.campus_management_system.entity.DTO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.VO.ListLogsVO;

public class ListLogsDTO {

    //全校今日待审核数
    private Integer today_pending;

    //全校本周通过数
    private Integer week_approved;

    //全校本周驳回数
    private Integer week_rejected;

    //列表总记录数（总页数 = 总记录数 ÷ 每页条数）
    private Long total;

    //当前页码数
    private Integer page;

    //每页条数
    private Integer size;

    //需要分页处理的数据
    private Page<ListLogsVO> recordsPage;


    public Integer getToday_pending() {
        return today_pending;
    }

    public void setToday_pending(Integer today_pending) {
        this.today_pending = today_pending;
    }

    public Integer getWeek_approved() {
        return week_approved;
    }

    public void setWeek_approved(Integer week_approved) {
        this.week_approved = week_approved;
    }

    public Integer getWeek_rejected() {
        return week_rejected;
    }

    public void setWeek_rejected(Integer week_rejected) {
        this.week_rejected = week_rejected;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Page<ListLogsVO> getRecordsPage() {
        return recordsPage;
    }

    public void setRecordsPage(Page<ListLogsVO> recordsPage) {
        this.recordsPage = recordsPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
