package demo.campus_management_system.controller;

import demo.campus_management_system.entity.DTO.*;

import demo.campus_management_system.entity.DTO.ListLogsDTO;
import demo.campus_management_system.entity.DTO.UserListQueryDTO;
import demo.campus_management_system.entity.VO.UserListVO;
import demo.campus_management_system.service.impl.SuperAdminImpl;
import demo.campus_management_system.service.service_interface.SuperAdmin;
import demo.campus_management_system.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
public class SuperAdminController {
    @Autowired
    private SuperAdminImpl superAdminImpl;

    // 更新用户数据
    @PostMapping("updateUsers")
    public ResultDTO<Boolean> updateUsers(@RequestHeader(value = "Authorization") String token,
                                          @RequestBody UpdateUsersDTO updateUsersDTO) {
        try {

            // 打印测试
            // System.out.println(JwtUtil.getUserAccountToken(token) + "----" +
            // JwtUtil.getUserPasswordToken(token));
            // System.out.println("usersDTO=" + "\n" + updateUsersDTO.getTotal());
            String account = JwtUtil.getUserAccountToken(token);
            // System.out.println("account=" + account + "---" + "password=" + password);

            // 先判断token正确性
            if (account == null || "error".equals(account)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }
            superAdminImpl.updateUsers(token, updateUsersDTO);
            return ResultDTO.success(true, "查询成功");

        } catch (Exception e) {
            System.out.println("e=" + e);
            return ResultDTO.fail(500, e.toString());
        }
    }


    @GetMapping("/listUsers")
    public ResultDTO<UserListDTO> listUsers(
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

    // 删除用户数据
    @GetMapping("deleteUsers")
    public ResultDTO<Boolean> deleteUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestParam String account, // 要删除的用户账号
            @RequestParam String user_type // 用户类型（user/teach_sec/class_mgr）
    ) {
        try {
            // 验证token有效性
            String adminAccount = JwtUtil.getUserAccountToken(token);
            if (adminAccount == null || "error".equals(adminAccount)) {
                return ResultDTO.fail(401, "未登录或Token失效");
            }

            // 封装删除参数
            DeleteUsersDTO deleteDTO = new DeleteUsersDTO();
            deleteDTO.setAccount(account);
            deleteDTO.setUser_type(user_type);

            // 调用服务层执行删除
            return superAdminImpl.deleteUsers(token, deleteDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器错误：" + e.getMessage());
        }
    }

    // 添加用户数据
    @PostMapping("addUsers")
    public ResultDTO<Boolean> addUsers(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody AddUsersDTO addUsersDTO) {
        try {
            // 调用服务层执行新增操作
            return superAdminImpl.addUsers(token, addUsersDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器错误：" + e.getMessage());
        }
    }

    // 日志显示
    @GetMapping("listLogs")
    public ResultDTO<List<ListLogsDTO>> listUsers(
            @RequestHeader(value = "Authorization") String token, // 前端传过来的token
            @RequestParam(required = false) String apply_status, // 申请状态
            @RequestParam(required = false) String college_id, // 学院id
            @RequestParam(required = false) String building_id, // 楼栋名称
            @RequestParam(required = false) String user_name, // 申请人姓名
            @RequestParam(required = false) String date_start, // 开始日期
            @RequestParam(required = false) String date_end, // 结束日期
            @RequestParam(defaultValue = "1") Integer page, // 页码
            @RequestParam(defaultValue = "10") Integer size) // 每页条数
    {

        List<ListLogsDTO> result = new ArrayList<>();
        // jwt认证
        if (JwtUtil.getUserAccountToken(token).equals("error")) {
            // 认证不通过就返回错误码
            return ResultDTO.fail(401, "未登录或Token失效");
        } else {
            // 认证通过就查找数据
            ResultDTO<List<ListLogsDTO>> data = superAdminImpl.ListLogs(apply_status,
                    college_id,
                    building_id,
                    user_name,
                    date_start,
                    date_end,
                    page,
                    size);
            // 返回数据
            return data;
        }
    }

    // 数据统计与分析analyzeData

    @Resource
    private SuperAdmin superAdminService;

    @GetMapping("/analyzeData")
    public ResultDTO<List<AnalyzeDataDTO>> analyzeData(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end) {

        return superAdminService.analyzeData(token, date_start, date_end);
    }

}
