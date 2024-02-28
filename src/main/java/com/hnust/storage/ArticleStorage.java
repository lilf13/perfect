package com.hnust.storage;

import com.hnust.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文章保存
 */
public class ArticleStorage {

    public static String saveArticle(MultipartFile text, HttpSession session){
        //获取用户信息
        User user = (User) session.getAttribute("user");
        String saveFileName = null;
        if (!text.isEmpty()) {
            try {
                // 指定文件保存路径
                String savePath = "D:\\project\\mavenProject\\forum\\article\\";
                String fileName = user.getEmail() + "$" + new Date().getTime() + "txt";
                // 构建保存文件的完整路径
                saveFileName = savePath + fileName;
                // 保存文件到指定路径
                text.transferTo(new File(saveFileName));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saveFileName;
    }
}
