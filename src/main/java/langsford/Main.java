package langsford;

import com.google.gson.Gson;
import langsford.dto.Person;
import langsford.service.PersonService;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Options options = createOptions();
        CommandLineParser parser = new GnuParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h"))
                displayHelp();
            if (line.hasOption("i"))
                calculateIdenticalSpouseGrandchildren(line);
            else {
                calculateGrandchildren(line);
            }

        } catch (ParseException e) {
            System.out.println("Unexpected exception:" + e.getMessage());
        }
    }

    private static Options createOptions() {
        Options options = new Options();
        options.addOption("h", false, "get help");
        options.addOption("i", false, "assume identical spouse lists");
        options.addOption("f", true, "file to use");
        return options;

    }

    private static void displayHelp() {
        System.out.println("grandchildren [options] filename");
        System.out.println("Options:");
        System.out.println("-i\t\t\t\t\tIdentical Spouse Lists." +
                "\n\t\t\t\t\tAssume lists of children for spouses are identical.\n");
        System.out.println("-h\t\t\t\t\tHelp\n\t\t\t\t\tDisplay this help screen.\n");
        System.out.println("-f <filename> \t\tUnique Spouse Lists." +
                "\n\t\t\t\t\tAssume lists of children for spouses are identical.\n");
    }


    private static void calculateGrandchildren(CommandLine line) {
        if (line.hasOption("f")) {
            String filename = line.getOptionValue("f");
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                Gson gson = new Gson();
                Person[] people = gson.fromJson(br, Person[].class);
                Set<Person> totalGrandchildren = new HashSet<>();
                for (Person person : people) {
                    Person[] grandchildren = PersonService.findGrandChildren(person, people);
                    totalGrandchildren.addAll(Arrays.asList(grandchildren));
                }
                System.out.println("Total number of grandchildren found: " +
                        totalGrandchildren.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error: File not found!");
            }
        }
        else {
            System.err.println("File not provided");
        }

    }

    private static void calculateIdenticalSpouseGrandchildren(
            CommandLine line) {
        if (line.hasOption("f")) {
            String filename = line.getOptionValue("f");
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                Gson gson = new Gson();
                Person[] people = gson.fromJson(br, Person[].class);
                Person[] noSpouses = PersonService.sameChildrenLists(people);
                Set<Person> totalGrandchildren = new HashSet<>();
                for (Person person : noSpouses) {
                    Person[] grandchildren = PersonService.findGrandChildren(person, people);
                    totalGrandchildren.addAll(Arrays.asList(grandchildren));
                }
                System.out.println("Total number of grandchildren found: " +
                        totalGrandchildren.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.err.println("Error: File not found!");
            }
        }
    }
}
