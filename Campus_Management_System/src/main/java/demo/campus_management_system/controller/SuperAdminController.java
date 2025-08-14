package demo.campus_management_system.controller;


import demo.campus_management_system.entity.DTO.ListLogsDTO;
import demo.campus_management_system.entity.DTO.UpdateUsersDTO;
import demo.campus_management_system.service.impl.SuperAdminImpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
public class SuperAdminController {
    @Autowired
    private SuperAdminImpl superAdminImpl;


    //更新用户数据
    @PostMapping("updateUsers")
    public ResultDTO<Boolean> updateUsers(@RequestHeader(value = "Authorization") String token, @RequestBody UpdateUsersDTO updateUsersDTO) {
        try {

            //打印测试
//            System.out.println(JwtUtil.getUserAccountToken(token) + "----" + JwtUtil.getUserPasswordToken(token));
//            System.out.println("usersDTO=" + "\n" + updateUsersDTO.getTotal());
            String account = JwtUtil.getUserAccountToken(token);
//            System.out.println("account=" + account + "---" + "password=" + password);

            //先判断token正确性
            if (account == null || "error".equals(account)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            return superAdminImpl.updateUsers(token, updateUsersDTO);

        } catch (Exception e) {
            System.out.println("e=" + e);
            return ResultDTO.fail(500, e.toString());
        }
    }


    //日志显示
    @GetMapping("listLogs")
    public ResultDTO<List<ListLogsDTO>> listUsers(
            @RequestHeader(value = "Authorization") String token,//前端传过来的token
            @RequestParam(required = false) String apply_status,   //申请状态
            @RequestParam(required = false) String college_id,     //学院id
            @RequestParam(required = false) String building_id,    //楼栋名称
            @RequestParam(required = false) String user_name,  //申请人姓名
            @RequestParam(required = false) String date_start, //开始日期
            @RequestParam(required = false) String date_end,   //结束日期
            @RequestParam(defaultValue = "1") Integer page,  //页码
            @RequestParam(defaultValue = "10") Integer size)  //每页条数
    {
        List<ListLogsDTO> result = new ArrayList<>();
        //jwt认证
        if (JwtUtil.getUserAccountToken(token).equals("error")) {
            //认证不通过就返回错误码
            return ResultDTO.fail(401, "未登录或Token失效");
        } else {
//        认证通过就查找数据
            ResultDTO<List<ListLogsDTO>> data = superAdminImpl.ListLogs(apply_status,
                    college_id,
                    building_id,
                    user_name,
                    date_start,
                    date_end,
                    page,
                    size
            );
            //返回数据
            return data;
        }
    }


}
