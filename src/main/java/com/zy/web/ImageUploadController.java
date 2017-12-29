package com.zy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * User: YuanZeYao
 * Date: 2017/12/29
 * Time: 17:19
 */
@Controller
public class ImageUploadController {
    @RequestMapping("uploader")
    public void upload(HttpServletRequest request,HttpServletResponse response){

        System.out.println("收到图片!");
        MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
        String upaloadUrl = request.getSession().getServletContext().getRealPath("/")+"upload/";//得到当前工程路径拼接上文件名
        File dir = new File(upaloadUrl);
        System.out.println(upaloadUrl);
        if(!dir.exists())//目录不存在则创建
            dir.mkdirs();
        int counter=0;
        for(MultipartFile file :files.values()){
            counter++;
            String fileName=file.getOriginalFilename();
            File tagetFile = new File(upaloadUrl+fileName);//创建文件对象
            if(!tagetFile.exists()){//文件名不存在 则新建文件，并将文件复制到新建文件中
                try {
                    tagetFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    file.transferTo(tagetFile);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        System.out.println("接收完毕");
    }
}
