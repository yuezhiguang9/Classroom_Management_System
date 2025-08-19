package demo.campus_management_system.entity.DTO;

public class DeleteUsersDTO {

    //要删除用户的账号
    private String account;
    //用户类型
    private String user_type;

    public String getAccount() {
        return account;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
