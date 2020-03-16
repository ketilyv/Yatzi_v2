package yzpakke;

import java.io.IOException;

public class KastTest {
	private static Terning[] terninger = new Terning[4];
	private static Terning terning = new Terning();
	
	public static void main(String[] args) {
		kast(4);

	}
	public static void kast(int antTern) {
		int[] kategorier = {0, 0, 0, 0, 0, 0};	
		String[] terntab = {"4", "6", "2", "5"};
		//Terning[] terninger = new Terning[antTern];
		for (int i = 0; i < antTern; i++) {
			Terning tr = new Terning();
			//System.out.println(terninger.hentVerdi());
		
		}
		//int[] terninger = new int[antTern];
		for (int i = 0; i < antTern; i++) {
			int verdi = Integer.parseInt(terntab[i]);
			System.out.println(verdi);
			Terning tr = new Terning();
			tr.settVerdi(verdi);
			terninger[i] = tr;
		}
		for (int i = 0; i < antTern; i++) {
			int t = terninger[i].hentVerdi();
			kategorier[t-1]++;
		}
		for (int i = 0; i < kategorier.length; i++) {
			System.out.println(kategorier[i]);/*if (valgt == -1) {
		}
			velgKat();
		} else {
			spart += kategorier[valgt];*/
		}
	}
		
}
