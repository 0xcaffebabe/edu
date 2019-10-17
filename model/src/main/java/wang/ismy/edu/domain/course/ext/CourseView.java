package wang.ismy.edu.domain.course.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.edu.domain.course.CourseBase;
import wang.ismy.edu.domain.course.CourseMarket;
import wang.ismy.edu.domain.course.CoursePic;

import java.io.Serializable;

/**
 * @author MY
 * @date 2019/10/17 20:22
 */
@Data
@NoArgsConstructor

public class CourseView implements Serializable {

    private CourseBase courseBase;

    private CoursePic coursePic;

    private CourseMarket courseMarket;

    private TeachplanNode teachplanNode;
}
