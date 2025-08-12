package demo.campus_management_system.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.ClassroomManagerMapper;
import demo.campus_management_system.entity.Classroom_manager;
import demo.campus_management_system.entity.DTO.batchUpdateStatusDTO;
import demo.campus_management_system.entity.VO.batchUpdateStatusVO;
import demo.campus_management_system.service.service_interface.ClassroomManagerService;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ClassroomManagerimpl extends ServiceImpl<ClassroomManagerMapper, Classroom_manager> implements ClassroomManagerService {

    @Autowired
    ClassroomManagerMapper classroomManagerMapper;

    //批量更新教室状态
    public ResultDTO<Boolean> batchUpdateStatus(String token, batchUpdateStatusVO[] UpdateStatusVO) {
        try {
            //判断当前登录的人是不是教室管理员
            String mgrAccount = classroomManagerMapper.selectById(JwtUtil.getUserAccountToken(token)).getMgrAccount();
            if (mgrAccount.isEmpty()) {
                return ResultDTO.fail(400, "身份验证失败");
            } else {
                // 获取当前日期和时间
                DateTime now = DateTime.now();
                System.out.println("nowTime" + now);


                for (batchUpdateStatusVO str : UpdateStatusVO) {
                    if (str.getRoom_status().equals("空闲")) {
                        classroomManagerMapper.updateStatusOne(str.getRoom_num(), str.getRoom_status(), now);
                        classroomManagerMapper.updateStatusOnet(str.getRoom_num(), now);
                    } else if (str.getRoom_status().equals("不可用")) {
                        // 验证持续时间是否有效
                        if (str.getDuration() == null || str.getDuration().isBefore(now)) {
                            throw new IllegalArgumentException("无效的持续时间");
                        }
                        classroomManagerMapper.updateStatusTwo(str.getRoom_num(), str.getRoom_status(), now, str.getDuration());
                    }
                }
                return ResultDTO.success(true);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResultDTO.fail(500, "服务器内部错误");
        }
    }


}
