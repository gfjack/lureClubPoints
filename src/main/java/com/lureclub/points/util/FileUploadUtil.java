package com.lureclub.points.util;

import com.lureclub.points.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author system
 * @date 2025-06-19
 */
@Component
public class FileUploadUtil {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.prize-images}")
    private String prizeImagesPath;

    // 允许的图片格式
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    // 最大文件大小 (10MB)
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 上传奖品图片
     *
     * @param file 图片文件
     * @return 图片URL
     */
    public String uploadPrizeImage(MultipartFile file) {
        // 验证文件
        validateImageFile(file);

        // 修复：正确拼接路径
        String fullPath = buildUploadPath(uploadPath, prizeImagesPath);
        createDirectoryIfNotExists(fullPath);

        // 生成文件名
        String fileName = generateFileName(file.getOriginalFilename());

        // 保存文件
        try {
            Path filePath = Paths.get(fullPath, fileName);
            Files.copy(file.getInputStream(), filePath);

            // 修复：返回正确的访问URL
            return buildUrlPath(prizeImagesPath, fileName);

        } catch (IOException e) {
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 修复：构建上传路径
     */
    private String buildUploadPath(String basePath, String subPath) {
        // 移除路径前后的斜杠并重新组合
        String cleanBasePath = basePath.replaceAll("^/+|/+$", "");
        String cleanSubPath = subPath.replaceAll("^/+|/+$", "");

        if (cleanBasePath.isEmpty()) {
            return File.separator + cleanSubPath;
        }

        return File.separator + cleanBasePath + File.separator + cleanSubPath;
    }

    /**
     * 修复：构建URL路径
     */
    private String buildUrlPath(String subPath, String fileName) {
        // 确保子路径以/开头，不以/结尾
        String cleanSubPath = subPath.replaceAll("^/+|/+$", "");
        return "/" + cleanSubPath + "/" + fileName;
    }

    /**
     * 验证图片文件
     *
     * @param file 文件
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException("文件大小不能超过10MB");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new BusinessException("只支持jpg、png、gif、webp格式的图片");
        }

        // 检查文件扩展名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !hasValidImageExtension(originalFilename)) {
            throw new BusinessException("文件扩展名不正确");
        }
    }

    /**
     * 检查文件扩展名
     *
     * @param filename 文件名
     * @return 是否有效
     */
    private boolean hasValidImageExtension(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return Arrays.asList("jpg", "jpeg", "png", "gif", "webp").contains(extension);
    }

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 生成唯一文件名
     *
     * @param originalFilename 原始文件名
     * @return 新文件名
     */
    private String generateFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return timestamp + "_" + uuid + "." + extension;
    }

    /**
     * 创建目录（如果不存在）
     *
     * @param path 目录路径
     */
    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new BusinessException("创建上传目录失败: " + path);
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return 是否删除成功
     */
    public boolean deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return true;
        }

        try {
            // 修复：正确构建完整文件路径
            String fullPath = buildUploadPath(uploadPath, fileUrl);
            Path filePath = Paths.get(fullPath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

}