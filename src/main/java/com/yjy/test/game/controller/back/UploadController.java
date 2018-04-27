package com.yjy.test.game.controller.back;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjy.test.game.util.ImageCompress;
import com.yjy.test.game.util.ImageUtils;
import com.yjy.test.game.web.ErrorCode;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

/**
 * 上传文件
 *
 * @author wsc
 * 2016年11月28日
 */
@Controller
public class UploadController extends BaseBackController {

    public static final Logger log = LoggerFactory.getLogger(UploadController.class);

    public static final String FRONT_IMG_PATH = "/upload/back/images/";
    public static final String FRONT_IMG_TEMP_PATH = "/upload/back/images/temp/";

    public static final String FRONT_FILE_PATH = "/upload/back/files/";
    public static final String FRONT_FILE_TEMP_PATH = "/upload/back/files/temp/";

    @RequestMapping("/upload_image.do")
    public void uploadPic(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        if (null == file) {
            this.ajaxErrorToJson(response, null, "文件不存在");
            return;
        }
        String originalFilename = file.getOriginalFilename();
        if (!ImageUtils.isImage(originalFilename)) {
            this.ajaxErrorToJson(response, null, "图片格式不正确");
            return;
        }
        try {
            String proPath = request.getSession().getServletContext().getRealPath("/");
            long size = file.getSize();
            String newFileName = newFileName(originalFilename);
            if (size > 5242880) {//大于5M
                uploadAndCompress(file, tempDirPath(proPath, "image"), descDirPath(proPath, "image"), newFileName);
            } else {
                uploadFile(file, descDirPath(proPath, "image"), newFileName);
            }
            String absUrl = absUrl(newFileName, "image");//存入数据库中的路径
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("url", absUrl);
            ajaxSuccessToJson(response, JSON.toJSONString(map), ErrorCode.SUCCESS);
            return;
        } catch (Exception e) {
            log.error("上传图片文件失败", e);
        }
    }

    @RequestMapping("/upload_file.do")
    public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        if (null == file) {
            this.ajaxErrorToJson(response, null, "文件不存在");
            return;
        }
        String originalFilename = file.getOriginalFilename();
        if (!ImageUtils.isFile(originalFilename)) {
            this.ajaxErrorToJson(response, null, "文件格式不正确");
            return;
        }
        try {
            String proPath = request.getSession().getServletContext().getRealPath("/");
            String newFileName = newFileName(originalFilename);

            uploadFile(file, descDirPath(proPath, "file"), newFileName);

            String absUrl = absUrl(newFileName, "file");//存入数据库中的路径
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("url", absUrl);
            ajaxSuccessToJson(response, JSON.toJSONString(map), ErrorCode.SUCCESS);
            return;
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
    }

    /**
     * 上传并压缩，大于500k的文件
     */
    protected void uploadAndCompress(MultipartFile file, String tempDir, String descDir, String filename) throws Exception {
        uploadFile(file, tempDir, filename);
        compress(tempDir + filename, descDir, filename);
        deleteTempFile(new File(tempDir, filename));
    }

    /**
     * 上传文件
     */
    protected void uploadFile(MultipartFile file, String dir, String filename) throws Exception {
        File f = new File(dir, filename);
        if (!f.getParentFile().exists()) {
            f.mkdirs();
        }
        file.transferTo(f);
    }

    /**
     * 删除临时文件
     */
    protected void deleteTempFile(File file) throws Exception {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 压缩文件
     */
    protected void compress(String tempFilePath, String descFilePath, String newFileName) throws Exception {
        ImageCompress.compress(tempFilePath, descFilePath, newFileName);
    }

    /**
     * 相对于项目的路径，存入数据库
     */
    protected String absUrl(String fileName, String type) throws Exception {
        if ("image".equals(type))
            return FRONT_IMG_PATH + dateDir() + fileName;
        else
            return FRONT_FILE_PATH + dateDir() + fileName;
    }

    /**
     * 临时文件全路径
     */
    protected String tempDirPath(String proPath, String type) throws Exception {
        if ("image".equals(type))
            return proPath + FRONT_IMG_TEMP_PATH;
        else
            return proPath + FRONT_FILE_TEMP_PATH;
    }

    /**
     * 上传目标文件全路径
     */
    protected String descDirPath(String proPath, String type) throws Exception {
        if ("image".equals(type))
            return proPath + FRONT_IMG_PATH + dateDir();
        else
            return proPath + FRONT_FILE_PATH + dateDir();
    }

    /**
     * 拼上日期路径
     */
    protected String dateDir() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        return sdf.format(new Date());
    }

    /**
     * 新的文件名
     */
    protected String newFileName(String originalFilename) throws Exception {
        String extension = FilenameUtils.getExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }

}
