package wang.ismy.edu.ucenter.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.ucenter.XcCompanyUser;
import wang.ismy.edu.domain.ucenter.XcUser;
import wang.ismy.edu.domain.ucenter.ext.XcUserExt;
import wang.ismy.edu.ucenter.dao.CompanyUserRepository;
import wang.ismy.edu.ucenter.dao.UserRepository;

/**
 * @author MY
 * @date 2019/10/29 16:58
 */
@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private CompanyUserRepository companyUserRepository;

    public XcUserExt getUserExt(String username) {
        XcUser user = findUser(username);
        if (user == null) {
            return null;
        }

        XcCompanyUser companyUser = companyUserRepository.findByUserId(user.getId());
        String companyId = "";
        if (companyUser != null) {
            companyId = companyUser.getCompanyId();
        }

        XcUserExt ext = new XcUserExt();
        BeanUtils.copyProperties(user,ext);
        ext.setCompanyId(companyId);
        return ext;
    }

    public XcUser findUser(String username) {
        return userRepository.findByUsername(username);
    }

}
