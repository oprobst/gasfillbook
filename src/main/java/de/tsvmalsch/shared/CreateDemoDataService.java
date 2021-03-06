package de.tsvmalsch.shared;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.tsvmalsch.shared.model.BlendingType;
import de.tsvmalsch.shared.model.Cylinder;
import de.tsvmalsch.shared.model.CylinderType;
import de.tsvmalsch.shared.model.FillingInvoiceItem;
import de.tsvmalsch.shared.model.Member;
import de.tsvmalsch.shared.model.UserRights;

public class CreateDemoDataService {
	private static String getFirstName(int i) {

		final String[] names = { "O ", "Mia ", "Emma ", "Hannah ", "Sofia ",
				"Anna ", "Lea ", "Emilia ", "Marie ", "Lena ", "Leonie ",
				"Emily ", "Lina ", "Amelie ", "Sophie ", "Lilly ", "Luisa ",
				"Johanna ", "Laura ", "Nele ", "Lara ", "Maja ", "Charlotte ",
				"Clara ", "Leni ", "Sarah ", "Pia ", "Mila ", "Alina ",
				"Lisa ", "Lotta ", "Ida ", "Julia ", "Greta ", "Melina ",
				"Paula ", "Marlene ", "Jana ", "Helena ", "Isabella ",
				"Elisa ", "Amy ", "Mira ", "Katharina ", "Stella ", "Antonia ",
				"Annika ", "Fiona ", "Pauline ", "Nora ", "Eva ", "Jule ",
				"Magdalena ", "Luna ", "Merle ", "Maria ", "Nina ", "Melissa ",
				"Franziska ", "Milena ", "Ronja ", "Elena ", "Romy ", "Mina ",
				"Helene ", "Selina ", "Paulina ", "Vanessa ", "Fabienne ",
				"Zoey ", "Miriam ", "Linda ", "Samira ", "Elina ", "Jolina ",
				"Celina ", "Elisabeth ", "Valentina ", "Julie", "Olivia ",
				"Jette ", "Kim ", "Elif ", "Lotte ", "Anastasia ", "Luana ",
				"Rosalie ", "Mona ", "Lana ", "Diana ", "Thea ", "Alexandra ",
				"Angelina ", "Ben ", "Luca ", "Paul ", "Jonas ", "Finn ",
				"Leon ", "Luis ", "Lukas ", "Maximilian ", "Felix ", "Noah ",
				"Elias ", "Julian ", "Max ", "Tim ", "Moritz ", "Henry ",
				"Niklas ", "Philipp ", "Jakob ", "Tom ", "Jan ", "Emil ",
				"Alexander ", "David ", "Oskar ", "Fabian ", "Anton ", "Erik ",
				"Rafael Dean ", "Adam ", "Diego ", "Piet ", "Franz ", "Fritz ",
				"Hugo ", "Michel", " Ilias Malik", " Nikita ", "Henrik ",
				"Lionel ", "Martin ", "Clemens Milo ", "Ian", " Len Emilian",
				" Yusuf", " Lio ", "Jasper", " Lion ", "Maik Eddi Can", " Ali",
				" Berat ", "Maurice ", "Andreas", " Taylor ", "Kevin",
				" Alessio ", "Kai Laurens Carlo Leonardo ", "Willi Benno",
				" Devin ", "Luan ", "Gustav ", "Leonhard ", "Mert", " Chris",
				" Thore Ryan", " Yasin ", "Fiete ", "Henning", " Arian ",
				"Roman ", "Korbinian", " Carlos", " Jonte", " Alessandro ",
				"Peter", " Deniz", " Nino", " Alex", " Antonio", " Mailo",
				" Brian Amir ", "Christopher", " Thilo Damien", " Mehmet",
				" Ricardo Dario", " Joris", " Victor Darian ", "Josef Jack",
				" Kaan", " Mick ", "Enno ", "Kjell", " Aiden Domenic Jesper",
				" Enes", " Ömer", " Titus", " Hamza", " Mustafa", " Mikail ",
				"Torben Jaron ", "Ruben", " Bjarne", " Miran ",
				"Stefan Claas Edgar", " Jerome ", "Leif", " Leonas", " Lino",
				" Romeo", " Andre ", "Nathan", " Tino", " William ", "Hanno",
				" Sami ", "Ahmet", " Miguel ", "Steven", " Emin", " Lean",
				" Mirac", " Semih", " Sinan", " Etienne", " Ibrahim ",
				"Mario ", "Timon", " Xaver ", "Armin", " Efe", " Janosch",
				" Kerem", " Mio ", "Wilhelm ", "Albert ", "Erwin ", "Hans",
				" Marian", " Anthony", " Cem", " Emre", " Eymen", " Leonidas",
				" Aras", " Ensar ", "Kenan", " Kuzey", " Lutz", " Selim ",
				"Tamme", " Valentino", " Danny", " Emanuel", " Giuliano",
				" Hassan Kerim", " Umut", " Amin", " Arda", " Danilo", " Eren",
				" Mattes", " Vince", " Arvid", " Darius ", "Dustin", " Jake",
				" Jarne", " Jim ", "Marten", " Sean", " James", " Jean",
				" Lucien", " Rayan", " Elian", " Emirhan", " Furkan", " Jonne",
				" Kalle", " Karim", " Milian", " Timur", " Damon ", "Enrico",
				" Marek", " Quentin", " Alwin", " Angelo", " Jesse ", "Otto",
				" Samir", " Yassin", " Bilal", " Caspar", " Jannek Jarno",
				" Maddox", " Mahir", " Marlo", " Rico", " Tjark",
				" Elija Iven Joscha", " Nicolai Rocco ", "Sven", " Berkay",
				" Dion", " Gregor", " Jano", " Koray", " Ramon", " Sandro",
				" Taylan", " Davin", " Francesco", " Jamal", " Jordan",
				" Lewis", " Omar", " Pius", " Taha", " Veit", " Amar",
				" Eduard", " Gianluca", " Ismail", " Joost Lucian", " Miko",
				" Sirac", " Thies ", "Alfred", " Dylan", " Eray", " Flynn",
				" Hauke", " Logan ", "Melvin", " Younes", " Ilja", " Jon",
				" Mete", " Tamino", " Alan", " Arjen", " Jenke", " Johnny",
				" Keno", " Loris", " Milow", " Santino", " Tiago Burak",
				" Fabrizio", " Gian", " Hennes", " Kirill", " Lorik", " Luiz",
				" Peer", " Talha", " Tizian", " Tommy", " Yunus", " Aidan",
				" Baran ", "Björn", " Cornelius", " Dorian ", "Hagen",
				" Leano", " Quinn", " Youssef", " Benny", " Bent", " Ege",
				" Gero", " Ivan", " Kimi", " Levent", " Luuk", " Maris",
				" Miro", " Sören", " Stanley", " Vito", " Yigit", " Batuhan",
				" Edwin", " Jakub", " Juri", " Kiyan", " Meo", " Musa",
				" Raul", " Ron", " Rüzgar", " Said", " Sammy", " Santiago",
				" Severin", " Adem", " Adriano", " Alexandros", " Ansgar ",
				"August", " Bo", " Dejan", " Eyüp", " Fridolin", " Hüseyin",
				" Ilay", " Jay", " Jimmy", " Josua", " Merlin", " Nathanael",
				" Quirin", " Azad", " Davide", " Denny", " Evan", " Giuseppe",
				" Jari", " Jascha", " Neven", " Pierre", " Rick", " Tarik",
				" Vinzenz", " Ahmad", " Amon", " Andrej", " Aurel", " Bosse",
				" Demian", " Ethan", " Joe", " Joey", " Nelio ", "René",
				" Salvatore" };
		return names[i].trim();
	}

