package com.mitocode.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "media_file")
public class MediaFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idFile;
	private String fileName;
	private String fileType;
	private byte[] value;
}