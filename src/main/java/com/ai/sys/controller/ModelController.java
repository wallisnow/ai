package com.ai.sys.controller;

import com.ai.sys.common.Response;
import com.ai.sys.model.Model;
import com.ai.sys.service.ModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/model")
public class ModelController {
    private final ModelService modelService;

    @GetMapping("/all")
    public List<Model> findAllModel() {
        return modelService.findAll();
    }

    @DeleteMapping("/delete/**")
    public Response deleteModel(HttpServletRequest request) {
        try {
            String path = request.getRequestURI().split(request.getContextPath() + "/delete")[1];
            boolean ok = modelService.deleteByPath(path);
            if (ok) {
                return Response.httpOk("删除成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.httpError("删除失败， 请确认文件是否存在");
    }

    //TODO jun: to improve
    @RequestMapping("/download/{path}")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("path") String path) throws IOException {
        File file = new File(path.replaceAll("\\+", "/"));
        if (file.exists()) {

            //get the mimetype
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             *
             */

            /**
             * Here we have mentioned it to show inline
             */
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }
    }

}