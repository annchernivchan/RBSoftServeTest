package application.controllers;

import application.dto.FileDto;
import application.mapper.MapperManager;
import application.model.FileResource;
import application.service.FileResourceService;
import application.service.UserDetailsService;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileResourceService fileResourceService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET, produces = "application/pdf;charset=UTF-8")
    public void download(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        FileResource fileResource = fileResourceService.findFileById(fileId);
        File file = Paths.get(fileResourceService.getUploadRootPath(), fileResource.getRelativePath()).toFile();

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + encodeToUTF8(fileResource.getName()));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String upload(@RequestParam("files") MultipartFile[] files) throws IOException {
        User principal = UserDetailsService.getPrincipal();
        application.model.User user = userService.findByUsername(principal.getUsername());

        for (MultipartFile file : files) {
            fileResourceService.saveFile(user, file.getBytes(), file.getOriginalFilename());
        }

        return null;
    }

    @PostMapping("/delete/{fileId}")
    public String delete(@PathVariable("fileId") String fileId) {
        fileResourceService.deleteFileById(fileId);
        return null;
    }

    @PostMapping("/getAll")
    public List<FileDto> getAll() {
        User principal = UserDetailsService.getPrincipal();
        List<FileResource> files = fileResourceService.findFilesByNickname(principal.getUsername());

        return MapperManager.FILE_MAPPER.toDto(files);
    }

    private String encodeToUTF8(String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, "UTF-8").replaceAll("\\+", "%20");
    }

}