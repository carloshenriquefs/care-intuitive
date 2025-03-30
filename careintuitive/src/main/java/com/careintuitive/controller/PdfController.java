package com.careintuitive.controller;

import com.careintuitive.service.CsvWriteService;
import com.careintuitive.service.PdfExtractorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.careintuitive.constants.Constants.ARQUIVO_PROCESSADO;
import static com.careintuitive.constants.Constants.ERRO_NO_PROCESSO;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfExtractorService pdfExtractorService = new PdfExtractorService();
    private final CsvWriteService csvWriteService = new CsvWriteService();

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file) {
        try {
            File tempFile = File.createTempFile("temp", ".pdf");
            file.transferTo(tempFile);

            List<String[]> extractedData = pdfExtractorService.extractTableFromPdf(tempFile.getPath());
            String csvFilePath = "output.csv";
            csvWriteService.writeCsv(csvFilePath, extractedData);

            return ARQUIVO_PROCESSADO + csvFilePath;
        } catch (IOException e) {
            return ERRO_NO_PROCESSO + e.getMessage();
        }
    }
}
