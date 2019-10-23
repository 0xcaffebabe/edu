package wang.ismy.edu.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.response.CmsPageResult;
import wang.ismy.edu.domain.course.*;
import wang.ismy.edu.domain.course.ext.CourseInfo;
import wang.ismy.edu.domain.course.ext.CoursePublishResult;
import wang.ismy.edu.domain.course.ext.CourseView;
import wang.ismy.edu.domain.course.ext.TeachplanNode;
import wang.ismy.edu.domain.course.request.CourseListRequest;
import wang.ismy.edu.domain.course.response.CourseCode;
import wang.ismy.edu.manage_course.client.CmsPageClient;
import wang.ismy.edu.manage_course.dao.CourseMapper;
import wang.ismy.edu.manage_course.dao.TeachPlanMapper;
import wang.ismy.edu.manage_course.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
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
    private CourseMapper courseMapper;
    private CourseMarketRepository courseMarketRepository;
    private CoursePicRepository coursePicRepository;
    private CoursePubRepository coursePubRepository;
    private TeachPlanMediaRepository teachPlanMediaRepository;

    private CmsPageClient cmsPageClient;

    public TeachplanNode findTeachPlan(String courseId) {
        return teachPlanMapper.selectList(courseId);
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseResult savePlan(Teachplan teachplan) {
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();

        if (StringUtils.isEmpty(parentid)) {
            parentid = getTeachplanRoot(courseid);
        }
        Optional<Teachplan> parentNode = teachPlanRepository.findById(parentid);

        if ("1".equals(parentNode.get().getGrade())) {
            teachplan.setGrade("2");
        } else {
            teachplan.setGrade("3");
        }
        teachplan.setParentid(parentid);
        teachPlanRepository.save(teachplan);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private String getTeachplanRoot(String courseId) {

        Optional<CourseBase> opt = courseBaseRepository.findById(courseId);
        if (!opt.isPresent()) {
            return null;
        }

        List<Teachplan> list = teachPlanRepository.findByCourseidAndParentid(courseId, "0");

        if (CollectionUtils.isEmpty(list)) {
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

    public QueryResponseResult<CourseInfo> findCourseList(Integer page, Integer size, CourseListRequest request) {
        PageHelper.startPage(page, size);
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage();

        QueryResult<CourseInfo> result = new QueryResult<>();
        result.setTotal(courseListPage.getTotal());
        result.setList(courseListPage.getResult());
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseResult saveCourse(CourseBase courseBase) {

        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CourseBase findCourse(String courseId) {

        return courseBaseRepository.findById(courseId).orElse(null);
    }

    public ResponseResult updateCourse(String courseId, CourseBase courseBase) {
        if (findCourse(courseId) == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        courseBase.setId(courseId);
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CourseMarket findCourseMarket(String courseId) {
        return courseMarketRepository.findById(courseId).orElse(null);
    }

    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {
        if (findCourse(courseId) == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        courseMarket.setId(courseId);
        courseMarketRepository.save(courseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseResult addCoursePic(String courseId, String pic) {

        if (findCourse(courseId) == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        CoursePic coursePic = new CoursePic();
        coursePic.setCourseid(courseId);
        Optional<CoursePic> opt = coursePicRepository.findById(courseId);

        if (opt.isPresent()) {
            coursePic = opt.get();
        }

        coursePic.setPic(pic);
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CoursePic findCoursePic(String courseId) {
        if (findCourse(courseId) == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        return coursePicRepository
                .findById(courseId)
                .orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseResult deleteCoursePic(String courseId) {
        if (findCourse(courseId) == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        if (coursePicRepository.deleteByCourseid(courseId) != 1) {
            return new ResponseResult(CommonCode.FAIL);
        } else {
            return new ResponseResult(CommonCode.SUCCESS);
        }

    }

    public CourseView findCourseView(String courseId) {
        CourseView courseView = new CourseView();
        CourseBase courseBase = courseBaseRepository.findById(courseId).orElse(null);
        courseView.setCourseBase(courseBase);
        CoursePic coursePic = coursePicRepository.findById(courseId).orElse(null);
        courseView.setCoursePic(coursePic);
        CourseMarket courseMarket = courseMarketRepository.findById(courseId).orElse(null);
        courseView.setCourseMarket(courseMarket);
        courseView.setTeachplanNode(teachPlanMapper.selectList(courseId));
        return courseView;
    }

    public CoursePublishResult preview(String courseId) {
        CourseBase courseBase = findCourse(courseId);

        if (courseBase == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5da8674ce5918e5c1eb60eb9");
        cmsPage.setTemplateId("5ad9a24d68db5239b8fef199");
        cmsPage.setDataUrl("http://localhost:31200/course/courseview/" + courseId);
        cmsPage.setPageName(courseId + ".html");
        cmsPage.setPageAliase(courseBase.getName());
        cmsPage.setPageWebPath("/course/detail/");
        cmsPage.setPagePhysicalPath("/course/detail/");
        cmsPage.setPageCreateTime(new Date());

        CmsPageResult result = cmsPageClient.save(cmsPage);

        if (result.isSuccess()) {
            CmsPage cmsPage1 = result.getCmsPage();
            String url = "//www.edu.com/cms/preview/" + cmsPage1.getPageId();
            return new CoursePublishResult(CommonCode.SUCCESS, url);

        }
        ExceptionCast.cast(CourseCode.COURSE_PUBLISH_CDETAILERROR);
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    public CoursePublishResult publish(String courseId) {
        // 调用cms将页面发布到服务器
        CourseBase courseBase = findCourse(courseId);

        if (courseBase == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5da8674ce5918e5c1eb60eb9");
        cmsPage.setTemplateId("5ad9a24d68db5239b8fef199");
        cmsPage.setDataUrl("http://localhost:31200/course/courseview/" + courseId);
        cmsPage.setPageName(courseId + ".html");
        cmsPage.setPageAliase(courseBase.getName());
        cmsPage.setPageWebPath("/course/detail/");
        cmsPage.setPagePhysicalPath("d:/dev/static/course/detail/");
        cmsPage.setPageCreateTime(new Date());
        CmsPageResult publish = cmsPageClient.publish(cmsPage);
        String pageUrl = "//www.edu.com/course/detail/" + cmsPage.getPageName();
        if (!publish.isSuccess()) {
            return new CoursePublishResult(CommonCode.FAIL, null);
        }

        // 修改课程状态
        saveCoursePubState(courseId);
        // 保存课程索引
        saveCoursePub(courseId);
        return new CoursePublishResult(CommonCode.SUCCESS, pageUrl);
    }

    private CourseBase saveCoursePubState(String courseId) {
        CourseBase courseBase = findCourse(courseId);
        courseBase.setStatus("202002");
        return courseBaseRepository.save(courseBase);
    }

    private void saveCoursePub(String courseId) {
        CoursePub pub = new CoursePub();

        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if (baseOptional.isPresent()) {
            CourseBase courseBase = baseOptional.get();
            BeanUtils.copyProperties(courseBase, pub);
        }
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if (picOptional.isPresent()) {
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic, pub);
        }
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(courseId);
        if (marketOptional.isPresent()) {
            CourseMarket courseMarket = marketOptional.get();
            BeanUtils.copyProperties(courseMarket, pub);
        }
        TeachplanNode teachplanNode = teachPlanMapper.selectList(courseId);
        String jsonString = JSON.toJSONString(teachplanNode);

        pub.setTeachplan(jsonString);
        pub.setId(courseId);
        pub.setTimestamp(new Date());
        pub.setPubTime(LocalDateTime.now().toString());
        coursePubRepository.save(pub);
    }

    public ResponseResult saveMedia(TeachplanMedia media) {
        String teachplanId = media.getTeachplanId();
        if (StringUtils.isEmpty(teachplanId)) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<Teachplan> opt = teachPlanRepository.findById(teachplanId);
        if (!opt.isPresent()) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        Teachplan teachplan = opt.get();
        if (!"3".equals(teachplan.getGrade())) {
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_GRADE_ERROR);
        }
        TeachplanMedia teachplanMedia = teachPlanMediaRepository.findById(teachplanId).orElse(new TeachplanMedia());
        teachplanMedia.setTeachplanId(teachplanId);
        teachplanMedia.setCourseId(media.getCourseId());
        teachplanMedia.setMediaFileOriginalName(media.getMediaFileOriginalName());
        teachplanMedia.setMediaId(media.getMediaId());
        teachplanMedia.setMediaUrl(media.getMediaUrl());
        teachPlanMediaRepository.save(teachplanMedia);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
