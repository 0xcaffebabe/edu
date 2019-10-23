package wang.ismy.edu.manage.media.service;

import com.mongodb.ParallelScanOptions;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.domain.media.MediaFile;
import wang.ismy.edu.domain.media.request.QueryMediaFileRequest;
import wang.ismy.edu.manage.media.dao.MediaFileRepository;

/**
 * @author MY
 * @date 2019/10/23 13:17
 */
@Service
@AllArgsConstructor
public class MediaFileService {

    private MediaFileRepository mediaFileRepository;

    public QueryResponseResult findList(Integer page, Integer size, QueryMediaFileRequest params) {
        if (params == null){
            params = new QueryMediaFileRequest();
        }
        MediaFile mediaFile = new MediaFile();
        if (!StringUtils.isEmpty(params.getTag())){
            mediaFile.setTag(params.getTag());
        }

        if (!StringUtils.isEmpty(params.getFileOriginalName())){
            mediaFile.setFileOriginalName(params.getFileOriginalName());
        }

        if (!StringUtils.isEmpty(params.getProcessStatus())){
            mediaFile.setProcessStatus(params.getProcessStatus());
        }
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<MediaFile> example = Example.of(mediaFile, exampleMatcher);
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
        Page<MediaFile> all = mediaFileRepository.findAll(example,PageRequest.of(page-1, size));
        QueryResult<MediaFile> queryResult = new QueryResult<>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());
        return new QueryResponseResult<>(CommonCode.SUCCESS,queryResult);
    }
}
