package com.readingcsv.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.readingcsv.demo.model.FileData;
import com.readingcsv.demo.repository.FileDataRepository;

@Service
public class FileDataService {
	
	FileDataRepository repo;
	
	public FileDataService(FileDataRepository repo) {
		super();
		this.repo = repo;
	}

	public void storeData(List<FileData> fileDatas) {
		// TODO Auto-generated method stub
		
		repo.saveAll(fileDatas);
		
	}

}
