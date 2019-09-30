package com.gialen.tools.dao.repository.customer.extend;

import com.gialen.tools.dao.dto.DateTimeDto;
import com.gialen.tools.dao.dto.UserDataDto;
import com.gialen.tools.dao.repository.customer.UserMapper;

import java.util.List;

/**
 * @author wong
 * @Date: 2019-09-25
 * @Version: 1.0
 */
@org.springframework.stereotype.Repository
public interface UserExtendMapper extends UserMapper {

    List<UserDataDto> countUserType(DateTimeDto dateTimeDto);

}
