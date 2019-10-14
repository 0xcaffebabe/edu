package wang.ismy.edu.domain.course.request;

import wang.ismy.edu.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;


/**
 * @author MY
 */
@Data
@ToString
public class CourseListRequest extends RequestData {
    //公司Id
    private String companyId;
}
