package wang.ismy.edu.manage.media.cotroller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.media.MediaFileControllerApi;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.media.request.QueryMediaFileRequest;
import wang.ismy.edu.manage.media.service.MediaFileService;

/**
 * @author MY
 * @date 2019/10/23 13:16
 */
@RestController
@RequestMapping("media/file")
@AllArgsConstructor
public class MediaFileController implements MediaFileControllerApi {

    private MediaFileService mediaFileService;

    @GetMapping("list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable Integer page,@PathVariable Integer size, QueryMediaFileRequest params) {
        return mediaFileService.findList(page,size,params);
    }
}
