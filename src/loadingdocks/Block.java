package loadingdocks;

public class Block {

	
	public int x;
	public int y;
	public String _virname;
	public double _concentration;
	
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		this._virname = null;
		this._concentration = 0;
	}
	
	public String get_virname() {
		return _virname;
	}

	public void set_virname(String _virname) {
		this._virname = _virname;
	}

	public double get_concentration() {
		return _concentration;
	}

	public void set_concentration(double _concentration) {
		this._concentration = _concentration;
	}

	public void has_virus(int _concentration) {
		if (this._virname != null);
	}
	
	public void decay(int _concentration) {
		if (this._concentration != 0) {
			this._concentration = this._concentration/10;
			
			if (this._concentration < 0.1) {
				this._concentration = 0;
			}
		}
	}
	
	public void update(String virname, double concen) {
		this._concentration += concen;
		this._virname = virname;
	}

}
