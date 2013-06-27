package net.sf.exlp.monitor.net.dns;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DnsResult
{
	// **********   Fields   *************** /
		@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
		private long id;
		
		private int duration;
		 
		// **********   Constructor   *********** /
		public DnsResult(int duration)
		{
			this.duration = duration;
		}
		 
		// ********   Getter/Setter   *********** //
		public long getId() {return id;}
		public void setId(long id) {this.id = id;}
		 
		public int getDuration() {return duration;}
		public void setDuration(int duration) {this.duration = duration;}
		
		// ***********   Methods   ************** //
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
				sb.append("(").append(id).append(")");
				sb.append(" ").append(duration);
			return sb.toString();
		}
}
