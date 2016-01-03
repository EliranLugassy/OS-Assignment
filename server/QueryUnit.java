package server;

public class QueryUnit {

	private int x,y,z;

	public QueryUnit(int x) {
		this.x=x;
		y=0;
		z=0;
	}

	
	public QueryUnit(int x, int y) {
		this.x = x;
		this.y = y;
		z=1;
	}
	
	public void update(int z){
		this.z = z;
	}
	
	public int increase(){
		this.z++;
		return z;
	}
	
}
