package wang.ismy.edu.manage_course.pojo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import wang.ismy.edu.domain.course.TeachplanMedia;

/**
 * @author MY
 * @date 2019/10/23 21:05
 */
@Document(indexName = "edu_media",type = "doc")
@Data
public class MediaIndex {

    @Id
    private String teachplanId;

    @Field(type = FieldType.Keyword)
    private String mediaId;

    @Field(type = FieldType.Keyword)
    private String mediaFileOriginalName;

    @Field(type = FieldType.Keyword)
    private String mediaUrl;

    @Field(type = FieldType.Keyword)
    private String courseId;

    public static MediaIndex convert(TeachplanMedia teachplanMedia){
        MediaIndex index = new MediaIndex();
        BeanUtils.copyProperties(teachplanMedia,index);
        return index;
    }
}
