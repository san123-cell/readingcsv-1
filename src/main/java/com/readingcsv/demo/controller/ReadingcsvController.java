package com.readingcsv.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.readingcsv.demo.model.FileData;
import com.readingcsv.demo.service.FileDataService;

@Controller
public class ReadingcsvController {
	
	FileDataService fileDataService;
	
	
	public ReadingcsvController(FileDataService fileDataService) {
		super();
		this.fileDataService = fileDataService;
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws IOException {
		if(file.getOriginalFilename().endsWith(".csv")) {
			List<FileData> fileDatas=readFileData(file.getInputStream());
			System.out.println(file.getOriginalFilename() + " no of lines "+fileDatas.size());
			fileDataService.storeData(fileDatas);
		return "redirect:/Success.html";
		}else {
			return "redirect:/Failure.html";
		}
	}
	
    @GetMapping("/uplodedData")
    public String uplodedData() {
        return "uplodedData";
    }
    
    private List<FileData> readFileData(InputStream is) {
    	try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    	        CSVParser csvParser = new CSVParser(fileReader,
    	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

    	      List<FileData> fileDatas = new ArrayList<FileData>();

    	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

    	      for (CSVRecord csvRecord : csvRecords) {
    	    	  FileData fileData = new FileData();
    	    	  fileData.setId(csvRecord.getRecordNumber());
    	    	  fileData.setStudentName(csvRecord.get("studentname"));
    	    	  fileData.setDepartment(csvRecord.get("department"));
    	    	  fileData.setUserName(csvRecord.get("username"));
    	    	  fileData.setPassword(encryptPassword(csvRecord.get("password")));
    	    	  fileDatas.add(fileData);
    	      }

    	      return fileDatas;
    	    } catch (IOException e) {
    	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    	    }
    	  }

	private String encryptPassword(String input) {
		  StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] result = md.digest(input.getBytes());
			
		        for (byte b : result) {
		            sb.append(String.format("%02x", b));
		        }
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	        return sb.toString();
	}
    	
    

}


