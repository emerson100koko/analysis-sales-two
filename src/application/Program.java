package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the file patch: ");
		String path = sc.nextLine();

		List<Sale> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			System.out.println("\nTotal sales of by seller: ");

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}

			Set<String> name = new HashSet<>();
			name = list.stream().map(s -> s.getSeller()).collect(Collectors.toSet());

			for (String seller : name) {
				double total = list.stream()
						.filter(s -> s.getSeller().equals(seller))
						.map(s -> s.getTotal())
						.reduce(0.0, (x, y) -> x + y);

				System.out.println(seller + " - R$ " + String.format("%.2f", total));
			}

		} catch (IOException e) {
			System.out.println("Error" + e.getMessage());
		}

		sc.close();
	}
}
