package com.pangsir.helper.filter;

import com.pangsir.helper.filter.wrapper.CharArrayWrapper;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


/**
 * 通用替换过滤器 过滤器,替换出现的所有给定的字符串替换。
 * 这是一个抽象类:你必须覆盖getTargetString()和getReplacementString()方法在子类中。
 * 第一种方法指定应替换的字符串 第二个这些指定字符串,应该替换出现的每个目标字符串。
 * @see com.pangsir.helper.filter.AbsReplaceFilter
 * @since 1.0
 */
public abstract class AbsReplaceFilter implements Filter {
    private FilterConfig config;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 声明包装器。
        CharArrayWrapper responseWrapper = new CharArrayWrapper(
                (HttpServletResponse) response);
        // 调用资源
        chain.doFilter(request, responseWrapper);
        // 返回值：把整个输出到一个大的字符串。
        String responseString = responseWrapper.toString();
        // 在输出中,替换出现的所有目标字符串替换字符串
        responseString = replace(responseString, getTargetString(),
                getReplacementString());
        // 更新的内容长度标题详细说明。
        updateHeaders(response, responseString);
        PrintWriter out = response.getWriter();
        out.write(responseString);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    protected FilterConfig getFilterConfig() {
        return (config);
    }

    @Override
    public void destroy() {

    }

    /**
     * 定义需要更换的字符串了。重载这个方法在你的子类。
     */
    public abstract String getTargetString();

    /**
     * 字符串替换目标。重载这个方法在你的子类。
     */
    public abstract String getReplacementString();

    /**
     * 更新响应头信息，这个简单的版本只是设置内容长度标题 假设我们使用一个字符集,使用1字节/字符，对于其他字符集,覆盖这个方法
     * 使用不同的逻辑或放弃持续HTTP连接。在这后一种情况下,都该方法设置连接头“结束”
     */
    public void updateHeaders(ServletResponse response, String responseString) {
        response.setContentLength(responseString.length());
    }

    /**
     * 出现的所有更改,mainString源自在替换。
     *
     * @param mainString  原来字符串
     * @param orig        要替换的字符
     * @param replacement 替代者
     * @return String
     */
    public static String replace(String mainString, String orig,
                                 String replacement) {
        String result = "";
        int oldIndex = 0;
        int index = 0;
        int origLength = orig.length();
        while ((index = mainString.indexOf(orig, oldIndex)) != -1) {
            result = result + mainString.substring(oldIndex, index)
                    + replacement;
            oldIndex = index + origLength;
        }
        result = result + mainString.substring(oldIndex);
        return (result);
    }

}
