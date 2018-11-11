/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.download;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static java.net.URLEncoder.encode;

public class DownloadView implements View {

    private final String filename;
    private final String filenameEncoding;
    private final HttpStatus httpStatus;
    private final InputStream inputStream;

    public DownloadView(String filename, String filenameEncoding, HttpStatus httpStatus, InputStream inputStream) {
        this.filename = filename;
        this.filenameEncoding = filenameEncoding;
        this.httpStatus = httpStatus;
        this.inputStream = inputStream;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            response.setStatus(httpStatus.value());
            response.setHeader("Content-Disposition", "attachment; filename=" + encode(filename, filenameEncoding));
            IOUtils.copy(this.inputStream, response.getOutputStream());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // NOP
                }
            }
        }
    }

}
