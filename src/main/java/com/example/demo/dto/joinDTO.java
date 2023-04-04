package com.example.demo.dto;

import com.example.demo.model.newswordsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class joinDTO {
	private Integer id;
	private Integer wordsId;
	private Integer newsId;
	
	public joinDTO(final newswordsEntity entity) {
		this.id = entity.getId();
		this.wordsId = entity.getWordsId();
		this.newsId = entity.getNewsId();
	}
	
	public static newswordsEntity toEntity(final joinDTO dto) {
		return newswordsEntity.builder()
				.id(dto.getId())
				.wordsId(dto.getWordsId())
				.newsId(dto.getNewsId())
				.build();
	}
}
//
//
