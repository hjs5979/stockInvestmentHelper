package com.example.demo.dto;

import com.example.demo.model.newsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class newsDTO {
	private Integer id;
	private String category;
	private String title;
	private String url;
	
	public newsDTO(final newsEntity entity) {
		this.id = entity.getId();
		this.category = entity.getCategory();
		this.title = entity.getTitle();
		this.url = entity.getUrl();
	}
	
	public static newsEntity toEntity(final newsDTO dto) {
		return newsEntity.builder()
				.id(dto.getId())
				.category(dto.getCategory())
				.title(dto.getTitle())
				.build();
	}
}
