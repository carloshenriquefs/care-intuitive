package com.careintuitive.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PdfExtractorService {

    public List<String[]> extractTableFromPdf(String filePath) throws IOException {
        File file = new File(filePath);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        return processTextToTable(text);
    }

    private List<String[]> processTextToTable(String text) {

        text = text.replace("OD", "OD: Seg. Odontológica");
        text = text.replace("AMB", "AMB: Seg. Ambulatorial");

        String[] lines = text.split("\n");
        return Arrays.stream(lines)
                .map(line -> line.split("\\s{2,}"))
                .toList();
    }
}
