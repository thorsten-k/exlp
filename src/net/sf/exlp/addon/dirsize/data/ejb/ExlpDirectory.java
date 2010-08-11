package net.sf.exlp.addon.dirsize.data.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
@NamedQueries
({
	@NamedQuery(name="allExlpDirectories",query="SELECT d FROM ExlpDirectory d")
})
public class ExlpDirectory implements Serializable
{
	public static final long serialVersionUID=1;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String path;
	
	private boolean scanActive,pathRelative;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<ExlpDirectorySize> sizes;

	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private ExlpDirectorySize recentSize;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public String getPath() {return path;}
	public void setPath(String path) {this.path = path;}
	
	public boolean isScanActive() {return scanActive;}
	public void setScanActive(boolean scanActive) {this.scanActive = scanActive;}
	
	public boolean isPathRelative() {return pathRelative;}
	public void setPathRelative(boolean pathRelative) {this.pathRelative = pathRelative;}
	
	public List<ExlpDirectorySize> getSizes() {if(sizes==null){sizes = new ArrayList<ExlpDirectorySize>();};return sizes;}
	public void setSizes(List<ExlpDirectorySize> sizes) {this.sizes = sizes;}
	
	public ExlpDirectorySize getRecentSize() {return recentSize;}
	public void setRecentSize(ExlpDirectorySize recentSize) {this.recentSize = recentSize;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}
