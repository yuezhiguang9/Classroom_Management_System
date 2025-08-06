package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.*;
import demo.campus_management_system.entity.VO.ListLogsVO;
import demo.campus_management_system.entity.VO.UserListVO;
import demo.campus_management_system.service.impl.SuperAdminImpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class SuperAdmin {
    @Autowired
    private SuperAdminImpl superAdminImpl;

    /**
     * 用户列表
     */
    @GetMapping("listUsers")
    public ResultDTO<List<UserListVO>> listUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String user_type,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String college_id,
            @RequestParam(required = false) String user_name,
            @RequestParam(required = false) String user_id) {
        
        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }
        
        UserListQueryDTO queryDTO = new UserListQueryDTO();
        queryDTO.setUser_type(user_type);
        queryDTO.setPage(page);
        queryDTO.setSize(size);
        queryDTO.setCollege_id(college_id);
        queryDTO.setUser_name(user_name);
        queryDTO.setUser_id(user_id);
        
        return superAdminImpl.listUsers(queryDTO);
    }
    
    /**
     * 编辑用户
     */
    @PostMapping("updateUsers")
    public ResultDTO<Boolean> updateUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody UserOperationDTO userDTO) {
        
        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }
        
        // 参数校验
        if (userDTO.getAccount() == null || userDTO.getAccount().trim().isEmpty()) {
            return ResultDTO.fail(400, "账号不能为空");
        }
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            return ResultDTO.fail(400, "姓名不能为空");
        }
        if (userDTO.getPhone() == null || userDTO.getPhone().trim().isEmpty()) {
            return ResultDTO.fail(400, "手机号不能为空");
        }
        if (userDTO.getUser_type() == null || userDTO.getUser_type().trim().isEmpty()) {
            return ResultDTO.fail(400, "用户类型不能为空");
        }
        
        return superAdminImpl.updateUser(userDTO);
    }
    
    /**
     * 删除用户
     */
    @GetMapping("deleteUsers")
    public ResultDTO<Boolean> deleteUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String account) {
        
        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }
        
        // 参数校验
        if (account == null || account.trim().isEmpty()) {
            return ResultDTO.fail(400, "账号不能为空");
        }
        
        return superAdminImpl.deleteUser(account);
    }
    
    /**
     * 添加用户
     */
    @PostMapping("addUsers")
    public ResultDTO<Boolean> addUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody UserOperationDTO userDTO) {
        
        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }
        
        // 参数校验
        if (userDTO.getAccount() == null || userDTO.getAccount().trim().isEmpty()) {
            return ResultDTO.fail(400, "账号不能为空");
        }
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            return ResultDTO.fail(400, "姓名不能为空");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            return ResultDTO.fail(400, "密码不能为空");
        }
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            return ResultDTO.fail(400, "邮箱不能为空");
        }
        if (userDTO.getPhone() == null || userDTO.getPhone().trim().isEmpty()) {
            return ResultDTO.fail(400, "手机号不能为空");
        }
        if (userDTO.getUser_type() == null || userDTO.getUser_type().trim().isEmpty()) {
            return ResultDTO.fail(400, "用户类型不能为空");
        }
        
        return superAdminImpl.addUser(userDTO);
    }
    
    /**
     * 日志详情查看
     */
    @GetMapping("viewLogs")
    public ResultDTO<ListLogsVO> viewLogs(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String apply_id) {
        
        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }
        
        // 参数校验
        if (apply_id == null || apply_id.trim().isEmpty()) {
            return ResultDTO.fail(400, "申请编号不能为空");
        }
        
        return superAdminImpl.viewLogDetail(apply_id);
    }
    
    /**
     * 数据统计与分析
     */
    @GetMapping("analyzeData")
    public ResultDTO<Object> analyzeData(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String time_range,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end,
            @RequestParam(required = false) String college_id,
            @RequestParam(required = false) String building_id,
            @RequestParam(required = false) String room_type,
            @RequestParam(required = false) String dataType) {
        
        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }
        
        // 参数校验
        if (time_range == null || time_range.trim().isEmpty()) {
            return ResultDTO.fail(400, "时间范围不能为空");
        }
        if (!"week".equals(time_range) && !"month".equals(time_range) && !"semester".equals(time_range)) {
            return ResultDTO.fail(400, "无效的时间范围");
        }
        
        return superAdminImpl.analyzeData(time_range, date_start, date_end, 
                college_id, building_id, room_type, dataType);
    }

    /**
     * 日志显示
     */
    @GetMapping("listLogs")
    public ResultDTO<List<ListLogsDTO>> listLogs(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam(required = false) String apply_status,
            @RequestParam(required = false) String college_id,
            @RequestParam(required = false) String building_id,
            @RequestParam(required = false) String user_name,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        // JWT认证 - 去掉Bearer前缀
        String actualToken = JwtUtil.extractToken(token);
        if (JwtUtil.getUserAccountToken(actualToken).equals("error")) {
            return ResultDTO.fail(401, "未登录或Token失效");
        }

        return superAdminImpl.ListLogs(apply_status, college_id, building_id, 
                user_name, date_start, date_end, page, size);
    }
}
