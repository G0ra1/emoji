package com.netwisd.base.file.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.dto.FileInfoDto;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.service.FileInfoServiceFactory;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.base.file.vo.FileInfoVoForVideo;
import com.netwisd.base.file.vo.FileResultVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 云数网讯 zouliming@netwisd.com
 * @Description 文件存储 功能描述...
 * @date 2020-02-09 12:27:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/fileinfo")
@Api(value = "fileinfo", tags = "文件存储Controller")
@Slf4j
public class FileInfoController {
    private final static String defaultStoreType = "default";

    @Autowired
    private FileInfoServiceFactory fileInfoServiceFactory;

    /**
     * 文件台账查询
     *
     * @param fileInfoDto
     * @return Result
     */
    @ApiOperation(value = "文件台账查询", notes = "文件台账查询")
    @PostMapping("/list")
    public Result<Page> list(@RequestBody FileInfoDto fileInfoDto) {
        Page<FileInfoVo> result = fileInfoServiceFactory.getFileInfoService(FileStoreTypeEnum.DEFAULT.name()).getFileInfoService().list(fileInfoDto);
        log.debug("查询成功");
        return Result.success(result);
    }

    /**
     * 通过id查询文件存储
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public Result<FileInfoVo> getById(@PathVariable("id") Long id) {
        FileInfoVo fileInfoVo = fileInfoServiceFactory.getFileInfoService(FileStoreTypeEnum.DEFAULT.name()).getFileInfoService().getFileInfoById(id);
        log.debug("查询成功");
        return Result.success(fileInfoVo);
    }

    /**
     * 通过id集合查询文件存储
     *
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id集合查询", notes = "通过id集合查询")
    @GetMapping("/getByIds")
    public Result<List<FileInfoVo>> getByIds(@RequestParam(value = "ids", required = true) String ids) {
        Collection<Long> idList = new ArrayList<>();
        Collection<String> strList = new ArrayList<>();
        if (StrUtil.isNotEmpty(ids)) {
            CollectionUtil.addAll(strList, ids.split(","));
            strList.forEach(strids -> {
                CollectionUtil.addAll(idList, strids);
            });
        } else {
            return Result.failed("id不能为空！");
        }
        List<FileInfoVo> list = fileInfoServiceFactory.getFileInfoService(FileStoreTypeEnum.DEFAULT.name()).getFileInfoService().getFileInfoList(idList);
        log.debug("查询成功");
        return Result.success(list);
    }

    /**
     * 新增文件存储
     * @param file
     * @param fileSource 文件来源，用枚举列出业务类型
     * @see com.netwisd.base.common.constants.FileSource
     * @return
     */
    /*@ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping
    public Result<FileInfoVo> upload(@RequestParam("file") MultipartFile file,@RequestParam("fileSource") String fileSource) {
        FileInfoVo fileInfoVo = fileInfoServiceFactory.getFileInfoService(fileStoreConfig.getStoreType()).upload(file,fileSource);
        log.debug("保存成功");
        return Result.success(fileInfoVo);
    }*/

