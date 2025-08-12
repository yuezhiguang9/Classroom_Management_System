package demo.campus_management_system.controller;


import demo.campus_management_system.entity.DTO.batchUpdateStatusDTO;
import demo.campus_management_system.entity.VO.batchUpdateStatusVO;
import demo.campus_management_system.service.impl.ClassroomManagerimpl;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mgr")
public class ClassroomManagerController {
    @Autowired
    ClassroomManagerimpl classroomManagerimpl;


    @PostMapping("batchUpdateStatus")
    public ResultDTO<Boolean> batchUpdateStatus(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody(required = true) batchUpdateStatusVO[] batchUpdateStatusVO
    ) {


        //验证token
        String account = JwtUtil.getUserAccountToken(token);
        if (account == null || "error".equals(account)) {
            return ResultDTO.fail(400, "身份验证失败");
        } else {
            try {
                classroomManagerimpl.batchUpdateStatus(token, batchUpdateStatusVO);

                return ResultDTO.success(true);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return ResultDTO.fail(500, "服务器内部错误");
            }
        }


    }


}
