package yzpakke;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Spill {
	
    private	int[] poengtavle = new int[6];
    private	int[] kategorier = new int[6]; //Tabell som sier hvor mange terninger av hver verdi det er etter et kast.
    private boolean[] fyltUt = new boolean[6];//Holder rede på hvilke kategorier som er ferdig utfylt og dermed ikke kan velges igjen.
    private String url_beg = "http://nav-yatzy.herokuapp.com/throw?n=";
    private int valgt = -1; //Nåværende valgt kategori/verdi.
    private int spart = 0; //antall terninger som er spart/tilsidesatt etter et kast.
    private boolean fortsett = true;//Dersom false, så er det en feil som gjør at videre kjøring avbrytes.
    
    public int[] hentPoengtavle() {
    	return poengtavle;
    }
	/*public static void main(String[] args) throws IOException {
		int totalSum = 0;
		for (int i = 0; i < 6; i++) {
			System.out.println("Runde nr " + (i+1) + ":");
			int sum = runde();
			if (sum == -1) break;
			else {
				totalSum += sum;
			}
			System.out.println();
		}
		System.out.println("Poengtavle: ");
		for (int i = 0; i < 6; i++) {
			System.out.println("Poeng for " + (i+1) + "'ere: " + poengtavle[i]);
		}
		System.out.println();
		System.out.println("Total poengsum er: " + totalSum);
	}*/
	//En runde i spillet, bestående av max 3 kast
	public int runde()throws IOException {
		int antTern = 5;
		spart = 0;
		valgt = -1;
		int rundeSum = 0;
		for (int i = 0; i < 3; i++) {
			System.out.print("Kast nr " + (i+1) + ": ");
			kast(antTern - spart);
			if (fortsett == false) return -1;
			if (valgt > -1)
				rundeSum += kategorier[valgt] * (valgt +1);
		}
		 System.out.println("Rundesum: " + rundeSum);
		 System.out.println("Vi samlet på " + (valgt +1) +  "'ere denne runden.");
		 if (valgt > -1)
			 poengtavle[valgt] = rundeSum;
		 return rundeSum;
	}
	//Et kast i en gitt runde
	public void kast(int antTern)throws IOException {
		for (int i = 0; i < 6; i++) { 
			kategorier[i] = 0;
		}
		String tern = trillTerningFraNavURL(antTern);
		if (tern == "") return;
		String[] terntab = tern.split(",");
		Terning[] terninger = new Terning[antTern];
		//int[] terninger = new int[antTern];
		for (int i = 0; i < antTern; i++) {
			int verdi = Integer.parseInt(terntab[i]);
			Terning tr = new Terning();
			tr.settVerdi(verdi);
			terninger[i] = tr;
		}
		for (int i = 0; i < antTern; i++) {
			Terning tr = terninger[i];
			int t = tr.hentVerdi();
			kategorier[t-1]++;
		}
		if (valgt == -1) {
			velgKat();
		} else {
			spart += kategorier[valgt];
		}
	}
	
	//Her velges kategori, dvs hvilken verdi man vil samle på i denne runden
	private void velgKat() {
		boolean [] maxTab = new boolean[6];
		int max = 0; 
		int index = 6;
		for (int i = index-1; i > -1; i--) {
			if (kategorier[i] > max && fyltUt[i] == false) 
				max = kategorier[i];
		}
		for (int i = 0; i < index; i++) {
			if (kategorier[i] == max) maxTab[i] = true;
		}
		int i = 5;
		while (i > -1 && (maxTab[i] == false || fyltUt[i] == true)) 
			i--;
		if (i > -1) {
			valgt= i;
			fyltUt[i] = true;
			spart = kategorier[i];
		}
	}
	
	//"Terningdata" hentes vha. angitt url
	private String trillTerningFraNavURL(int antTern) throws IOException {
		String nt = String.valueOf(antTern);
		String tern = "";
		URL url = new URL(url_beg + nt);
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int status = con.getResponseCode();
			
			if (status > 299) {
				System.out.println();
				System.out.println("Feilkode: " + status);
				System.out.println("Fil ikke funnet!");
				fortsett = false;
				return "";
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
			    content.append(inputLine);
			}
			in.close();
			String ternt = content.toString(); 
			String tern1 = ternt.replace ("[", "");
			tern = tern1.replace ("]", "");
			System.out.println(tern);
		
		} catch (FileNotFoundException filfeil) {
			System.out.println(filfeil + " (Fil ikke funnet.)");
		}
		return tern;
	}
}