	private static String getLastName(int i) {

		final String[] names = { "Müller", "Schneider", "Fischer", "Weber",
				"Meyer", "Wagner", "Becker", "Schulz", "Hoffmann", "Schäfer",
				"Koch", "Bauer", "Richter", "Klein", "Übername", "Schröder",
				"Neumann", "Schwarz", "Zimmermann", "Braun", "Krüger",
				"Hofmann", "Hartmann", "Lange", "Schmitt", "Werner", "Schmitz",
				"Krause", "Meier", "Lehmann", "Schmid", "Schulze", "Maier",
				"Köhler", "Herrmann", "König", "Walter", "Mayer", "Huber",
				"Kaiser", "Fuchs", "Peters", "Lang", "Scholz", "Möller",
				"Weiß", "Jung", "Hahn", "Schubert", "Vogel", "Friedrich",
				"Keller", "Günther", "Frank", "Berger", "Winkler", "Roth",
				"Beck", "Lorenz", "Baumann", "Franke", "Albrecht", "Schuster",
				"Simon", "Ludwig", "Böhm", "Winter", "Kraus", "Martin",
				"Schumacher", "Krämer", "Vogt", "Stein", "Jäger", "Otto",
				"Sommer", "Groß", "Seidel", "Heinrich", "Brandt", "Haas",
				"Schreiber", "Graf", "Schulte", "Dietrich", "Ziegler", "Kuhn",
				"Kühn", "Pohl", "Engel", "Horn", "Busch", "Bergmann", "Thomas",
				"Voigt", "Sauer", "Arnold", "Wolff" };
		return names[i];
	}

