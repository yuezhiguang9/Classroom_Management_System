package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UsersMapper;
import demo.campus_management_system.entity.DTO.ClassroomApplyDTO;
import demo.campus_management_system.entity.DTO.SelectClassroomDTO;
import demo.campus_management_system.entity.DTO.myReservationsDTO;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.entity.VO.myReservationsVO;
import demo.campus_management_system.dao.dao_interface.ApplyInfo;
import demo.campus_management_system.service.service_interface.UserService;
import demo.campus_management_system.util.DataUtils;
import demo.campus_management_system.util.JwtUtil;
import demo.campus_management_system.util.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {

}
