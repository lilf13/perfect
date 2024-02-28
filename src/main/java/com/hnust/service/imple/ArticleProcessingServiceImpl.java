package com.hnust.service.imple;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hnust.pojo.Blog;
import com.hnust.pojo.BlogTag;
import com.hnust.pojo.Tag;
import com.hnust.pojo.User;
import com.hnust.repository.BlogMapper;
import com.hnust.repository.BlogTagMapper;
import com.hnust.repository.TagMapper;
import com.hnust.service.ArticleProcessingService;
import com.hnust.storage.ArticleStorage;
import com.hnust.utils.LabelProcessingUtil;
import com.hnust.utils.PageAnalysisUtil;
import com.hnust.utils.ReadFileUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文章处理
 */
@Service
public class ArticleProcessingServiceImpl implements ArticleProcessingService {

    @Resource
    BlogMapper blogMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    BlogTagMapper blogTagMapper;

    /**
     * 文章保存
     * @param title
     * @param text
     * @param labels
     * @param briefIntroduction
     * @param type
     * @return
     */
    @Transactional
    @Override
    public Boolean articleStorage(String title, MultipartFile text, String[] labels, String briefIntroduction, String type, HttpSession session) {
        //获取当前登录用户信息
        User user = (User) session.getAttribute("user");
        //先对文章进行保存
        String path = ArticleStorage.saveArticle(text,session);
        Blog blog = new Blog(null, user.getId(), title, path, briefIntroduction, type, "已发布", LocalDateTime.now(), LocalDateTime.now());
        int i = blogMapper.saveBlog(blog);
        BlogTag[] blogTags = LabelProcessingUtil.handle(labels, blog, tagMapper);
        //正式执行插入操作
        i += blogTagMapper.saveBolgTag(blogTags);
        return true;
    }

    /**
     * 分页查询
     * @param session
     * @param pageNum
     */
    @Override
    public void selectAllByPageNum(HttpSession session, Integer pageNum, Long categoryId) {
        //如何categoryId为0，则查出所有文章
        if (categoryId == 0) {
            //开启分页查询
            PageHelper.startPage(pageNum,8);
            List<Blog> blogs = blogMapper.selectAllAdoptArticleStep();
            PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs, pageNum);
            PageAnalysisUtil.analysis(blogPageInfo,session);
            //将分页查询结果存入session域
            session.setAttribute("blogPageInfo",blogPageInfo);
            session.setAttribute("category","文章");
        } else {
            //分类查询
            //查询出符合该类别的所有文章id
            List<Long> blogIds = blogTagMapper.selectByTagId(categoryId);
            // 将 List 转换为数组
            Long[] blogIdsArray = blogIds.toArray(new Long[0]);
            //开启分页查询
            PageHelper.startPage(pageNum,8);
            //查询文章内容
            List<Blog> blogs = blogMapper.selectArticleByBlogIds(blogIdsArray);
            PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs, pageNum);
            PageAnalysisUtil.analysis(blogPageInfo,session);
            //将分页查询结果存入session域
            session.setAttribute("blogPageInfo",blogPageInfo);
            //查询出当前文章类别名称
            Tag tag = tagMapper.selectByTagId(categoryId);
            //将当前文章类别名称存入session域
            session.setAttribute("category",tag.getName());
        }
        //将当前文章类别编号存入session域
        session.setAttribute("categoryId",categoryId);
    }

    @Override
    public void selectArticleDetails(HttpSession session, Integer articleIndex) {
        //从session域中获取blogPageInfo集合对象
        PageInfo<Blog> blogPageInfo = (PageInfo<Blog>) session.getAttribute("blogPageInfo");
        List<Blog> list = blogPageInfo.getList();
        //通过该文章在分页查询文章集合中的位置获取该blog对象
        Blog blog = list.get(articleIndex);
        List<BlogTag> blogTags = blogTagMapper.selectByBlogIdStep(blog.getId());
        List<Tag> tags = new ArrayList<>();
        blogTags.forEach(blogTag -> {
            tags.add(blogTag.getTag());
        });
        blog.setTags(tags);
        //查询出文章信息
        String fileString = ReadFileUtil.read(blog.getContentPath());
        blog.setMainTextOfTheArticle(fileString);
        //将查询结果存入session域
        session.setAttribute("blog",blog);
    }

}
