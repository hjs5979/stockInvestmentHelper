package com.sih.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.sih.dao.dto.AtchDtlDto;
import com.sih.dao.dto.AtchDto;
import com.sih.dao.dto.BoardDto;
import com.sih.dao.dto.BoardInqDto;
import com.sih.dao.dto.DetailDto;
import com.sih.dao.dto.NewsDto;
import com.sih.dao.dto.NewsWordDto;
import com.sih.dao.dto.StockDto;
import com.sih.dao.dto.WordDto;

@Mapper
public interface SihDao {
	
	public List<StockDto> selectStockList();
	
	public List<NewsDto> selectNewsList(NewsDto NewsDto);
	
	public List<NewsWordDto> selectNewsWordList(NewsWordDto NewsWordDto);
	
	public List<WordDto> selectWordList();
	
	public List<DetailDto> selectDetailList(WordDto WordDto);
	
	public List<BoardDto> selectBoardList(BoardInqDto boardInqDto);
	
	public Integer countBoardList(BoardInqDto boardInqDto);
	
	public BoardDto selectBoardDetail(BoardDto boardDto);
	
	public void insertBoardDetail(BoardDto boardDto);
	
	public BigInteger lastInsertId();
	
	public void insertAtchBase(AtchDto atchDto);
	
	public void insertAtchDtl(AtchDtlDto atchDtlDto);
	
	public List<AtchDto> selectAtchDtl(AtchDto atchDto);
	
	public AtchDto selectAtchDnld(AtchDto atchDto);
	
	public void deleteAtchDtl(AtchDto atchDto);
	
	public void updateBoardDetail(BoardDto boardDto);
	
	public BoardDto selectForUpdateBoardDetail(BoardDto boardDto);
	
	public AtchDto selectAtchBase(AtchDto atchDto);
	
	public BigInteger selectNmbnAtchDtlno(BigInteger atchNo);
	
	public AtchDto selectForUpdateAtchBase(BigInteger atchNo);
	
	public void updateAtchBase(AtchDto atchDto);
	
}
