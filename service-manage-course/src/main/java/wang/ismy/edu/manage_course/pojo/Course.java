package wang.ismy.edu.manage_course.pojo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import wang.ismy.edu.domain.course.CoursePub;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import java.util.Date;

/**
 * @author MY
 * @date 2019/10/20 13:35
 */
@Document(indexName = "edu_course",type = "doc")
@Data
public class Course {

    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;
    @Field(type = FieldType.Text,index = false)
    private String users;
    @Field(type = FieldType.Keyword)
    private String mt;
    @Field(type = FieldType.Keyword)
    private String st;
    @Field(type = FieldType.Keyword)
    private String grade;
    @Field(type = FieldType.Keyword)
    private String studymodel;
    @Field(type = FieldType.Keyword)
    private String teachmode;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String description;
    @Field(type = FieldType.Keyword,index = false)
    private String pic;
    @Field(type = FieldType.Keyword)
    private String charge;
    @Field(type = FieldType.Keyword)
    private String valid;
    @Field(type = FieldType.Keyword,index = false)
    private String qq;
    @Field(type = FieldType.Float)
    private Float price;
    @Field(type = FieldType.Float)
    private Float price_old;
    @Field(type = FieldType.Date)
    private String expires;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String teachplan;
//    @Field(type = FieldType.Date)
//    private String pubTime;

    public static Course convert(CoursePub pub){
        Course course = new Course();
        BeanUtils.copyProperties(pub,course);
        return course;
    }
}
