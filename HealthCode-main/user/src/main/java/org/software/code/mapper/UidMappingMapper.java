package org.software.code.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.software.code.entity.UidMapping;

/**
* @description 针对表【uid_mapping】的数据库操作Mapper
* @createDate 2025-02-19 10:21:35
* @Entity org.software.code.entity.UidMapping
 *
 * @author “101”计划《软件工程》实践教材案例团队
*/
@Mapper
public interface UidMappingMapper extends BaseMapper<UidMapping> {
}




