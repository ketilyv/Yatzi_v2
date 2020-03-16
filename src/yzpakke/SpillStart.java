package yzpakke;

import java.io.IOException;

public class SpillStart {
	public static void main(String[] args) throws IOException {
		int totalSum = 0;
		Spill spill = new Spill();
		for (int i = 0; i < 6; i++) {
			System.out.println("Runde nr " + (i+1) + ":");
			int sum = spill.runde();
			if (sum == -1) break;
			else {
				totalSum += sum;
			}
			System.out.println();
		}
		System.out.println("Poengtavle: ");
		for (int i = 0; i < 6; i++) {
			System.out.println("Poeng for " + (i+1) + "'ere: " + spill.hentPoengtavle()[i]);
		}
		System.out.println();
		System.out.println("Total poengsum er: " + totalSum);
	}
}