    /**
     * 新增文件存储
     *
     * @param file
     * @param fileSource 文件来源，用枚举列出业务类型
     * @return
     * @see com.netwisd.base.common.constants.FileSource
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping
    public Result<FileInfoVo> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileSource") String fileSource, @RequestParam(value = "poolName", required = false, defaultValue = defaultStoreType) String poolName) {
        FileResultVo fileResultVo = fileInfoServiceFactory.getFileInfoService(poolName);
        FileInfoVo fileInfoVo = fileResultVo.getFileInfoService().upload(file, fileResultVo.getPoolName(), fileSource);
        log.debug("保存成功");
        return Result.success(fileInfoVo);
    }

    /**
     * 新增文件存储
     *
     * @param file
     * @param fileSource 文件来源，用枚举列出业务类型
     * @return
     * @see com.netwisd.base.common.constants.FileSource
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping("/videoOrMusic")
    public Result<FileInfoVoForVideo> uploadForVideo(@RequestParam("file") MultipartFile file, @RequestParam("fileSource") String fileSource, @RequestParam(value = "poolName", required = false, defaultValue = defaultStoreType) String poolName) throws Exception {
        FileResultVo fileResultVo = fileInfoServiceFactory.getFileInfoService(poolName);
        FileInfoVo fileInfoVo = fileResultVo.getFileInfoService().upload(file, fileResultVo.getPoolName(), fileSource);
        //如果是视频或者音频 返回时长（分钟）
        FileInfoVoForVideo fileInfoVoForVideo = new FileInfoVoForVideo();
        BeanUtils.copyProperties(fileInfoVo, fileInfoVoForVideo);
        if (fileInfoVo.getFileContentType().contains("audio") || fileInfoVo.getFileContentType().contains("video")) {
            File file1 = new File(fileInfoVo.getFilePath());
            MultimediaObject multimediaObject = new MultimediaObject(file1);
            MultimediaInfo info = multimediaObject.getInfo();
            long ms = info.getDuration();
            long min = ms / 1000 / 60;
            long s = ms / 1000 % 60;
            fileInfoVoForVideo.setMillisecond(ms);
            fileInfoVoForVideo.setSecond(s);
            fileInfoVoForVideo.setDurationText(min + ":" + s);
        }
        log.debug("保存成功");
        return Result.success(fileInfoVoForVideo);
    }

    /**
     * 通过id删除文件存储
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除文件存储", notes = "通过id删除文件存储")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable Long id) {
        Boolean result = false;
        if (ObjectUtil.isNotEmpty(id)) {
            result = fileInfoServiceFactory.getFileInfoService(getPoolName(id)).getFileInfoService().removeById(id);
            log.debug("删除成功");
        } else {
            log.error("参数错误");
            throw new IncloudException("参数错误");
        }
        return Result.success(result);
    }

    /**
     * 根据ID先获取下，是什么存储方式
     *
     * @param id
     * @return
     */
    String getPoolName(Long id) {
        FileInfo fileInfo = fileInfoServiceFactory.getFileInfoService(FileStoreTypeEnum.DEFAULT.name()).getFileInfoService().getById(id);
        if (ObjectUtil.isNotEmpty(fileInfo)) {
            return fileInfo.getFilePoolName();
        }
        return null;
    }

    /**
     * 通过id获取文件流
     * @param id
     */
    /*@ApiOperation(value = "通过id获取文件流", notes = "通过id获取文件流")
    @GetMapping("/stream")
    @SneakyThrows
    public ResponseEntity getFile(@RequestParam Long id) {
        File file = fileInfoServiceFactory.getFileInfoService(minioFileStoreType.getStoreType()).getObjectById(id);
        String fileName = URLEncoder.encode(file.getName(),"UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_ENCODING,"UTF-8");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",  file.getName()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }*/

    /**
     * 通过id获取文件流
     *
     * @param id
     */
    @ApiOperation(value = "通过id获取文件流", notes = "通过id获取文件流")
    @GetMapping("/stream")
    @SneakyThrows
    public void getFiles(@RequestParam Long id, HttpServletResponse response, HttpServletRequest request) {
        InputStream inputStream = null;
        try {
            Map<String, InputStream> map = fileInfoServiceFactory.getFileInfoService(getPoolName(id)).getFileInfoService().getStream(id);
            String fileName = (String) (map.keySet().toArray())[0];
            inputStream = map.get(fileName);
            String userAgent = request.getHeader("User-Agent");
            //解决乱码MSIE IE 8 至 IE 10 ,Trident/7.0:IE 11
            if (userAgent.toUpperCase().contains("MSIE") || userAgent.contains("Trident/7.0")) {
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取异常", e);
            throw new IncloudException("文件读取异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 新增文件记录
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "新增文件记录", notes = "新增文件记录")
    @PostMapping("/saveFileInfo")
    public Result<Boolean> saveFileInfo(@RequestBody FileInfoDto infoDto) {
        return Result.success(fileInfoServiceFactory.getFileInfoService(FileStoreTypeEnum.DEFAULT.name()).getFileInfoService().saveFileInfo(infoDto));
    }

    /**
     * 删除文件记录
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除文件记录", notes = "删除文件记录")
    @DeleteMapping("/deleteFileInfo/{id}")
    public Result<Boolean> deleteFileInfo(@PathVariable("id") Long id) {
        return Result.success(fileInfoServiceFactory.getFileInfoService(FileStoreTypeEnum.DEFAULT.name()).getFileInfoService().deleteFileInfo(id));
    }
}