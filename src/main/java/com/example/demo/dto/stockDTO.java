package com.example.demo.dto;

import com.example.demo.model.stockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class stockDTO {
	private Integer id;
	private String thema;
	private String value;
	
	public stockDTO(final stockEntity entity) {
		this.id = entity.getId();
		this.thema = entity.getThema();
		this.value = entity.getValue();
	}
	
	public static stockEntity toEntity(final stockDTO dto) {
		return stockEntity.builder()
				.id(dto.getId())
				.thema(dto.getThema())
				.value(dto.getValue())
				.build();
	}
}

