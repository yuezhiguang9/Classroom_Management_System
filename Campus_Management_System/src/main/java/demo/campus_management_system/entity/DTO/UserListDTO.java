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

    //分页信息
    private Integer page;

    //每页显示的数量
    private Integer size;

    //总记录数
    private Long total;


}
