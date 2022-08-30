package cn.exrick.xboot.support.retn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chenchao
 * @version 创建时间：2017年11月12日 上午22:57:51
 * 分页实体类
 * total 总数
 * code  是否成功
 * data 当前页结果集
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7320421323343652634L;

    @Builder.Default
    private int pageNo = 1;
    @Builder.Default
    private int pageSize = 10;
    @Builder.Default
    private long count = 0;
    private long code;

    //总页数
    //private int					totalPage;


    private List<T> data = new ArrayList<T>();


    public long getTotalPage() {
        return (count + pageSize - 1) / pageSize;
    }


}
