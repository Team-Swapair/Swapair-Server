package com.swapair.server.excel;

import com.swapair.server.category.CategoryService;
import com.swapair.server.goods.GoodsRepository;
import com.swapair.server.post.Post;
import com.swapair.server.post.PostRepository;
import com.swapair.server.post.PostService;
import com.swapair.server.post.have.HaveGoods;
import com.swapair.server.post.have.HaveGoodsRepository;
import com.swapair.server.post.want.WantGoods;
import com.swapair.server.post.want.WantGoodsRepository;
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
    private final GoodsRepository goodsRepository;
    private final PostRepository postRepository;
    private final HaveGoodsRepository haveGoodsRepository;
    private final WantGoodsRepository wantGoodsRepository;


    @GetMapping("/excel")
    public String main() {
        return "excel";
    }

    @PostMapping("/excel/post/read")
    public String readPostExcel(@RequestParam("file") MultipartFile file, Model model)
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
            data.setPostId((long) row.getCell(0).getNumericCellValue());
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

            data.setCreatedAt(LocalDateTime.now());

            postService.createExcelPost(data);
        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/have/read")
    public String readHaveExcel(@RequestParam("file") MultipartFile file, Model model)
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
            HaveGoods data = new HaveGoods();

            if (row.getCell(0) == null) {
                break;
            }
            Long goodsId = (long) row.getCell(1).getNumericCellValue();
            data.setGoods(goodsRepository.findByGoodsId(goodsId));
            goodsId = (long) row.getCell(2).getNumericCellValue();
            data.setPost(postRepository.findById(goodsId).orElseThrow());

            haveGoodsRepository.save(data);

        }
        System.out.println("엑셀 완료");
        return "excel";
    }

    @PostMapping("/excel/want/read")
    public String readWantExcel(@RequestParam("file") MultipartFile file, Model model)
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
            WantGoods data = new WantGoods();

            if (row.getCell(0) == null) {
                break;
            }
            Long goodsId = (long) row.getCell(1).getNumericCellValue();
            data.setGoods(goodsRepository.findByGoodsId(goodsId));
            goodsId = (long) row.getCell(2).getNumericCellValue();
            data.setPost(postRepository.findById(goodsId).orElseThrow());

            wantGoodsRepository.save(data);

        }
        System.out.println("엑셀 완료");
        return "excel";
    }
}
