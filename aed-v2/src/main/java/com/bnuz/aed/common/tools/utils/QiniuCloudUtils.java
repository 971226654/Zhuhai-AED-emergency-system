package com.bnuz.aed.common.tools.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Leia Liang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QiniuCloudUtils {

    /** 设置需要操作的账号的AK和SK */
    private static final String ACCESS_KEY = "Eb4JJq-S0DHzVvLfviEEPfcYSZtSq5-GqI0rarcM";
    private static final String SECRET_KEY = "kThyvsFiFpgMLr4ayCpVuVC10Z3zKB53cJb-1kSl";

    /** 空间名 */
    private static final String bucketName = "zhuhaiaed";

    /** 图片上传路径 */
    public static final String DOMAIN = "qiniu.zhuhaiaed.xyz";

    /** 密钥 */
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    /** 创建地区 */
    private Configuration cfg = new Configuration(Region.huanan());
    /** 上传对象 */
    private UploadManager uploadManager = new UploadManager(cfg);

    /** 可接收的后缀名(暂时为图片) */
    private static String[] FILE_SUFFIX = new String[]{"png", "bmp", "jpg", "jpeg",
            "pdf", "mp4", "flv"};

    /** 判断是否是允许的格式 */
    private static boolean isFileAllowed(String fileName) {
        for (String suffix : FILE_SUFFIX) {
            if (suffix.equals(fileName)) {
                return true;
            }
        }
        return false;
    }

    /** 自定义图片样式 */
    private static final String style = "";

    /** 获取上传凭证 */
    public StringMap getUpToken() {
        //自定义上传回复
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        return putPolicy;
    }

    /** 普通上传 */
    public String uploadToQiniu(MultipartFile file) throws IOException {
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
        System.out.println("后缀：" + fileExt);
        if (!isFileAllowed(fileExt)) {
            System.out.println("文件后缀名不合法！");
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        try {
            //调用put方法上传
            String token = auth.uploadToken(bucketName, null, 3600, getUpToken());
            if (token.isEmpty()) {
                System.out.println("未获取到token，请重试！");
                return null;
            }
            Response res = uploadManager.put(file.getBytes(), fileName, token);
            //打印返回信息
            System.out.println(res.bodyString());
            if (res.isOK()) {
                Ret ret = res.jsonToObject(Ret.class);
                //如果不需要对图片进行样式处理，则使用一下方式即可
                return DOMAIN + ret.key;
                //return DOMAIN + ret.key + "?" + style;
            }
        } catch (QiniuException e) {
            Response r = e.response;
            //请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                // ignore
            }
        }
        return null;
    }

    /** 单个文件删除 */
    public int deleteFromQiniu(String key) {
        // 实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // 去掉DOMAIN
        key = key.substring(DOMAIN.length());
        try {
            // 调用delete方法移动文件
            Response delete = bucketManager.delete(bucketName, key);
            return delete.statusCode;
        } catch (QiniuException e) {
            // 打印的异常的信息
            Response r = e.response;
            System.out.println(e.code());
            System.out.println(r.toString());
        }
        return -1;
    }


    class Ret {
        public long fsize;
        public String key;
        public String hash;
        public int width;
        public int height;
    }

}
