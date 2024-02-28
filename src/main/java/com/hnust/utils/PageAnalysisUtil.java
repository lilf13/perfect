package com.hnust.utils;

import com.github.pagehelper.PageInfo;
import com.hnust.pojo.Blog;
import jakarta.servlet.http.HttpSession;

/**
 * 页数解析工具类
 */
public class PageAnalysisUtil {

    public static void analysis(PageInfo<Blog> blogPageInfo, HttpSession session) {
        Integer beginPage = blogPageInfo.getPageNum() - 2;
        Integer endPage = blogPageInfo.getPageNum() + 2;
        if ((blogPageInfo.getPageNum()-2) <= 0) {
            beginPage = 1;
        }
        if ((blogPageInfo.getPageNum()+2) > blogPageInfo.getPages()) {
            endPage = blogPageInfo.getPages();
        }
        session.setAttribute("beginPage",beginPage);
        session.setAttribute("endPage",endPage);
    }

}
