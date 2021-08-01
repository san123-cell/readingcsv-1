package com.readingcsv.demo.repository;
import org.springframework.data.repository.CrudRepository;

import com.readingcsv.demo.model.FileData;
public interface FileDataRepository extends CrudRepository<FileData, Long>{

}
