package com.example.demo.dto;

import com.example.demo.model.mainEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class mainDTO {
	private Integer id;
	private String word;
	private Integer count;
	
	public mainDTO(final mainEntity entity) {
		this.id = entity.getId();
		this.word = entity.getWord();
		this.count = entity.getCount();
	}
	
	public static mainEntity toEntity(final mainDTO dto) {
		return mainEntity.builder()
				.id(dto.getId())
				.word(dto.getWord())
				.count(dto.getCount())
				.build();
	}
}
