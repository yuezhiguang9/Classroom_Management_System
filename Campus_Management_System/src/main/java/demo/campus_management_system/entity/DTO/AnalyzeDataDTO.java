package demo.campus_management_system.entity.DTO;

public class AnalyzeDataDTO {
    // 用户相关
    private Integer total_users; // 总用户数（users表）
    private Integer active_users; // 活跃用户数（login表近30天）

    // 预约相关
    private Integer total_applies; // 总预约数（apply_info表）
    private String approval_rate; // 成功率（apply_info表，已批准/总申请）

    // 教室相关
    private String classroom_usage_rate; // 使用率（classroom_resource表，已预订/总资源）
    private String room_type; // 教室类型（classroom表）
    private Integer room_type_count; // 该类型教室数量（classroom表）

    // 时段相关
    private String period; // 节次（classroom_resource表res_period）
    private Integer usage_count; // 该时段使用次数

    // 详情字段
    private String apply_id; // 预约ID（apply_info表）
    private String user_name; // 申请人姓名（users表）
    private String building_name; // 教学楼名称（building表）
    private String room_num; // 教室号（classroom表）
    private String book_time; // 预约时间（apply_info表apply_book_time）
    private String apply_status; // 申请状态（apply_info表，值为：未审核/已批准/已拒绝）

    // 分页相关
    private Integer total; // 记录总数
    private Integer page; // 当前页码
    private Integer size; // 每页条数

    public Integer getTotal_users() {
        return total_users;
    }

    public void setTotal_users(Integer total_users) {
        this.total_users = total_users;
    }

    public Integer getActive_users() {
        return active_users;
    }

    public void setActive_users(Integer active_users) {
        this.active_users = active_users;
    }

    public Integer getTotal_applies() {
        return total_applies;
    }

    public void setTotal_applies(Integer total_applies) {
        this.total_applies = total_applies;
    }

    public String getApproval_rate() {
        return approval_rate;
    }

    public void setApproval_rate(String approval_rate) {
        this.approval_rate = approval_rate;
    }

    public String getClassroom_usage_rate() {
        return classroom_usage_rate;
    }

    public void setClassroom_usage_rate(String classroom_usage_rate) {
        this.classroom_usage_rate = classroom_usage_rate;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public Integer getRoom_type_count() {
        return room_type_count;
    }

    public void setRoom_type_count(Integer room_type_count) {
        this.room_type_count = room_type_count;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getUsage_count() {
        return usage_count;
    }

    public void setUsage_count(Integer usage_count) {
        this.usage_count = usage_count;
    }

    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public String getApply_status() {
        return apply_status;
    }

    public void setApply_status(String apply_status) {
        this.apply_status = apply_status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
}