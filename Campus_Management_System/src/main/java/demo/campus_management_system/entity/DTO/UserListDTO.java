package demo.campus_management_system.entity.DTO;

import demo.campus_management_system.entity.VO.UserListVO;
import lombok.Data;

import java.util.List;

@Data
public class UserListDTO {
    //总用户数
    private Integer totalUsers;

    //活跃用户
    private Integer activeUsers;

    //用户列表
    private List<UserListVO> userListVO;


}
