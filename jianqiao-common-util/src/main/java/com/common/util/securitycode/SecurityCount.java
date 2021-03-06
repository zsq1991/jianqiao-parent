package com.common.util.securitycode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @Description : 图形验证码
 * @author  by : tenghui
 * @Creation Date ：2018年01月16日11:53
 */
public class SecurityCount {

    private static Logger logger = LoggerFactory.getLogger(SecurityCount.class);

    // 去掉了1,0,i,o几个容易混淆的字符
    /**
     *
     * @author hangxin
     * @data 2018/1/3 16:29
     * @Description:
     * @Version
     * @return
     *
     */
    public static final String VERIFY_CODES = "0123456789";
    private static Random random = new Random();

    /**
     * 使用指定源生成验证码
     *
     * @param symbolCount
     *            验证码长度
     * @param sources
     *            验证码字符源
     * @return
     */
    public static String generateVerifyCode(int symbolCount, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(symbolCount);
        for (int i = 0; i < symbolCount; i++) {
            verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
        }

        //取第一位数加2取余判断加减号
        String subString = verifyCode.subSequence(0, 1).toString();
        int decideCount = (Integer.parseInt(subString)+2) % 2;
        String zero = "0";
        String one = "1";
        //与1,2进行比较
        if (one.equals(String.valueOf(decideCount))){
            verifyCode.replace(1, 2, "+");
        }else if(zero.equals(String.valueOf(decideCount))){
            verifyCode.replace(1, 2, "-");
        }

        //  最后一个替换成等于号
        verifyCode.replace(3, 4, "=");
        return verifyCode.toString();
    }

    /**
     * @description ：输出随机验证码图片流,并返回验证码值
     * @Created by  : tenghui
     * @Creation Date ： 2018/1/16 13:55
     * @version : 1.0.0
     * @param w 宽
     * @param h 搞
     * @param os 输出流
     * @param verifySize 验证码长度
     * @return
     * @throws IOException
     */
    public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException {
        logger.info("=============进入输出验证码图片流方法==================");
        logger.info("========使用指定源生成验证码===========");
        String verifyCode = generateVerifyCode(verifySize, VERIFY_CODES);
        logger.info("生成的数字是："+verifyCode);
        //减号替换后的
        //获取加号或者减号
        String subString = verifyCode.substring(1,2);

        //判断加减号 并计算
        int x = 0;
        String add="+";
        String sub="-";
        if (add.equals(subString)) {
            String one = verifyCode.toString().substring(0,1);
            logger.info("生成的前一个字符："+one);
            String two = verifyCode.toString().substring(2,3);
            logger.info("生成的后一个字符："+two);

            x = Integer.parseInt(one) + Integer.parseInt(two);
            logger.info("相加——计算出的数字："+ x);
        }else if(sub.equals(subString)) {
            //如果是减号，，判断大小，然后从新生成

            String one = verifyCode.toString().substring(0,1);
            logger.info("生成的前一个字符："+one);
            String two = verifyCode.toString().substring(2,3);
            logger.info("生成的后一个字符："+two);

            if (Integer.parseInt(one) < Integer.parseInt(two)){
                verifyCode = new StringBuffer().append(two).append("-").append(one).append("=").toString();
                x = Integer.parseInt(two) - Integer.parseInt(one);
                logger.info("减号替换后的："+verifyCode);
            }else {
                x = Integer.parseInt(one) - Integer.parseInt(two);
            }
            logger.info("相减——计算出的数字："+ x);

        }
        outputImage(w, h, os, verifyCode);
        logger.info("=============输出验证码图片流方法结束==================");
        return String.valueOf(x);
    }

    /**
     * 输出指定验证码图片流
     *
     * @param w
     * @param h
     * @param os
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int symbolCount = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colors = new Color[5];
        Color[] colorSpaces = new Color[] { Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA,
                Color.ORANGE, Color.PINK, Color.YELLOW };
        float[] fractions = new float[colors.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = colorSpaces[random.nextInt(colorSpaces.length)];
            fractions[i] = random.nextFloat();
        }
        Arrays.sort(fractions);

        // 设置边框色
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, w, h);

        // getRandColor(200, 250);
        Color c = new Color(220, 240, 230);
        // 设置背景色
        g2.setColor(c);
        g2.fillRect(0, 2, w, h - 4);

        // 绘制干扰线
        // 设置线条的颜色
        g2.setColor(getRandColor(60, 80));
        int snum = 5;
        for (int i = 0; i < snum; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }

        // 使图片扭曲
        shear(g2, w, h, c);

        g2.setColor(getRandColor(80, 100));
        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < symbolCount; i++) {
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                    (w / symbolCount) * i + fontSize / 2, h / 2);
            g2.setTransform(affine);
            g2.drawChars(chars, i, 1, ((w - 10) / symbolCount) * i + 5, h / 2 + fontSize / 2 - 5);
        }

        // add small symbols
        int fontSize1 = fontSize * 2 / 3;
        Font font1 = new Font("Algerian", Font.ITALIC, fontSize1);
        g2.setFont(font1);
        char[] chars1 = generateVerifyCode(10, null).toCharArray();
        for (int i = 0; i < chars1.length; i++) {
            g2.setColor(getRandColor(120, 140));
            AffineTransform affine = new AffineTransform();
            int y = random.nextInt(h / 2);
            affine.setToRotation(Math.PI / 4 * random.nextDouble() * (random.nextBoolean() ? 1 : -1), i * fontSize1, y);
            g2.setTransform(affine);
            g2.drawChars(chars1, i, 1, random.nextInt(w), 20 + random.nextInt(h / 2));
        }

        // 添加噪点
        // 噪声率
        float yawpRate = 0.04f;
        int area = (int) (yawpRate * w * h);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(w);
            int y = random.nextInt(h);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }

        g2.dispose();
        ImageIO.write(image, "jpg", os);
    }

    private static Color getRandColor(int fc, int bc) {
        int ac = 255;
        if (fc > ac){
            fc = 255;
        }
        if (bc > ac){
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }
        return color;
    }

    private static int[] getRandomRgb() {
        int[] rgb = new int[3];
        int inum = 3;
        for (int i = 0; i < inum; i++) {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }

    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {

        int period = random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {

        // 50;
        int period = random.nextInt(40) + 10;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    /**
     * 生成指定验证码图像文件
     *
     * @param w
     * @param h
     * @param outputFile
     * @param code
     * @throws IOException
     */
    public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
        if (outputFile == null) {
            return;
        }
        File dir = outputFile.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            outputImage(w, h, fos, code);
            fos.close();
        } catch (IOException e) {
            throw e;
        }
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("D:/verifies");
        int w = 1600, h = 500;

        //进行替换数字
        int num = 5;
        for (int i = 0; i < num; i++){
            System.out.println("生成的验证码："+new Date(System.currentTimeMillis()));
            String verifyCode = generateVerifyCode(4, null);
            System.out.println("verifyCode:"+ verifyCode);
            File file = new File(dir, verifyCode + ".jpg");
            outputImage(w, h, file, verifyCode);
            System.out.println("生成完验证码："+new Date(System.currentTimeMillis()));
        }

    }

}
