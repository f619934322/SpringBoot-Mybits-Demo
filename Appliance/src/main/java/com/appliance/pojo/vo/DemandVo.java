package com.appliance.pojo.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class DemandVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 采购需求表id
	 */
	private Long id;

	/**
	 * 库存表id
	 */
	private Long inventoryId;

	/**
	 * 物品名称
	 */
	private String itemName;
	
	/**
	 * 补充或新增标识
	 */
	private int addedFlag;

	/**
	 * 补充或新增标识字符串（用于导出显示）
	 */
	private String addedFlagStr;
	
	/**
	 * 采购需求数量
	 */
	private int itemCount;

	/**
	 * 物品类型
	 */
	private String itemType;

	/**
	 * 采购金额
	 */
	private BigDecimal purchasePrice;

	/**
	 * 审核状态(0未审核，1驳回，2已通过（等待采购），3采购失败，4采购完成)
	 */
	private int status;

	/**
	 * 审核人
	 */
	private String reviewer;

	/**
	 * 审核时间
	 */
	private String reviewTime;

	/**
	 * 审核备注
	 */
	private String reviewCommit;

	/**
	 * 采购需求原因
	 */
	private String commit;

	/**
	 * 采购需求提起人
	 */
	private String creator;

	/**
	 * 更新人
	 */
	private String updator;

	/**
	 * 采购需求发起时间
	 */
	private String createTime;

	/**
	 * 更新时间
	 */
	private String updateTime;

}
