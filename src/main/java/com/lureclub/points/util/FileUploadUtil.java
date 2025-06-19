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
 * 文件上传工具类（完整修复版）
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

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "jpg", "jpeg", "png", "gif", "webp"
    );

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    /**
     * 上传奖品图片
     */
    public String uploadPrizeImage(MultipartFile file) {
        validateImageFile(file);

        String uploadDir = buildUploadDirectory();
        createDirectoryIfNotExists(uploadDir);

        String fileName = generateFileName(file.getOriginalFilename());

        try {
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath);
            return buildAccessUrl(fileName);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 构建上传目录路径
     */
    private String buildUploadDirectory() {
        String cleanBasePath = cleanPath(uploadPath);
        String cleanSubPath = cleanPath(prizeImagesPath);

        if (cleanBasePath.isEmpty()) {
            return cleanSubPath;
        }

        return cleanBasePath + File.separator + cleanSubPath;
    }

    /**
     * 构建访问URL
     */
    private String buildAccessUrl(String fileName) {
        String cleanSubPath = cleanPath(prizeImagesPath);

        if (cleanSubPath.isEmpty()) {
            return "/" + fileName;
        }

        return "/" + cleanSubPath.replace(File.separator, "/") + "/" + fileName;
    }

    /**
     * 清理路径
     */
    private String cleanPath(String path) {
        if (path == null) {
            return "";
        }
        return path.replaceAll("^[\\/\\\\]+|[\\/\\\\]+$", "");
    }

    /**
     * 验证图片文件
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException("文件大小不能超过10MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new BusinessException("只支持jpg、png、gif、webp格式的图片");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !hasValidImageExtension(originalFilename)) {
            throw new BusinessException("文件扩展名不正确");
        }

        if (containsUnsafeCharacters(originalFilename)) {
            throw new BusinessException("文件名包含不安全字符");
        }
    }

    /**
     * 检查文件名安全性
     */
    private boolean containsUnsafeCharacters(String filename) {
        if (filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            return true;
        }
        return filename.chars().anyMatch(c -> c < 32 || c == 127);
    }

    /**
     * 检查文件扩展名
     */
    private boolean hasValidImageExtension(String filename) {
        String extension = getFileExtension(filename).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 生成安全的文件名
     */
    private String generateFileName(String originalFilename) {
        String extension = getFileExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String safeFileName = "prize_" + timestamp + "_" + uuid.substring(0, 8);
        return safeFileName + "." + extension;
    }

    /**
     * 创建目录
     */
    private void createDirectoryIfNotExists(String path) {
        try {
            Path directory = Paths.get(path);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
        } catch (IOException e) {
            throw new BusinessException("创建上传目录失败: " + e.getMessage());
        }
    }

    /**
     * 删除文件
     */
    public boolean deleteFile(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return true;
        }

        try {
            String fileName = extractFileNameFromUrl(fileUrl);
            if (fileName == null) {
                return false;
            }

            String uploadDir = buildUploadDirectory();
            Path filePath = Paths.get(uploadDir, fileName);

            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 从URL中提取文件名
     */
    private String extractFileNameFromUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return null;
        }

        String[] parts = fileUrl.replace("\\", "/").split("/");
        if (parts.length > 0) {
            String fileName = parts[parts.length - 1];
            if (!containsUnsafeCharacters(fileName)) {
                return fileName;
            }
        }

        return null;
    }

    /**
     * 检查文件是否存在
     */
    public boolean fileExists(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return false;
        }

        try {
            String fileName = extractFileNameFromUrl(fileUrl);
            if (fileName == null) {
                return false;
            }

            String uploadDir = buildUploadDirectory();
            Path filePath = Paths.get(uploadDir, fileName);

            return Files.exists(filePath);
        } catch (Exception e) {
            return false;
        }
    }
}