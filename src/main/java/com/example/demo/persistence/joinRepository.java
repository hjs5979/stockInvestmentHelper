package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.model.newswordsEntity;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.mainEntity;

import java.util.List;

@Repository
public interface joinRepository extends JpaRepository<newswordsEntity, Integer>{
	List<newswordsEntity> findByWordsId(Integer wordsId);
}