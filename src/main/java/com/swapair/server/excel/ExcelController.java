package com.swapair.server.excel;

import com.swapair.server.category.CategoryService;
import com.swapair.server.post.Post;
import com.swapair.server.post.PostService;
import com.swapair.server.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ExcelController {
    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;


    @GetMapping("/excel")
    public String main() {
        return "excel";
    }

    @PostMapping("/excel/post/read")
    public String readAreaExcel(@RequestParam("file") MultipartFile file, Model model)
            throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Post data = new Post();

            if (row.getCell(0) == null) {
                break;
            }
            Long userId = (long) row.getCell(1).getNumericCellValue();
            data.setUser(userService.getUser(userId));
            data.setPostTitle(row.getCell(2).getStringCellValue());
            data.setPostContent(row.getCell(3).getStringCellValue());
            Long categoryId = (long) row.getCell(4).getNumericCellValue();
            data.setPostCategory(categoryService.getCategory(categoryId));
            data.setWantImage(row.getCell(5).getStringCellValue());
            data.setHaveImage(row.getCell(6).getStringCellValue());
            if(row.getCell(7).getNumericCellValue() == 1)
                data.setIsClosed(true);
            else
                data.setIsClosed(false);

            if(row.getCell(8).getNumericCellValue() == 1)
                data.setIsChecked(true);
            else
                data.setIsChecked(false);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm:ss");
            LocalDateTime to = LocalDateTime.parse(row.getCell(9).getStringCellValue(), formatter);
            data.setCreatedAt(to);

            postService.createPost(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }
}
