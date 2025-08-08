package demo.campus_management_system.entity.DTO;

public class AddUsersDTO {
    // 账号（主键，所有用户类型必填）
    private String account;
    // 密码（所有用户类型必填）
    private String password;
    // 姓名（普通用户、教学秘书必填）
    private String name;
    // 联系电话（所有用户类型必填）
    private String phone;
    // 所属学院ID（普通用户、教学秘书必填）
    private String college_id;
    // 所在楼栋ID（教室管理员必填）
    private String building_id;
    // 用户类型（必填：user/teach_sec/class_mgr）
    private String user_type;

    // getter和setter方法
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCollege_id() { return college_id; }
    public void setCollege_id(String college_id) { this.college_id = college_id; }
    public String getBuilding_id() { return building_id; }
    public void setBuilding_id(String building_id) { this.building_id = building_id; }
    public String getUser_type() { return user_type; }
    public void setUser_type(String user_type) { this.user_type = user_type; }
}