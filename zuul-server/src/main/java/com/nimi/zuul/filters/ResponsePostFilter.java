package com.nimi.zuul.filters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ResponsePostFilter extends ZuulFilter
{

  @Override
  public String filterType()
  {
    return "post";
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
    try (final InputStream responseDataStream = ctx.getResponseDataStream())
    {
      final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
      ctx.setResponseBody("ZUUL-ResponsePostFilter ::: " +responseData);
    }
    catch (IOException e)
    {
      System.out.println("Exception : " + e);
    }
    return null;
  }

}
