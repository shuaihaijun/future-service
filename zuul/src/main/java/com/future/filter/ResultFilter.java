package com.future.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultFilter extends ZuulFilter  {

    private static Logger log = LoggerFactory.getLogger(ResultFilter.class);

    @Override
    public String filterType() {
        //定义filter的类型，有pre、route、post、error四种
        return "post";
    }

    @Override
    public int filterOrder() {
        //定义filter的顺序，数字越小表示顺序越高，越先执行
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //表示是否需要执行该filter，true表示执行，false表示不执行
        return true;
    }

    @Override
    public Object run() {
        //filter需要执行的具体操作

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        
        log.info("===============>进入结果处理的过滤器！");
        
        return null;
    }

}
