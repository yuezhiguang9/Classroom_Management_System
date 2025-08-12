package demo.campus_management_system.dao.dao_interface;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.campus_management_system.entity.Apply_info;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplyInfo extends BaseMapper<Apply_info> {
}
