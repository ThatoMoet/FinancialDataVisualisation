package com.ThatoMoet.controller;

import com.ThatoMoet.entities.FinancialRecords;
import com.ThatoMoet.entities.User;
import com.ThatoMoet.repository.FinancialDataRepository;
import com.ThatoMoet.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200") 

@RestController
@RequestMapping("/api/finances")
public class MyRestController {
    private final FinancialDataRepository financialDataRepository;
    private final UserRepository userRepository;

    public MyRestController(FinancialDataRepository financialDataRepository, UserRepository userRepository) {
        this.financialDataRepository = financialDataRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/upload/{userId}/{year}")
    public ResponseEntity<String> handleFileUpload(@PathVariable Long userId, @PathVariable int year,
                                                   @RequestParam MultipartFile excelFile) {

//        System.out.println("Received"+ file.getOriginalFilename());
        if (excelFile.isEmpty()) {
            return ResponseEntity.badRequest().body("File is Empty.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        try (var workbook = WorkbookFactory.create(excelFile.getInputStream());) {
            var sheet = workbook.getSheetAt(0);
            List<FinancialRecords> records = new ArrayList<>();

            List<FinancialRecords> existingRecords = financialDataRepository.findByUserAndYear(user, year);
            if (!existingRecords.isEmpty()) {
                financialDataRepository.deleteAll(existingRecords);
            }
            int lastRowNum = sheet.getLastRowNum();

            for (int i =1 ; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;


                Cell monthCell = row.getCell(0);
                Cell amountCell = row.getCell(1);

                if (monthCell == null || amountCell == null) {
                    continue;
                }

                if (monthCell.getCellType() == CellType.BLANK && amountCell.getCellType() == CellType.BLANK) {
                    continue;
                }

                try {
                    String month = Objects.requireNonNull(monthCell).getStringCellValue();
                    BigDecimal amount = BigDecimal.valueOf(amountCell.getNumericCellValue());

                    FinancialRecords record = new FinancialRecords();
                    record.setUser(user);
                    record.setYear(year);
                    record.setMonth(month);
                    record.setAmount(amount);

                    records.add(record);
                } catch (Exception e) {
                    System.err.println("Error loading row " + i + e.getMessage());
                }
            }
            if (records.isEmpty()) {
                return ResponseEntity.badRequest().body("No valid records found.");
            }
            financialDataRepository.saveAll(records);
            return ResponseEntity.ok("File uploaded successfully "+ records.size()+ " records");


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading file "+ e.getMessage());
        }



    }
    @GetMapping("/{userId}/{year}")
    public ResponseEntity<List<FinancialRecords>> getRecords(@PathVariable Long userId,
                                                             @PathVariable int year){
        User  user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<FinancialRecords> records = financialDataRepository.findByUserAndYear(user,year);
        return ResponseEntity.ok(records);

    }


    private String getCellAsString(Cell cell){
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
    }}
}

