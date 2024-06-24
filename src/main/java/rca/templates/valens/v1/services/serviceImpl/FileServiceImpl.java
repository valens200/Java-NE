package rca.templates.valens.v1.services.serviceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import rca.templates.valens.v1.services.IFileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements IFileService {

    @Value("${upload.dir.prod")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + fileExtension;
        Path uploadPath = Paths.get(uploadDir);

        if (!uploadPath.toFile().exists()) {
            uploadPath.toFile().mkdirs();
        }

        File destination = new File(uploadPath.toFile(), newFileName);
        file.transferTo(destination);

        return newFileName;
    }

    public String updateFile(String oldFileName, MultipartFile newFile) throws IOException {
        if (newFile.isEmpty()) {
            return oldFileName;
        }
        // Delete the old file
        if (oldFileName != null) {
            Path oldFilePath = Paths.get(uploadDir, oldFileName);
            File oldFile = oldFilePath.toFile();
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }

        return uploadFile(newFile);
    }

    public void removeFile(String fileName) {
        if (fileName != null) {
            Path filePath = Paths.get(uploadDir, fileName);
            File file = filePath.toFile();
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public File getFile(String fileName) {
        if (fileName != null) {
            Path filePath = Paths.get(uploadDir, fileName);
            File file = filePath.toFile();
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
}