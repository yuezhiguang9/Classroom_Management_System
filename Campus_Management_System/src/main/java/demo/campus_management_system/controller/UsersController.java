package demo.campus_management_system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.DTO.myReservationsDTO;
import demo.campus_management_system.service.impl.UserServiceImpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.entity.DTO.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user")
public class UsersController {
    @Autowired
    UserServiceImpl userService;


    @GetMapping("selectClassroom")
    public ResultDTO<Page<SelectClassroomDTO>> selectClassroom(
            @RequestHeader(value = "Authorization") String token,   //token
            @RequestParam(required = false) String building_id,     //楼栋id
            @RequestParam(required = false) Integer capacity,       //容量
            @RequestParam(required = false) String room_type,       //教室类型
            @RequestParam(required = false) String room_status,     //教室状态
            @RequestParam(required = false) String res_week,        //周次
            @RequestParam(required = false) String res_day_of_week,    //星期
            @RequestParam(required = false) String period,      //节次
            @RequestParam(required = false) String date,      //使用时间
            @RequestParam(defaultValue = "1") Integer page,   //当前页码
            @RequestParam(defaultValue = "10") Integer size)   //每页条数
    {
        //验证token
        String account = JwtUtil.getUserAccountToken(token);
        if (account == null || "error".equals(account)) {
            return ResultDTO.fail(401, "未登录或Token失效");
        } else {
            return userService.selectClassroom(
                    building_id,
                    capacity,
                    room_type,
                    room_status,
                    res_week,
                    res_day_of_week,
                    period,
                    date,
                    page,
                    size);
        }
    }

    @PostMapping("submitClassroomApply")
    public ResultDTO<Boolean> submitClassroomApply(
            @RequestHeader(value = "Authorization") String token,   //token
            @RequestBody ClassroomApplyDTO classroomApplyDTO) {
        //验证token
        String account = JwtUtil.getUserAccountToken(token);
        if (account == null || "error".equals(account)) {
            return ResultDTO.fail(400, "身份验证失败");
        } else {
            try {
                return userService.submitClassroomApply(account, classroomApplyDTO);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultDTO.fail(400, e.toString());
            }

        }
    }


    @GetMapping("myReservations")
    public ResultDTO<myReservationsDTO> myReservations(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String user_id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        //验证token
        String account = JwtUtil.getUserAccountToken(token);
        if (account == null || "error".equals(account)) {
            return ResultDTO.fail(400, "身份验证失败");
        } else {
            return userService.myReservations(token, user_id, page, size);
        }
    }

    @PostMapping("cancelReservation")
    public ResultDTO<Boolean> cancelReservation(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam() String apply_id
    ) {
        //验证token
        String account = JwtUtil.getUserAccountToken(token);
        if (account == null || "error".equals(account)) {
            return ResultDTO.fail(400, "身份验证失败");
        } else {
            return userService.cancelReservation(token, apply_id);
        }
    }


}
