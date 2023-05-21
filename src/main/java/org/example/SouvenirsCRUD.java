package org.example;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.Collectors;

public class SouvenirsCRUD {

    private final File souvenirsFile = new File("souvenirs.csv");
    private final File manufacturersFile = new File("manufacturers.csv");

    public SouvenirsCRUD() {}



    public void changeSouvenir() {

    }

    public void addSouvenir(Souvenir souvenir) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        try (Writer writer  = new FileWriter(souvenirsFile.toPath().toString(), true)) {

            StatefulBeanToCsv<Souvenir> sbc = new StatefulBeanToCsvBuilder<Souvenir>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(souvenir);
        }
    }

    public void addManufacturer(Manufacturer manufacturer) {
        try (Writer writer  = new FileWriter(manufacturersFile.toPath().toString(), true)) {

            StatefulBeanToCsv<Manufacturer> sbc = new StatefulBeanToCsvBuilder<Manufacturer>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            sbc.write(manufacturer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Manufacturer> readAllManufacturers() {
        try (FileReader reader = new FileReader(manufacturersFile)) {
            CsvToBean<Manufacturer> csvToBean = new CsvToBeanBuilder<Manufacturer>(reader)
                    .withType(Manufacturer.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Souvenir> getSouvenirsByManufacturer(String name) {
        List<Souvenir> list = readAllSouvenirs();

        return list.stream().filter(s -> s.getRequisites().equals(name)).toList();
    }

    public List<Souvenir> getSouvenirsByCountry(String country) {
        List<String> mNames = readAllManufacturers().stream().filter(m -> m.getCountry().equals(country)).map(Manufacturer::getName).toList();
        return readAllSouvenirs().stream().filter(s -> mNames.contains(s.getRequisites())).toList();
    }

    public List<Manufacturer> getBySouvenirsPriceLessThan(int price) {
        List<String> requisites = readAllSouvenirs().stream().filter(s -> s.getPrice() < price).map(Souvenir::getRequisites).toList();
        return readAllManufacturers().stream().filter(m -> requisites.contains(m.getName())).toList();
    }

    public void printAllInfo() {
        List<Manufacturer> manList = readAllManufacturers();

        for (Manufacturer m : manList) {
            System.out.println("\n Manufacturer: " + m.getName() + " Country: " + m.getCountry());
            System.out.print("Souvenirs: " + "\n");
            printAllManSouvenirs(m.getName());
        }
    }

    private void printAllManSouvenirs(String name) {
        List<Souvenir> list = readAllSouvenirs().stream().filter(s -> s.getRequisites().equals(name)).toList();
        for (Souvenir s : list) {
            System.out.println("Name: " + s.getName() + " Manufacturer: " + s.getRequisites() + " Date: " + s.getDate()
                                + " Price: " + s.getPrice());
        }
    }

    public List<Souvenir> getAllByYear(int year) {
        return readAllSouvenirs().stream().filter(s -> s.getDate().getYear() == year).collect(Collectors.toList());
    }


    public void deleteManufacturer(String name) {
        List<Manufacturer> list = readAllManufacturers();
        try {
            Files.delete(manufacturersFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        list.stream().filter(m -> !m.getName().equals(name)).forEach(this::addManufacturer);
    }

    public void printAllMansBySouvNameAndYear(String name, int year) {
        List<String> mNames = readAllSouvenirs().stream().filter(s -> s.getName().equals(name)
                && (s.getDate().getYear() == year)).map(Souvenir::getRequisites).collect(Collectors.toList());

        List<Manufacturer> mans = readAllManufacturers().stream().filter(m -> mNames.contains(m.getName())).toList();

        for (Manufacturer man : mans) {
            System.out.println(man);
        }
    }


    public List<Souvenir> readAllSouvenirs() {
        try (FileReader reader = new FileReader(souvenirsFile)) {
            CsvToBean<Souvenir> csvToBean = new CsvToBeanBuilder<Souvenir>(reader)
                    .withType(Souvenir.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
