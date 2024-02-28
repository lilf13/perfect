package com.hnust.storage;

import com.hnust.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文章图片保存
 */
@Component
public class ArticleImgStorage {

    @Value("${path.articleImg}")
    private String savePath;  //文件保存路径

    public String savingArticleImages(MultipartFile imgFile,HttpSession session) {
        //获取用户信息
        User user = (User) session.getAttribute("user");
        String src = "../../../../img/articleImg/";
        if (!imgFile.isEmpty()) {
            try {
                String fileName = user.getEmail() + "$" + new Date().getTime() + '_' + imgFile.getOriginalFilename();
                // 构建保存文件的完整路径
                String saveFileName = savePath + fileName;
                // 保存文件到指定路径
                imgFile.transferTo(new File(saveFileName));
                src += fileName;
            } catch (IOException e) {
                e.printStackTrace();
                // 处理文件上传失败的情况
                return "{\n" +
                        "    \"errno\": 1,\n" +
                        "    \"data\": [\n" +
                        "        \"上传失败\"\n" +
                        "    ]\n" +
                        "}";
            }
        }
        return "{\n" +
                "    \"errno\": 0,\n" +
                "    \"data\": {\n" +
                "        \"url\": \"" + src + "\" \n" +
                "    }\n" +
                "}";
    }
    
}
