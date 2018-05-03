package com.yjy.test.game.util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 压缩图片
 *
 * @author wsc
 * 2016年12月10日
 */
public class ImageCompress {

    private static final float SCALE = 0.5f;
    private static final float QUALITY = 0.75f;
    private static final int WIDTH = 750;

    public static void compress(String inputPath, String outputPath,
                                String toFileName) {
        imageCompress(inputPath, outputPath, toFileName, SCALE, QUALITY, WIDTH);
    }

    public static void imageCompress(String inputPath, String outputPath,
                                     String toFileName, float scale, float quality, int width) {
        FileOutputStream out = null;
        try { // 原图路径 原图名称 目标路径 压缩比率0.5 0.75 原图宽度 原图高度
            Image image = ImageIO.read(new File(inputPath));
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            if (scale > 0.5)
                scale = 0.5f;// 默认压缩比为0.5，压缩比越大，对内存要去越高，可能导致内存溢出
            // 按比例计算出来的压缩比
            float realscale = getRatio(imageWidth, imageHeight, width);
            float finalScale = Math.min(scale, realscale);// 取压缩比最小的进行压缩
            imageWidth = (int) (finalScale * imageWidth);
            imageHeight = (int) (finalScale * imageHeight);
            image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_AREA_AVERAGING);
            BufferedImage mBufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = mBufferedImage.createGraphics();
            g2.drawImage(image, 0, 0, imageWidth, imageHeight, Color.white, null);
            g2.dispose();
            float[] kernelData2 = {-0.125f, -0.125f, -0.125f, -0.125f, 2,
                    -0.125f, -0.125f, -0.125f, -0.125f};
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);
            File file = new File(outputPath, toFileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(outputPath + toFileName);

            // jdk1.8不再支持com.sun.image.codec.jpeg.JPEGCodec
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(mBufferedImage);
//            param.setQuality(quality, true);// 默认0.75
//            encoder.setJPEGEncodeParam(param);
//            encoder.encode(mBufferedImage);

            String[] names = toFileName.split("\\.");
            ImageIO.write(mBufferedImage, names.length > 1 ? names[names.length - 1] : "jpg", out);
            out.close();

            out.flush();
            out.close();
            mBufferedImage.flush();
            mBufferedImage = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 按宽度进行压缩
     */
    private static float getRatio(int width, int height, int maxWidth) {// 获得压缩比率的方法
        return (float) maxWidth / width;
    }

    public static byte[] convertImage2Type(String imageFile, String imageType)
            throws Exception {// 图片格式转换
        File inputFile = new File(imageFile);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        BufferedImage input = ImageIO.read(inputFile);
        ImageIO.write(input, imageType, output);
        return output.toByteArray();
    }

    public static void convertImage2TypePng(String imageFile, String imageType)
            throws Exception {// 图片格式转换
        File inputFile = new File(imageFile);
        int suffixIndex = imageFile.lastIndexOf(".");
        String suffix = imageFile.substring(suffixIndex + 1);
        if (!"png".equals(suffix)) {// 如果原图片的不是PNG格式的图片
            String fileName = imageFile.substring(0, suffixIndex + 1) + "png";
            File output = new File(fileName);
            BufferedImage input = ImageIO.read(inputFile);
            ImageIO.write(input, imageType, output);
            // 转换后删除原文件
            if (inputFile.exists())
                inputFile.delete();
        }
    }

}