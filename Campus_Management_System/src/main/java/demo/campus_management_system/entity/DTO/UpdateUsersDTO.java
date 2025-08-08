package demo.campus_management_system.entity.DTO;

public class UpdateUsersDTO {

    private String account;

    private String name;

    private String password;

    private String phone;
    private String college_id;

    private String building_id;

    //测试时必填！
    private String user_type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getTotal() {
        return "account=" + account + "\n" +
                "name=" + name + "\n" +
                "password=" + password + "\n" +
                "phone=" + phone + "\n" +
                "college_id=" + college_id + "\n" +
                "building_id=" + building_id + "\n" +
                "user_type=" + user_type;
    }


}
