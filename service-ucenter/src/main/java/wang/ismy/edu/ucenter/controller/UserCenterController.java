package wang.ismy.edu.ucenter.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.ucenter.UcenterControllerApi;
import wang.ismy.edu.domain.ucenter.ext.XcUserExt;
import wang.ismy.edu.ucenter.service.UserService;

/**
 * @author MY
 * @date 2019/10/29 17:04
 */
@RestController
@RequestMapping("ucenter")
@AllArgsConstructor
public class UserCenterController implements UcenterControllerApi {

    private UserService userService;

    @GetMapping("getuserext")
    @Override
    public XcUserExt getUser(@RequestParam String username) {
        return userService.getUserExt(username);
    }
}
