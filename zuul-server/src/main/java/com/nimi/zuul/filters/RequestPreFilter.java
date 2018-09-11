package com.nimi.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class RequestPreFilter extends ZuulFilter
{

  @Override
  public String filterType()
  {
    return "pre";
  }

  @Override
  public int filterOrder()
  {
    return 0;
  }

  @Override
  public boolean shouldFilter()
  {
    return true;
  }

  @Override
  public Object run()
  {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    System.out.println("ZUUL-RequestPreFilter => Request Method : " + request.getMethod() + " ::: Request URL : " + request.getRequestURL().toString());
    return null;
  }

}