	public static Collection<Member> createDummyMembers() {
		Collection<Member> mlist = new LinkedList<>();

		mlist.add(createClubToolMember());

		for (int i = 0; i < 500; i++) {
			Member m = new Member();
			mlist.add(m);
			m.setFirstName(CreateDemoDataService.getFirstName(i % 250));
			m.setLastName(CreateDemoDataService.getLastName(i % 94));
			m.setMemberNumber(i + 1);
			m.setEncodedPassword("1");

			m.setEmail(CreateDemoDataService.getFirstName(i % 250) + "."
					+ CreateDemoDataService.getLastName(i % 94)
					+ "@tsv-malsch.de");
			m.setRights(new UserRights());
			m.getRights().setBlendingAuthorization((int) (Math.random() * 4));

			if (i < 6) {
				m.setIsAdmin(true);
				m.getRights().setBlendingAuthorization(4);
			}

			long month = 60000 * 60 * 24 * 30;
			m.getRights().setLastBriefing(
					new Date(System.currentTimeMillis() - month * 14
							+ (int) (Math.random() * 5) * month));
			m.setIsActive(true);
			m.setHasGasblenderBrevet(true);

			Set<Cylinder> cset = new HashSet<Cylinder>();
			Cylinder cy = new Cylinder();
			cy.setName("D12-" + m.getFirstName().charAt(0)
					+ m.getLastName().charAt(0));
			cy.setMaximumPreasure(300);
			cy.setNextInspectionDate(new Date(System.currentTimeMillis() + 1000
					* 60 * 24 * 360 * 1000));
			cy.setSizeInLiter(12.0d);
			cy.setOwner(m);

			cy.setSerialNumber("0000124124" + i);
			cset.add(cy);
			Cylinder cy2 = new Cylinder();
			cy2.setName("D12-" + m.getFirstName().charAt(0)
					+ m.getLastName().charAt(0));
			cy2.setMaximumPreasure(300);
			cy2.setNextInspectionDate(new Date(System.currentTimeMillis()
					+ 1000 * 60 * 24 * 360 * 1000));
			cy2.setSizeInLiter(12.0d);
			cy2.setOwner(m);
			cy2.setSerialNumber("0000124125" + i);
			cy2.setTwinSetPartner(cy);
			cset.add(cy2);

			for (int j = 0; j < 5; j++) {
				Cylinder cy3 = new Cylinder();
				if (j < 2) {
					cy3.setName("S-" + j);
				}
				cy3.setMaximumPreasure(232);
				cy3.setNextInspectionDate(new Date(System.currentTimeMillis()
						+ 60000 * 60 * 24 * 30 * (j - 2)));
				cy3.setSizeInLiter(11.1d);
				cy3.setOwner(m);
				cy3.setSerialNumber("MES123" + i + "" + j);
				cy3.setGasType(CylinderType.OXYCLEAN);
				cset.add(cy3);
			}
			m.setCylinders(cset);

		}
		return mlist;
	}

