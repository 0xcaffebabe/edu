package wang.ismy.edu.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;


/**
 * @author MY
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}
