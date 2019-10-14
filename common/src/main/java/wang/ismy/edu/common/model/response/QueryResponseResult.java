package wang.ismy.edu.common.model.response;

import lombok.Data;
import lombok.ToString;

/**
 * @author MY
 */
@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult<T> queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

}
