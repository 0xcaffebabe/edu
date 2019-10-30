package wang.ismy.edu.ucenter.mapper;

import wang.ismy.edu.domain.ucenter.XcMenu;

import java.util.List;

/**
 * @author MY
 * @date 2019/10/30 20:41
 */
public interface MenuMapper {

    /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限列表
     */
    List<XcMenu> selectMenu(String userId);
}
