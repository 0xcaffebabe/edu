package wang.ismy.edu.common.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: mrt.
 * @Description:
 * @Date:Created in 2018/1/24 18:33.
 * @Modified By:
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryResult<T> {
    //数据列表
    private List<T> list;
    //数据总数
    private long total;

    public QueryResult<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public QueryResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }
}
