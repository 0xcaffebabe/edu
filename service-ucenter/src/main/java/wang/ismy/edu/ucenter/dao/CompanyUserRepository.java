package wang.ismy.edu.ucenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.ucenter.XcCompanyUser;
import wang.ismy.edu.domain.ucenter.XcUser;

/**
 * @author MY
 * @date 2019/10/29 16:51
 */
public interface CompanyUserRepository extends JpaRepository<XcCompanyUser,String> {

    /**
     * 根据用户ID查询所属公司ID
     * @param userId 用户ID
     * @return 公司用户联系
     */
    XcCompanyUser findByUserId(String userId);
}
