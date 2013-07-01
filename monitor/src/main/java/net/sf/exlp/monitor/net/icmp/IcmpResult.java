package net.sf.exlp.monitor.net.icmp;

import java.util.Date;

public class IcmpResult
{
	public static enum Code {REACHABLE,TIMEOUT,UNKNOWN_HOST,ERROR}
	
	private Date record;
	
	private long duration;
	
	private Code code;
			
	// ********   Getter/Setter   *********** //
		 
	public long getDuration() {return duration;}
	public void setDuration(long duration) {this.duration = duration;}
		
	public Date getRecord() {return record;}
	public void setRecord(Date record) {this.record = record;}
		
	public Code getCode() {return code;}
	public void setCode(Code code) {this.code = code;}
	
	// ***********   Methods   ************** //
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(code.toString());
		sb.append(" ").append(record);
		sb.append(" ").append(duration);
		return sb.toString();
	}
}