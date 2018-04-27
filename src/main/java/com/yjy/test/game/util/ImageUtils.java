package com.yjy.test.game.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class ImageUtils {

    public static Boolean isImage(String originalFilename) {
        Boolean flag = false;
        String[] extensions = new String[]{"JPG", "JPEG", "PNG", "GIF"};
        if (StringUtils.isNotBlank(originalFilename)) {
            String extension = FilenameUtils.getExtension(originalFilename);
            if (StringUtils.isNotBlank(extension)) {
                extension = extension.toUpperCase();
                for (int i = 0; i < extensions.length; i++) {
                    if (extension.equals(extensions[i])) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    public static Boolean isFile(String originalFilename) {
        Boolean flag = false;
        String[] extensions = new String[]{"ZIP", "DOC", "XLS", "DOCX", "XLSX", "PDF"};
        if (StringUtils.isNotBlank(originalFilename)) {
            String extension = FilenameUtils.getExtension(originalFilename);
            if (StringUtils.isNotBlank(extension)) {
                extension = extension.toUpperCase();
                for (int i = 0; i < extensions.length; i++) {
                    if (extension.equals(extensions[i])) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }
}
