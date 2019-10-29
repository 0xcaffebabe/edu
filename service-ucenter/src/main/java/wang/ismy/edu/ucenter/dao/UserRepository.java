package wang.ismy.edu.ucenter.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.ucenter.XcUser;

/**
 * @author MY
 * @date 2019/10/29 16:46
 */
public interface UserRepository extends JpaRepository<XcUser,String> {

    XcUser findByUsername(String username);
}
