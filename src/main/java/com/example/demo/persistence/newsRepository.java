package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.example.demo.model.newsEntity;
import org.springframework.data.repository.query.Param;
import com.example.demo.model.mainEntity;

import java.util.List;

@Repository
public interface newsRepository extends JpaRepository<newsEntity, Integer>{
	List<newsEntity> findByIdIs(Integer newsId);
}