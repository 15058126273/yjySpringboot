package com.yjy.test.util.hibernate;

/**
 * 查询条件的合并
 * @author wdy
 * @version ：2011-12-20 下午03:15:11
 */
@SuppressWarnings("serial")
public class MergerBy extends Condition {

	private MergerType mergerType;
	private Condition conditiona;
	private Condition conditionb;
	
	protected MergerBy(Condition conditiona, Condition conditionb, MergerType MergerType) {
		this.conditiona = conditiona;
		this.conditionb = conditionb;
		this.mergerType = MergerType;
	}

	public static MergerBy and(Condition conditiona, Condition conditionb) {
		return new MergerBy(conditiona,conditionb, MergerType.AND);
	}

	public static MergerBy or(Condition conditiona, Condition conditionb) {
		return new MergerBy(conditiona,conditiona, MergerType.OR);
	}
	
	public MergerType getMergerType() {
		return mergerType;
	}

	public void setMergerType(MergerType mergerType) {
		this.mergerType = mergerType;
	}

	public Condition getConditiona() {
		return conditiona;
	}

	public Condition getConditionb() {
		return conditionb;
	}

	public void setConditiona(Condition conditiona) {
		this.conditiona = conditiona;
	}

	public void setConditionb(Condition conditionb) {
		this.conditionb = conditionb;
	}



	public static enum MergerType {
		AND, OR
	}
	
}
