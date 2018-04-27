package com.yjy.test.util.hibernate;

/**
 * 处理比较的查询
 * @author wdy
 * @version ：2011-12-20 下午02:51:44
 */
@SuppressWarnings("serial")
public class CompareBy extends Condition {

	private CompareType compareType;
	private Object fieldValue;

	protected CompareBy(String field, Object fieldValue,CompareType compareType) {
		this.field = field;
		this.compareType = compareType;
		this.fieldValue = fieldValue;
	}

	public static CompareBy gt(String field, Object fieldValue) {
		return new CompareBy(field, fieldValue, CompareType.GT);
	}

	public static CompareBy lt(String field, Object fieldValue) {
		return new CompareBy(field,fieldValue, CompareType.LT);
	}
	
	public static CompareBy eq(String field, Object fieldValue) {
		return new CompareBy(field, fieldValue, CompareType.EQ);
	}

	public CompareBy getOrder() {
		CompareBy compareBy = null;
		if (CompareType.GT == compareType) {
			compareBy = CompareBy.gt(getField(),getFieldValue());
		} else if (CompareType.LT == compareType) {
			compareBy = CompareBy.lt(getField(),getFieldValue());
		}else {
			compareBy = CompareBy.eq(getField(),getFieldValue());
		}
		return compareBy;
	}

	public CompareType getCompareType() {
		return compareType;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

	public void setCompareType(CompareType compareType) {
		this.compareType = compareType;
	}

	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public static enum CompareType {
		GT, LT, EQ
	}
	
}
