package wang.ismy.edu.domain.ucenter.ext;

import wang.ismy.edu.domain.course.ext.CategoryNode;
import wang.ismy.edu.domain.ucenter.XcMenu;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/3/20.
 */
@Data
@ToString
public class XcMenuExt extends XcMenu {

    List<CategoryNode> children;
}
