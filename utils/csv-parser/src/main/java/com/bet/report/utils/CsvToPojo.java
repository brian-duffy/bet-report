package com.bet.report.utils;

import com.bet.utils.exception.SystemException;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Csv to pojo.
 */
@Slf4j
public final class CsvToPojo {

  /**
   * Upload csv file list.
   *
   * @param <T>  the type parameter
   * @param file the file
   * @param clz  the clz
   * @return the list
   */
  public static <T> List<T> from(MultipartFile file, Class<T> clz) {
    List<T> models;
    try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
      BeanListProcessor<T> rowProcessor = new BeanListProcessor<>(clz);
      CsvParserSettings parserSettings = new CsvParserSettings();
      parserSettings.setProcessor(rowProcessor);
      parserSettings.setHeaderExtractionEnabled(true);
      parserSettings.setIgnoreLeadingWhitespaces(true);
      parserSettings.setNumberOfRowsToSkip(0);
      CsvParser parser = new CsvParser(parserSettings);
      parser.parse(reader);
      models = rowProcessor.getBeans();
      return models;
    } catch (IOException e) {
      log.error("Unable to convert from csv {}", e.getMessage());
      throw new SystemException("Unable to convert from csv");
    }
  }
}
