package wang.ismy.edu.base_filesystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.api.filesystem.FileSystemControllerApi;
import wang.ismy.edu.base_filesystem.service.FileSystemService;
import wang.ismy.edu.domain.filesystem.response.UploadFileResult;

/**
 * @author MY
 * @date 2019/10/15 19:41
 */
@RestController
@RequestMapping("filesystem")
@AllArgsConstructor
public class FileSystemController implements FileSystemControllerApi {

    private FileSystemService fileSystemService;

    @PostMapping("upload")
    @Override
    public UploadFileResult upload(MultipartFile file, String businesskey, String filetag, String metadata) {
        return fileSystemService.upload(file, businesskey, filetag, metadata);
    }
}
