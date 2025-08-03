package demo.campus_management_system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import demo.campus_management_system.dao.dao_interface.UsersMapper;
import demo.campus_management_system.entity.Users;
import demo.campus_management_system.service.service_interface.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UsersMapper, Users> implements UserService {
}
