package cn.exrick.xboot.support.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenchao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest<T> implements Serializable {

    /**
     *
     */
    private static final long	serialVersionUID	= -7320421323343652634L;
    private int					pageNo				= 1;
    private int					pageSize			= 10;

    private String				sortName			= "";
    private OrderDir sortDir				= OrderDir.DESC;

    private T t;



    public int getPageSize() {
        return (0 == pageSize) ? 10 : pageSize;
    }

    public int getFirstResult() {
        return (pageNo - 1) * pageSize;
    }

    public int getHbPageNo(){
        return pageNo - 1;
    }

}
