package yzpakke;

public class Terning {
	private int verdi;
	
	Terning(){
		verdi = 0;
	}
	
	public void settVerdi(int tall) {
		verdi = tall;
	}
	public int hentVerdi() {
		return verdi;
	}
}
