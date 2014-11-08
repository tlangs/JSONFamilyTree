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
            if (line.hasOption("h")) {
                displayHelp();
                return;
            } else if (!line.hasOption("f")) {
                System.err.println("A file must be provided");
                return;
            }
            String filename = line.getOptionValue("f");
            BufferedReader br = new BufferedReader(new FileReader(filename));
            Gson gson = new Gson();
            Person[] people = gson.fromJson(br, Person[].class);
            Set<Person> allGrandchildren = new HashSet<>();
            if (line.hasOption("i")) {
                PersonService personService = new PersonService(people);
                personService.removeSpouses();
                allGrandchildren = personService.findAllGrandchildren();
            } else {
                PersonService personService = new PersonService(people);
                allGrandchildren = personService.findAllGrandchildren();
            }
            System.out.println("Total number of grandchildren found: " +
                    allGrandchildren.size());

        } catch (ParseException | FileNotFoundException e) {
            System.err.println("Unexpected exception:" + e.getMessage());
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
        System.out.println("grandchildren [options] -f <filename>");
        System.out.println("Options:");
        System.out.println("-i\t\t\t\tIdentical Spouse Lists." +
                "\n\t\t\t\tAssume lists of children for spouses are identical.\n");
        System.out.println("-h\t\t\t\tHelp\n\t\t\t\tDisplay this help screen.\n");
        System.out.println("-f <filename> \t\t\tUnique Spouse Lists." +
                "\n\t\t\t\tAssume lists of children for spouses are identical." +
                "\n\t\t\t\tIf lists are not identical, there is a possibility of miscounting.");
    }
}
