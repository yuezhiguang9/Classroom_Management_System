package demo.campus_management_system.controller;


import demo.campus_management_system.entity.DTO.ListLogsDTO;
import demo.campus_management_system.service.impl.SuperAdminImpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
public class SuperAdmin {
    @Autowired
    private SuperAdminImpl superAdminImpl;


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

        System.out.println("apply_status=" + apply_status + "\n");
        System.out.println("college_id=" + college_id + "\n");
        System.out.println("building_id=" + building_id + "\n");
        System.out.println("user_name=" + user_name + "\n");
        System.out.println("date_start=" + date_start + "\n");
        System.out.println("date_end=" + date_end + "\n");
        System.out.println("page=" + page + "\n");
        System.out.println("size=" + size + "\n");


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
