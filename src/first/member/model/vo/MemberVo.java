package first.member.model.vo;

public class MemberVo {
//	ID VARCHAR2(15 BYTE),
//	PASSWD VARCHAR2(15 BYTE) NOT NULL,
//	NAME VARCHAR2(20 BYTE) NOT NULL,
//	EMAIL VARCHAR2(30 BYTE),
//	CONSTRAINT PK_MEMBER PRIMARY KEY (ID)
	private String id;
	private String passwd;
	private String name;
	private String email;
	
	public MemberVo() {
		// TODO Auto-generated constructor stub
	}
	
	public MemberVo(String id, String passwd, String name, String email) {
		this.id = id;
		this.passwd = passwd;
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MemberVo [id=" + id + ", passwd=" + passwd + ", name=" + name + ", email=" + email + "]";
	}
}
