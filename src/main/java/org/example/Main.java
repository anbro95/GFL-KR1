package org.example;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SouvenirsCRUD crud = new SouvenirsCRUD();
        Souvenir souvenir = new Souvenir("Vaza", "USZoloto", LocalDate.now(), 15000);
        Souvenir souvenir1 = new Souvenir("Vaza1", "UkrZoloto", LocalDate.now(), 15000);
        Souvenir souvenir4 = new Souvenir("Vaza4", "Zolotiy vik", LocalDate.now(), 150004);
        Souvenir souvenir2 = new Souvenir("Vaza2", "IceBox", LocalDate.now(), 150002);

        Manufacturer manufacturer = new Manufacturer("Zolotiy vik", "Ukraine");
        Manufacturer manufacturer3 = new Manufacturer("UkrZoloto", "Ukraine");
        Manufacturer manufacturer4 = new Manufacturer("USZoloto", "USA");
        Manufacturer manufacturer1 = new Manufacturer("IceBox", "USA");

        try {
            crud.addSouvenir(souvenir);
            crud.addSouvenir(souvenir2);
            crud.addSouvenir(souvenir4);
            crud.addSouvenir(souvenir1);

            crud.addManufacturer(manufacturer);
            crud.addManufacturer(manufacturer1);
            crud.addManufacturer(manufacturer3);
            crud.addManufacturer(manufacturer4);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }

        crud.readAllSouvenirs().forEach(System.out::println);
        crud.readAllManufacturers().forEach(System.out::println);

        crud.deleteManufacturer("IceBox");

        crud.printAllInfo();
    }
}