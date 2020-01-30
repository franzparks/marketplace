package francis.market.user.util;

import francis.market.common.model.UserVO;
import francis.market.user.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * ObjectMapperUtil
 */
public final class ObjectMapperUtil {

    public static User userEntity(UserVO userVO) {
        User userEntity = new User();
        BeanUtils.copyProperties(userVO, userEntity);
        return userEntity;
    }

    public static UserVO userVO(User userEntity) {
        UserVO userVO = UserVO.builder().withEmail(userEntity.getEmail())
                .withContact(userEntity.getContact())
                .withFirstName(userEntity.getFirstName())
                .build();
        return userVO;
    }
}
