package net.sf.exlp.addon.dirsize.data.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;

@Entity
@NamedQueries
({
//	@NamedQuery(name="allExlpDirectories",query="SELECT d FROM ExlpDirectory d")
})
public class ExlpFile implements Serializable
{
	public static final long serialVersionUID=1;
	
	public static enum Type{dir,file,unknown}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Type type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private ExlpFile parent;
	
	private String name;
	
	private Date modified;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="parent")
	private List<ExlpFile> childs;
	
	// >>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<<<
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	public Type getType() {return type;}
	public void setType(Type type) {this.type = type;}
	
	public ExlpFile getParent() {return parent;}
	public void setParent(ExlpFile parent) {this.parent = parent;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public Date getModified() {return modified;}
	public void setModified(Date modified) {this.modified = modified;}
	
	public List<ExlpFile> getChilds() {if(childs==null){childs = new ArrayList<ExlpFile>();};return childs;}
	public void setChilds(List<ExlpFile> childs) {this.childs = childs;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append(id);
		return sb.toString();
	}
}