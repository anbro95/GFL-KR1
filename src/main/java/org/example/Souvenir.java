package org.example;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Souvenir {
    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String requisites;
    @CsvCustomBindByPosition(position = 2, converter = LocalDateConverter.class)
    private LocalDate date;
    @CsvBindByPosition(position = 3)
    private int price;
}
