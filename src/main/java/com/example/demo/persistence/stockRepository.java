package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.stockEntity;

import java.util.List;

@Repository
public interface stockRepository extends JpaRepository<stockEntity, Integer>{
	List<stockEntity> findAll();
}