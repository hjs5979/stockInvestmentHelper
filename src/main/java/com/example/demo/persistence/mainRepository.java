package com.example.demo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.mainEntity;

import java.util.List;

@Repository
public interface mainRepository extends JpaRepository<mainEntity, Integer>{
	List<mainEntity> findAll();
}