package com.winter.app.board;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@MappedSuperclass  // 부모임을 명시
public class BoardVO {

	@Id  // PK로 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
	private Long boardNum;
	
	@Column(name = "boardTitle", nullable = false, unique = true, length = 255, insertable = true, updatable = true )
	private String boardTitle;
	private String boardWriter;
	// @Column(columnDefinition = "LONGTEXT")   이거 쓰거나 아래 @Lob 둘 중에 하나
	@Lob  // long text
	private String boardContents;
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private LocalDateTime boardDate;
//	@Column(columnDefinition = "BIGINT DEFAULT 0", insertable = false)
	@Column
	@ColumnDefault(value = "0")
	private Long boardHit;
	
	// db에서 제외
	@Transient
	private String kind;
}
