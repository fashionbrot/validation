package com.github.fashionbrot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.fashionbrot.annotation.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司职位表
 *
 * @author fashionbrot
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CompanyPostEntity implements Serializable {
    private static final long serialVersionUID = 1674301834656256002L;



	private Long id;


	private Long companyId;


	private String postName;


	private String postBrief;


	private String postDescription;


	private Long postClassificationId;

	@NotNull(message = "请选择工作经验")
	private Long workExperienceId;


	private Integer workExperienceLow;


	private Integer workExperienceHigh;


	private Integer education;


	private String provinceName;


	private String provinceCode;


	private String cityName;


	private String cityCode;


	private String areaCode;


	private String address;


	private String keyWords;


	private Integer salLow;


	private Integer salHigh;


	private Integer salMonths;


	private Integer status;


	private Integer postStatus;


	private Integer agreeServiceAgreement;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;


	private String remark;

	private String postClassificationName;

	private String workExperienceName;



	private String areaName;

	private String companyName;
}
