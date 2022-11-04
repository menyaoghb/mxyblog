package com.mxy.system.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @Description 七牛云文件上传工具
 * @Author 孟小耀
 * @Date 2021/7/28 0028 20:07
 */
@Component
public class QiniuUploadUtil {

    private static String accessKey;
    @Value("${qny.accessKey}")
    public void setAccessKey(String accessKey) {
        QiniuUploadUtil.accessKey = accessKey;
    }
    private static String secretKey;
    @Value("${qny.secretKey}")
    public void setSecretKey(String secretKey) {
        QiniuUploadUtil.secretKey = secretKey;
    }
    private static String bucket;
    @Value("${qny.bucket}")
    public void setBucket(String bucket) {
        QiniuUploadUtil.bucket = bucket;
    }
    private static String prix;
    @Value("${qny.prix}")
    public void setPrix(String prix) {
        QiniuUploadUtil.prix = prix;
    }
    /**
     * @Description: 上传七牛云图片
     * @Param: [imgName, uploadBytes]
     * @return: java.lang.String
     * @Author: 孟耀
     * @Date: 2021/7/28
     */
    public static String upload(String imgName, byte[] uploadBytes) {
        String url = "";
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(uploadBytes, imgName, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                url = prix + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
            ex.printStackTrace();
        }
        return url;
    }


    /**
     * @Description: 删除七牛云图片
     * @Param: [key]
     * @return: void
     * @Author: 孟耀
     * @Date: 2021/7/28
     */
    public static void deleteImg(String key) {
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }


}