	private static Member createClubToolMember() {
		Member m = new Member();
		m.setFirstName("TSV");
		m.setLastName("Malsch");
		m.setMemberNumber(-1);
		m.setEncodedPassword("1");
		m.setEmail("info@tsv-malsch.de");
		m.setRights(new UserRights());
		m.getRights().setBlendingAuthorization((int) (Math.random() * 4));

		m.getRights().setLastBriefing(new Date(0));
		m.setIsActive(true);
		m.setHasGasblenderBrevet(false);

		Set<Cylinder> cset = new HashSet<Cylinder>();

		for (int j = 0; j < 15; j++) {
			Cylinder cy3 = new Cylinder();
			if (j < 2) {
				cy3.setName("Clubflasche-" + j);
			}
			cy3.setMaximumPreasure(232);
			cy3.setNextInspectionDate(new Date(System.currentTimeMillis()
					+ 60000 * 60 * 24 * 30 * (j - 2)));
			cy3.setSizeInLiter(12.0d);
			cy3.setOwner(m);
			cy3.setSerialNumber("0815" + j);
			cset.add(cy3);
		}
		m.setCylinders(cset);

		return m;
	}

	public static Collection<FillingInvoiceItem> createDummyInvoiceItem(Member m) {

		Collection<FillingInvoiceItem> mlist = new LinkedList<>();
		for (int j = 0; j < 3; j++) {
			FillingInvoiceItem fii = new FillingInvoiceItem();
			fii.setBlendingType(BlendingType.AIR);
			fii.setBlendingMember(m);
			fii.setAccountedMember(m);
			fii.setLiterAirFilled(1000 + j * 100);
			fii.setDateOfFilling(new Date(System.currentTimeMillis() - j * 1000
					* 60 * 60 * 24 * 2));
			fii.setId((long) 0 + j);
			Cylinder tank = m.getCylinders().iterator().next();
			fii.setFilledCylinder(tank);
			fii.setPricePerLiterOxygen(0.0055f);

			mlist.add(fii);
		}

		for (int j = 0; j < 4; j++) {
			FillingInvoiceItem fii = new FillingInvoiceItem();
			fii.setBlendingType(BlendingType.NX40_CASCADE);
			fii.setLiterOxygenFilled(288 + j * 123);
			fii.setLiterAirFilled(800 + j * 112);
			fii.setId((long) 12 + j);
			fii.setBlendingMember(m);
			fii.setAccountedMember(m);
			fii.setDateOfFilling(new Date(System.currentTimeMillis() - j * 1000
					* 60 * 60 * 24 * 2));
			Cylinder tank = m.getCylinders().iterator().next();
			fii.setFilledCylinder(tank);
			fii.setPricePerLiterOxygen(0.0055d);
			fii.setPricePerLiterHelium(0.0175d);
			mlist.add(fii);
		}

		FillingInvoiceItem fii = new FillingInvoiceItem();
		fii.setBlendingType(BlendingType.NX40_CASCADE);
		fii.setPricePerLiterHelium(0.0175f);
		fii.setPricePerLiterOxygen(0.0055f);
		fii.setLiterOxygenFilled(340);
		fii.setLiterAirFilled(2142);
		fii.setLiterHeliumFilled(1292);
		fii.setId(815l);
		fii.setBlendingMember(m);
		fii.setAccountedMember(m);
		fii.setDateOfFilling(new Date(System.currentTimeMillis() - 12 * 1000
				* 60 * 60 * 24 * 2));
		fii.setInvoicingDate(new Date(System.currentTimeMillis() - 8 * 1000
				* 60 * 60 * 24 * 2));
		Cylinder tank = m.getCylinders().iterator().next();
		fii.setFilledCylinder(tank);
		mlist.add(fii);

		fii = new FillingInvoiceItem();
		fii.setBlendingType(BlendingType.PARTIAL_METHOD);
		fii.setPricePerLiterHelium(0.0175f);
		fii.setPricePerLiterOxygen(0.0055f);
		fii.setLiterOxygenFilled(221);
		fii.setLiterAirFilled(1403);
		fii.setLiterHeliumFilled(3232);
		fii.setId(819l);
		fii.setBlendingMember(m);
		fii.setDateOfFilling(new Date(System.currentTimeMillis() - 8 * 1000
				* 60 * 60 * 24 * 2));
		fii.setInvoicingDate(new Date(System.currentTimeMillis() - 1 * 1000
				* 60 * 60 * 24 * 2));
		fii.setPaymentReceiptDate(new Date(System.currentTimeMillis() - 0
				* 1000 * 60 * 60 * 24 * 2));
		tank = m.getCylinders().iterator().next();
		fii.setFilledCylinder(tank);
		mlist.add(fii);

		return mlist;
	}
}
