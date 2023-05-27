package com.nw.fileuploaddownload;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@JsonSerialize
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "files")
public class Document implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, unique = true)
	@Check(constraints = ".txt OR .csv")
	private String name;
	
	private long size;
	@JsonIgnore
	@Column(name = "upload_time")
	private Date uploadTime;
	
	// mediumblob
	@JsonIgnore
	@Column(length = 102400)
	private byte[] content;
	
	public Document(long id, String name, long size) {
		this.id = id;
		this.name = name;
		this.size = size;
	}

}
