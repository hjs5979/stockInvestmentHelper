package com.sih.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
	
}