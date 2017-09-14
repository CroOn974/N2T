import java.util.Scanner;

class N2T{

	public static void main(String[] args){
		double saisie = recupSaisie();
		long partieEntiere = recupPartieEntiere(saisie);
		int partieDecimale = (int)((saisie - partieEntiere) * 100 + 0.1);			// Calcule la partie décimale de la saisie ajout de 0.1 corriger erreur de conversion

		String devise = ecrireDevise(partieEntiere);

		String centime = ecrireCentime(partieDecimale);
		String trad = conversionPartieEntiere(partieEntiere) + " " + devise + " et " + conversionPartieDecimale(partieDecimale) + " " + centime;
		System.out.print(trad);


	}

	// Gère les "s" à euro et le "d'" pour million et milliard
	public static String ecrireDevise(long partieEntiere){
		String devise = "euro";  //initialise la devise sans "s"
		if(partieEntiere > 1){ //ajoute un "s" si la partie entier est superieur a 1
			devise = "euros";
		}
		if((partieEntiere%1000000000 == 0 || partieEntiere%1000000 == 0) && partieEntiere > 1){ //ajoute un "d'" a euro pour certain cas comme "un million d'euro"
			devise = "d'" + devise;
		}
		return devise;
	}

	public static String ecrireCentime(int partieDecimale){
		String centime = "centime";// initialise les centime sans "s"
		if(partieDecimale > 1){// ajoute un "s" s'il la partie decimal est superieur a 1
			centime = "centimes";
		}
		return centime;
	}

	// Fonction qui demande à l'utilisateur de saisir un nombre
	public static double recupSaisie(){

		Scanner lireclav = new Scanner(System.in);
		System.out.print("Veuillez saisir un nombre : ");
		double saisie = lireclav.nextDouble();
		return saisie;

	}

	// Fonction qui récupère la partie entière de la saisie
	public static long recupPartieEntiere(double saisie){

		long partieEntiere = (long)saisie;
		return partieEntiere;
	}

	// Focntion qui convertit la partie entière en lettre
	public static String conversionPartieEntiere(long partieEntiere){

		int reste = 0;
		int compteur = 0;
		String tradPartieEntiere = "" ;
		boolean test = true;

		//si la partie entier est egal a 0
		if(partieEntiere == 0){

			tradPartieEntiere = "zero ";
		}

		while(partieEntiere > 0){
			long reste1 = (long)partieEntiere%1000;				// Sert à corriger erreur de convertion de type
			reste = (int)reste1;

			//verifie si le reste st different de 0
			if(reste != 0){


				//switch case pour determine si on doit affiche mille million ou milliard selon le compteur
				switch (compteur) {
					case 1 :
						tradPartieEntiere = "-mille " + tradPartieEntiere;
						break;
					case 2:
						if(reste > 1){
							tradPartieEntiere = "-millions " + tradPartieEntiere;
						}else{
							tradPartieEntiere = "-million " + tradPartieEntiere;
						}
						break;
					case 3:
					if(reste > 1){
						tradPartieEntiere = "-milliards " + tradPartieEntiere;
					}else{
						tradPartieEntiere = "-milliard " + tradPartieEntiere;
					}
					break;

				}

				tradPartieEntiere = split(reste,test) + tradPartieEntiere;
				if(compteur == 1 && reste < 2){
					tradPartieEntiere = tradPartieEntiere.substring(3, tradPartieEntiere.length());
				}

				test = false;

			}

			partieEntiere = (partieEntiere - reste)/1000;					//calcule le reste à traiter
			compteur = compteur + 1;
		}



		return tradPartieEntiere;

	}

	// Fonction qui convertit la partie décimale en lettre
	public static String conversionPartieDecimale(int partieDecimale){


		String tradPartieDecimale = conversionDizUnit(partieDecimale,true);

		//pas de centimes ou est egal a 0
		if(partieDecimale == 0){

			tradPartieDecimale = "zero";
		}

		return tradPartieDecimale;
	}

	// Fonction qui découpe un nombre en centaine et dizUnit
	public static String split(int reste, boolean test){

		int dizUnit = reste%100;										// Stock les dizaines et unités
		int centaine = (reste - dizUnit) /100;							// Stock les centaines
		String tradCentaine = conversionCentaine(centaine);				// Convertit les centaines en lettres
		if(centaine == 1 && dizUnit >= 0){
			if(dizUnit == 0){
				tradCentaine = tradCentaine + "cent";
			}else{
				tradCentaine = tradCentaine + "cent-";
			}
		}else if(centaine > 1){
			if(dizUnit > 0){
				tradCentaine = tradCentaine + "-cent-";
			}else{
				tradCentaine = tradCentaine + "-cents";
			}

		}
		String tradDizUnit = conversionDizUnit(dizUnit,test);				// Convertit les dizaines et unités en lettres
		String tradCDU = tradCentaine  + tradDizUnit;				// Concatène les centaines et dizaines et unités
		return tradCDU;
	}

	// Fonction qui convertit des centaines en lettres
	public static String conversionCentaine(int centaine){

		String tabCentaine[] = {"", "", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
		String tradC = tabCentaine[centaine];
		return tradC;

	}

	// Fonction qui convertit des dizUnit en lettres
	public static String conversionDizUnit(int dizUnit, boolean test){

		String tabUnite[] = {"zero", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
		String tabDizaine[] = {"", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"};
		String tabVingtQuatreVingtsDix[] = {"", "", "vingt", "trente", "quanrante", "cinquante", "soixante", "soixante", "quatre-vingt", "quatre-vingt"};
		String tradDU = "";

		int unite = dizUnit%10; 										// Isole l'unité
		int dizaine = (dizUnit - unite) /10;							// Isole la dizaine

		if(dizUnit < 10 && dizUnit > 0){								// Vérifie si Dizunite en compris entre 1 et 9

			tradDU = tabUnite[unite];

		}else if(dizUnit == 10){										// Vérifie si Dizunite est égal à 10

			tradDU = "dix";

		}else if(dizUnit < 20){											// Vérifie si Dizunite est inférieur à 20

			tradDU = tabDizaine[unite];

		}else if (test == true && dizUnit == 80) {
			tradDU = "quatre-vingts ";

		}else{
																	// Tout les nombres entre 20 et 99
			if(dizaine == 7 || dizaine == 9 || unite == 0){				// Cas spécieux pour 70, 90 et 0
				if(unite == 0){
					tradDU = tabVingtQuatreVingtsDix[dizaine] + tabDizaine[unite];
				}else{
					tradDU = tabVingtQuatreVingtsDix[dizaine] + "-" + tabDizaine[unite]; // Concaténation pour former les nombres, 71, 72...
				}
				if(unite == 1){
					tradDU = tabVingtQuatreVingtsDix[dizaine] + "-et-" + tabDizaine[unite];
				}
			}else{

				if(unite == 1){
					tradDU = tabVingtQuatreVingtsDix[dizaine] + "-et-" + tabUnite[unite];
				}else{
					tradDU = tabVingtQuatreVingtsDix[dizaine]  + "-" + tabUnite[unite];
				}
			}
		}

		return tradDU;

	}
}
