package wang.ismy.edu.manage_course.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.course.CourseBase;
import wang.ismy.edu.domain.course.Teachplan;
import wang.ismy.edu.domain.course.ext.TeachplanNode;
import wang.ismy.edu.manage_course.dao.TeachPlanMapper;
import wang.ismy.edu.manage_course.repository.CourseBaseRepository;
import wang.ismy.edu.manage_course.repository.TeachPlanRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author MY
 * @date 2019/10/14 13:13
 */
@Service
@AllArgsConstructor
public class CourseService {

    private TeachPlanMapper teachPlanMapper;

    private TeachPlanRepository teachPlanRepository;

    private CourseBaseRepository courseBaseRepository;

    public TeachplanNode findTeachPlan(String courseId) {
        return teachPlanMapper.selectList(courseId);
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseResult save(Teachplan teachplan) {
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();
        if (StringUtils.isEmpty(parentid)){
            parentid = getTeachplanRoot(courseid);
        }
        Optional<Teachplan> parentNode = teachPlanRepository.findById(parentid);

        if ("1".equals(parentNode.get().getGrade())){
            teachplan.setGrade("2");
        }else {
            teachplan.setGrade("3");
        }
        teachPlanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private String getTeachplanRoot(String courseId){

        Optional<CourseBase> opt = courseBaseRepository.findById(courseId);
        if (!opt.isPresent()){
            return null;
        }

        List<Teachplan> list = teachPlanRepository.findByCourseidAndParentid(courseId, "0");

        if (CollectionUtils.isEmpty(list)){
            // 查询不到，添加根节点
            Teachplan plan = new Teachplan();
            plan.setParentid("0");
            plan.setGrade("1");
            plan.setPname(opt.get().getName());
            plan.setStatus("0");
            return teachPlanRepository.save(plan).getId();
        }
        return list.get(0).getId();
    }
}
